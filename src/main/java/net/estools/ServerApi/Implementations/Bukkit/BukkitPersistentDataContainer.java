package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.Main;
import net.estools.ServerApi.EsPersistentDataType;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.EsPersistentDataContainer;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper.getNamespacedKey;

@SuppressWarnings("unchecked")  // Trust me bro
public class BukkitPersistentDataContainer implements EsPersistentDataContainer {
    private final PersistentDataContainer bukkitContainer;
    private static final PersistentDataType<?,?>[] dataTypes = new PersistentDataType[11];

    static {
        dataTypes[0] = PersistentDataType.STRING;
        dataTypes[1] = PersistentDataType.BYTE;
        dataTypes[2] = PersistentDataType.SHORT;
        dataTypes[3] = PersistentDataType.INTEGER;
        dataTypes[4] = PersistentDataType.LONG;
        dataTypes[5] = PersistentDataType.FLOAT;
        dataTypes[6] = PersistentDataType.DOUBLE;
        dataTypes[7] = PersistentDataType.BYTE_ARRAY;
        dataTypes[8] = PersistentDataType.INTEGER_ARRAY;
        dataTypes[9] = PersistentDataType.LONG_ARRAY;
        dataTypes[10] = PersistentDataType.TAG_CONTAINER;
    }

    public BukkitPersistentDataContainer(PersistentDataContainer container) {
        bukkitContainer = container;
    }

    @Override
    public void set(String key, EsPersistentDataType type, Object val) {
        bukkitContainer.set(Objects.requireNonNull(getNamespacedKey(key)), BukkitHelper.toBukkitPersistentDataType(type), val);
    }

    @Override
    public Object get(String key, EsPersistentDataType type) {
        return bukkitContainer.get(Objects.requireNonNull(getNamespacedKey(key)), BukkitHelper.toBukkitPersistentDataType(type));
    }

    @Override
    public void remove(String key) {
        bukkitContainer.remove(Objects.requireNonNull(getNamespacedKey(key)));
    }

    @Override
    public boolean has(String key) {
        if (Main.minecraftVersion.isLowerThan(1, 20, 4)) {
            EsPersistentDataType type = getType(key);
            return bukkitContainer.has(Objects.requireNonNull(getNamespacedKey(key)), BukkitHelper.toBukkitPersistentDataType(type));
        }

        return bukkitContainer.has(Objects.requireNonNull(getNamespacedKey(key)));
    }

    @Override
    public Set<String> getKeys() {
        Set<NamespacedKey> bukkitKeys = bukkitContainer.getKeys();
        Set<String> keys = new HashSet<>(bukkitKeys.size());
        for (NamespacedKey bukkitKey : bukkitKeys) {
            keys.add(bukkitKey.toString());
        }

        return keys;
    }

    @Override
    public EsPersistentDataType getType(String key) {
        for (PersistentDataType<?, ?> type : dataTypes) {
            try {
                Object value = bukkitContainer.get(Objects.requireNonNull(getNamespacedKey(key)), type);
                if (value != null) {
                    return BukkitHelper.fromBukkitPersistentDataType(type);
                }
            } catch (IllegalArgumentException ignored) {}
        }

        return null;
    }
}

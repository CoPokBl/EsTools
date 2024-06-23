package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.EsPersistentDataType;
import net.serble.estools.ServerApi.Implementations.Folia.FoliaHelper;
import net.serble.estools.ServerApi.Interfaces.EsPersistentDataContainer;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Objects;

import static net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper.*;

@SuppressWarnings("unchecked")  // Trust me bro
public class BukkitPersistentDataContainer implements EsPersistentDataContainer {
    private final PersistentDataContainer bukkitContainer;

    public BukkitPersistentDataContainer(PersistentDataContainer container) {
        bukkitContainer = container;
    }

    @Override
    public void set(String key, EsPersistentDataType type, Object val) {
        bukkitContainer.set(Objects.requireNonNull(getNamespacedKey(key)), FoliaHelper.toBukkitPersistentDataType(type), val);
    }

    @Override
    public Object get(String key, EsPersistentDataType type) {
        return bukkitContainer.get(Objects.requireNonNull(getNamespacedKey(key)), FoliaHelper.toBukkitPersistentDataType(type));
    }

    @Override
    public void remove(String key) {
        bukkitContainer.remove(Objects.requireNonNull(getNamespacedKey(key)));
    }
}

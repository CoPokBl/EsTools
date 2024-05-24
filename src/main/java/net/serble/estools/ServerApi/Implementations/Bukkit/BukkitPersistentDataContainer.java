package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsPersistentDataType;
import net.serble.estools.ServerApi.Interfaces.EsPersistentDataContainer;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;

import static net.serble.estools.ServerApi.Implementations.Bukkit.BukkitHelper.*;

@SuppressWarnings("unchecked")  // Trust me bro
public class BukkitPersistentDataContainer implements EsPersistentDataContainer {
    private final PersistentDataContainer bukkitContainer;

    public BukkitPersistentDataContainer(PersistentDataContainer container) {
        bukkitContainer = container;
    }

    @Override
    public void set(String key, EsPersistentDataType type, Object val) {
        bukkitContainer.set(getNamespacedKey(key), BukkitHelper.toBukkitPersistentDataType(type), val);
    }

    @Override
    public Object get(String key, EsPersistentDataType type) {
        return null;
    }
}

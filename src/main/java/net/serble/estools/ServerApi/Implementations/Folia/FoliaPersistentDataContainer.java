package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitPersistentDataContainer;
import org.bukkit.persistence.PersistentDataContainer;

public class FoliaPersistentDataContainer extends BukkitPersistentDataContainer {
    private final PersistentDataContainer bukkitContainer;

    public FoliaPersistentDataContainer(PersistentDataContainer container) {
        super(container);
        bukkitContainer = container;
    }
}

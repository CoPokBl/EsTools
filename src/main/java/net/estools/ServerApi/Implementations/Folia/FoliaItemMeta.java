package net.estools.ServerApi.Implementations.Folia;

import net.estools.ServerApi.Implementations.Bukkit.BukkitItemMeta;
import net.estools.ServerApi.Interfaces.EsPersistentDataContainer;
import org.bukkit.inventory.meta.ItemMeta;

public class FoliaItemMeta extends BukkitItemMeta {
    private final ItemMeta bukkitMeta;

    public FoliaItemMeta(ItemMeta meta) {
        super(meta);
        bukkitMeta = meta;
    }

    @Override
    public EsPersistentDataContainer getPersistentDataContainer() {
        return new FoliaPersistentDataContainer(bukkitMeta.getPersistentDataContainer());
    }
}

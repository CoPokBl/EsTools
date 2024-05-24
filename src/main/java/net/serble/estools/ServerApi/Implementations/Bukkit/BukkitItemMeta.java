package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class BukkitItemMeta implements EsItemMeta {
    private final ItemMeta bukkitMeta;

    public BukkitItemMeta(ItemMeta meta) {
        bukkitMeta = meta;
    }

    @Override
    public void setUnbreakable(boolean val) {
        bukkitMeta.setUnbreakable(val);
    }

    @Override
    public boolean isUnbreakable() {
        return bukkitMeta.isUnbreakable();
    }

    public ItemMeta getBukkitMeta() {
        return bukkitMeta;
    }
}

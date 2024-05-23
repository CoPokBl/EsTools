package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsItemMeta;

public class BukkitItemMeta implements EsItemMeta {
    private final org.bukkit.inventory.meta.ItemMeta bukkitMeta;

    public BukkitItemMeta(org.bukkit.inventory.meta.ItemMeta meta) {
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

    public org.bukkit.inventory.meta.ItemMeta getBukkitMeta() {
        return bukkitMeta;
    }
}

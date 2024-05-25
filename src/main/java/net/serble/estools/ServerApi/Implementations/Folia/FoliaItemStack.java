package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitItemStack;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Material;

public class FoliaItemStack extends BukkitItemStack {
    private final org.bukkit.inventory.ItemStack bukkitItem;

    public FoliaItemStack(String mat, int amount) {
        super(mat, amount);
        bukkitItem = new org.bukkit.inventory.ItemStack(Material.valueOf(mat), amount);
    }

    public FoliaItemStack(org.bukkit.inventory.ItemStack child) {
        super(child);
        bukkitItem = child;
    }

    @Override
    public EsItemMeta getItemMeta() {
        return new FoliaItemMeta(bukkitItem.getItemMeta());
    }

    @Override
    public EsItemStack clone() {
        return new FoliaItemStack(bukkitItem.clone());
    }
}

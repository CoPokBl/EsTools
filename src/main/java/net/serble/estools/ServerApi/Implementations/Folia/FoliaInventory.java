package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitInventory;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.inventory.Inventory;

public class FoliaInventory extends BukkitInventory {
    private final Inventory bukkitInv;

    public FoliaInventory(Inventory inv) {
        super(inv);
        bukkitInv = inv;
    }

    @Override
    public EsItemStack[] getContents() {
        org.bukkit.inventory.ItemStack[] bukkitItems = bukkitInv.getContents();
        EsItemStack[] items = new EsItemStack[bukkitItems.length];
        for (int i = 0; i < bukkitItems.length; i++) {
            if (bukkitItems[i] == null) {
                items[i] = null;
                continue;
            }
            items[i] = new FoliaItemStack(bukkitItems[i]);
        }
        return items;
    }

    @Override
    public EsItemStack getItem(int slot) {
        return new FoliaItemStack(bukkitInv.getItem(slot));
    }
}

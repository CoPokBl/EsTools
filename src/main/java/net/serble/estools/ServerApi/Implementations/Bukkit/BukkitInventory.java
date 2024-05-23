package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BukkitInventory implements EsInventory {
    private final org.bukkit.inventory.Inventory bukkitInv;

    public BukkitInventory(Inventory inv) {
        bukkitInv = inv;
    }

    @Override
    public void setItem(int slot, EsItemStack item) {
        bukkitInv.setItem(slot, ((BukkitItemStack) item).getBukkitItem());
    }

    @Override
    public EsItemStack getItem(int slot) {
        return new BukkitItemStack(bukkitInv.getItem(slot));
    }

    @Override
    public void addItem(EsItemStack stack) {
        bukkitInv.addItem(((BukkitItemStack) stack).getBukkitItem());
    }

    @Override
    public void setContents(EsItemStack[] items) {
        ItemStack[] bukkitItems = new ItemStack[items.length];
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                bukkitItems[i] = null;
                continue;
            }
            bukkitItems[i] = ((BukkitItemStack) items[i]).getBukkitItem();
        }
        bukkitInv.setContents(bukkitItems);
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
            items[i] = new BukkitItemStack(bukkitItems[i]);
        }
        return items;
    }

    @Override
    public void clear() {
        bukkitInv.clear();
    }

    public Inventory getBukkitInventory() {
        return bukkitInv;
    }
}

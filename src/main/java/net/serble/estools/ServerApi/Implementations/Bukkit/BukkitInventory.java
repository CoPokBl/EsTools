package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BukkitInventory implements EsInventory {
    private final org.bukkit.inventory.Inventory bukkitInv;

    public BukkitInventory(Inventory inv) {
        bukkitInv = inv;
        if (inv == null) {
            throw new NullPointerException("inv");
        }
    }

    public BukkitInventory() {
        bukkitInv = null;
    }

    @Override
    public void setItem(int slot, EsItemStack item) {
        ItemStack stack = item == null ? null : ((BukkitItemStack) item).getBukkitItem();
        bukkitInv.setItem(slot, stack);
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

    @Override
    public boolean isEqualTo(EsInventory inv) {
        if (inv == null) {
            return false;
        }
        return ((BukkitInventory) inv).getBukkitInventory().equals(bukkitInv);
    }

    @Override
    public Map<Integer, EsItemStack> all(String material) {
        Material mat = Material.valueOf(material);
        Map<Integer, EsItemStack> out = new HashMap<>();

        for (Map.Entry<Integer, ? extends ItemStack> entry : bukkitInv.all(mat).entrySet()) {
            out.put(entry.getKey(), new BukkitItemStack(entry.getValue()));
        }

        return out;
    }
}
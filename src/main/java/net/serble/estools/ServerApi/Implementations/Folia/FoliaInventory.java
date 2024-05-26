package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FoliaInventory implements EsInventory {
    private final Inventory bukkitInv;

    public FoliaInventory(Inventory inv) {
        bukkitInv = inv;
    }

    public Inventory getBukkitInventory() {
        return bukkitInv;
    }

    @Override
    public EsItemStack[] getContents() {
        ItemStack[] bukkitItems = bukkitInv.getContents();
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

    @Override
    public boolean isEqualTo(EsInventory inv) {
        if (inv == null) {
            return false;
        }
        return ((FoliaInventory) inv).getBukkitInventory().equals(bukkitInv);
    }

    @Override
    public Map<Integer, EsItemStack> all(String material) {
        Material mat = Material.valueOf(material);
        Map<Integer, EsItemStack> out = new HashMap<>();

        for (Map.Entry<Integer, ? extends ItemStack> entry : bukkitInv.all(mat).entrySet()) {
            out.put(entry.getKey(), new FoliaItemStack(entry.getValue()));
        }

        return out;
    }

    @Override
    public void setContents(EsItemStack[] items) {
        ItemStack[] bukkitItems = new ItemStack[items.length];
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                bukkitItems[i] = null;
                continue;
            }
            bukkitItems[i] = ((FoliaItemStack) items[i]).getBukkitItem();
        }
        bukkitInv.setContents(bukkitItems);
    }

    @Override
    public void setItem(int slot, EsItemStack item) {
        ItemStack stack = item == null ? null : ((FoliaItemStack) item).getBukkitItem();
        bukkitInv.setItem(slot, stack);
    }

    @Override
    public void addItem(EsItemStack stack) {
        bukkitInv.addItem(((FoliaItemStack) stack).getBukkitItem());
    }

    @Override
    public void clear() {
        bukkitInv.clear();
    }
}

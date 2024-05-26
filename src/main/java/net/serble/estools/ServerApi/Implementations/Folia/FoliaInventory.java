package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitInventory;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public boolean isEqualTo(EsInventory inv) {
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
}

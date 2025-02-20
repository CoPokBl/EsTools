package net.estools.ServerApi.Implementations.Folia;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Implementations.Bukkit.BukkitItemStack;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Material;
import org.bukkit.World;
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
            items[i] = FoliaHelper.fromBukkitItem(bukkitItems[i]);
        }
        return items;
    }

    @Override
    public EsItemStack getItem(int slot) {
        return FoliaHelper.fromBukkitItem(bukkitInv.getItem(slot));
    }

    @Override
    public boolean isEqualTo(EsInventory inv) {
        if (inv == null) {
            return false;
        }
        return ((FoliaInventory) inv).getBukkitInventory().equals(bukkitInv);
    }

    @Override
    public int getSize() {
        return bukkitInv.getSize();
    }

    @Override
    public Map<Integer, EsItemStack> all(EsMaterial material) {
        Material mat = BukkitHelper.toBukkitMaterial(material);
        Map<Integer, EsItemStack> out = new HashMap<>();

        for (Map.Entry<Integer, ? extends ItemStack> entry : bukkitInv.all(mat).entrySet()) {
            out.put(entry.getKey(), FoliaHelper.fromBukkitItem(entry.getValue()));
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
    public void addItemOrDrop(EsItemStack stack, EsLocation location) {
        HashMap<Integer, ItemStack> failed = bukkitInv.addItem(((BukkitItemStack) stack).getBukkitItem());
        for (ItemStack item : failed.values()) {
            ((World) location.getWorld()).dropItemNaturally(BukkitHelper.toBukkitLocation(location), item);
        }
    }

    @Override
    public void clear() {
        bukkitInv.clear();
    }
}

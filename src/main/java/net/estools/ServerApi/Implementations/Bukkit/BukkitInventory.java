package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BukkitInventory implements EsInventory {
    private final @NotNull Inventory bukkitInv;

    @SuppressWarnings("ConstantValue")
    public BukkitInventory(@NotNull Inventory inv) {
        bukkitInv = inv;
        if (inv == null) {
            throw new NullPointerException("inv");
        }
    }

    @Override
    public void setItem(int slot, EsItemStack item) {
        ItemStack stack = item == null ? null : ((BukkitItemStack) item).getBukkitItem();
        bukkitInv.setItem(slot, stack);
    }

    @Override
    public EsItemStack getItem(int slot) {
        return BukkitHelper.fromBukkitItem(bukkitInv.getItem(slot));
    }

    @Override
    public void addItem(EsItemStack stack) {
        bukkitInv.addItem(((BukkitItemStack) stack).getBukkitItem());
    }

    @Override
    public void addItemOrDrop(EsItemStack stack, EsLocation location) {
        HashMap<Integer, ItemStack> failed = bukkitInv.addItem(((BukkitItemStack) stack).getBukkitItem());

        Location loc = BukkitHelper.toBukkitLocation(location);
        World world = Objects.requireNonNull(loc.getWorld());

        for (ItemStack item : failed.values()) {
            world.dropItemNaturally(loc, item);
        }
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
            items[i] = BukkitHelper.fromBukkitItem(bukkitItems[i]);
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
    public int getSize() {
        return bukkitInv.getSize();
    }

    @Override
    public Map<Integer, EsItemStack> all(EsMaterial material) {
        Material mat = BukkitHelper.toBukkitMaterial(material);
        Map<Integer, EsItemStack> out = new HashMap<>();

        for (Map.Entry<Integer, ? extends ItemStack> entry : bukkitInv.all(mat).entrySet()) {
            out.put(entry.getKey(), BukkitHelper.fromBukkitItem(entry.getValue()));
        }

        return out;
    }
}

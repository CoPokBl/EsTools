package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.EsEquipmentSlot;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayerInventory;
import org.bukkit.inventory.PlayerInventory;

public class BukkitPlayerInventory extends BukkitInventory implements EsPlayerInventory {
    private final PlayerInventory bukkitInv;

    public BukkitPlayerInventory(PlayerInventory inv) {
        super(inv);
        bukkitInv = inv;
    }

    public BukkitPlayerInventory() {
        bukkitInv = null;
    }

    @Override
    public PlayerInventory getBukkitInventory() {
        return bukkitInv;
    }

    @Override
    public EsItemStack getOffHand() {
        return BukkitHelper.fromBukkitItem(bukkitInv.getItemInOffHand());
    }

    @Override
    public EsItemStack getMainHand() {
        return BukkitHelper.fromBukkitItem(bukkitInv.getItemInMainHand());
    }

    @Override
    public EsItemStack getHelmet() {
        return BukkitHelper.fromBukkitItem(bukkitInv.getHelmet());
    }

    @Override
    public EsItemStack getLeggings() {
        return BukkitHelper.fromBukkitItem(bukkitInv.getLeggings());
    }

    @Override
    public EsItemStack getChestplate() {
        return BukkitHelper.fromBukkitItem(bukkitInv.getChestplate());
    }

    @Override
    public EsItemStack getBoots() {
        return BukkitHelper.fromBukkitItem(bukkitInv.getBoots());
    }

    @Override
    public void setItem(EsEquipmentSlot slot, EsItemStack item) {
        bukkitInv.setItem(BukkitHelper.toBukkitEquipmentSlot(slot), ((BukkitItemStack) item).getBukkitItem());
    }
}

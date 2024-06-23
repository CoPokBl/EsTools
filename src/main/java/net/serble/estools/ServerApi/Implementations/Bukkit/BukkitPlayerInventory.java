package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEquipmentSlot;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitEquipmentSlotHelper;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayerInventory;
import org.bukkit.inventory.Inventory;
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
        if (Main.minecraftVersion.getMinor() <= 14) {
            int slotId;
            switch (slot) {
                case Head:
                    slotId = 5;
                    break;

                case Chest:
                    slotId = 6;
                    break;

                case Legs:
                    slotId = 7;
                    break;

                case Feet:
                    slotId = 8;
                    break;

                case Hand:
                    slotId = bukkitInv.getHeldItemSlot();
                    break;

                case OffHand:
                    slotId = 40;
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + slot);
            }
            ((Inventory) bukkitInv).setItem(slotId, ((BukkitItemStack) item).getBukkitItem());
            return;
        }
        bukkitInv.setItem(BukkitEquipmentSlotHelper.toBukkitEquipmentSlot(slot), ((BukkitItemStack) item).getBukkitItem());
    }
}

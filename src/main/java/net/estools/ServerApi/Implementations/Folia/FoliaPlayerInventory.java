package net.estools.ServerApi.Implementations.Folia;

import net.estools.ServerApi.EsEquipmentSlot;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayerInventory;
import org.bukkit.inventory.PlayerInventory;

public class FoliaPlayerInventory extends FoliaInventory implements EsPlayerInventory {
    private final PlayerInventory bukkitInv;

    public FoliaPlayerInventory(PlayerInventory inv) {
        super(inv);
        bukkitInv = inv;
    }

    @Override
    public PlayerInventory getBukkitInventory() {
        return bukkitInv;
    }

    @Override
    public EsItemStack getOffHand() {
        return FoliaHelper.fromBukkitItem(bukkitInv.getItemInOffHand());
    }

    @Override
    public EsItemStack getMainHand() {
        return FoliaHelper.fromBukkitItem(bukkitInv.getItemInMainHand());
    }

    @Override
    public EsItemStack getHelmet() {
        return FoliaHelper.fromBukkitItem(bukkitInv.getHelmet());
    }

    @Override
    public EsItemStack getLeggings() {
        return FoliaHelper.fromBukkitItem(bukkitInv.getLeggings());
    }

    @Override
    public EsItemStack getChestplate() {
        return FoliaHelper.fromBukkitItem(bukkitInv.getChestplate());
    }

    @Override
    public EsItemStack getBoots() {
        return FoliaHelper.fromBukkitItem(bukkitInv.getBoots());
    }

    @Override
    public void setItem(EsEquipmentSlot slot, EsItemStack item) {
        bukkitInv.setItem(FoliaHelper.toBukkitEquipmentSlot(slot), ((FoliaItemStack) item).getBukkitItem());
    }
}

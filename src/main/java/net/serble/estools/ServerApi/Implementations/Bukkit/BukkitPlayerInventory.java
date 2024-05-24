package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayerInventory;
import org.bukkit.inventory.PlayerInventory;

public class BukkitPlayerInventory extends BukkitInventory implements EsPlayerInventory {
    private final PlayerInventory bukkitInv;

    public BukkitPlayerInventory(PlayerInventory inv) {
        super(inv);
        bukkitInv = inv;
    }

    @Override
    public PlayerInventory getBukkitInventory() {
        return bukkitInv;
    }

    @Override
    public EsItemStack getOffHand() {
        return new BukkitItemStack(bukkitInv.getItemInOffHand());
    }

    @Override
    public EsItemStack getMainHand() {
        return new BukkitItemStack(bukkitInv.getItemInMainHand());
    }

    @Override
    public EsItemStack getHelmet() {
        return new BukkitItemStack(bukkitInv.getHelmet());
    }

    @Override
    public EsItemStack getLeggings() {
        return new BukkitItemStack(bukkitInv.getLeggings());
    }

    @Override
    public EsItemStack getChestplate() {
        return new BukkitItemStack(bukkitInv.getChestplate());
    }

    @Override
    public EsItemStack getBoots() {
        return new BukkitItemStack(bukkitInv.getBoots());
    }
}

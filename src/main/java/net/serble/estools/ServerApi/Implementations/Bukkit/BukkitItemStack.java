package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Material;
import org.bukkit.Registry;

import java.util.Objects;

public class BukkitItemStack implements EsItemStack {
    private final org.bukkit.inventory.ItemStack bukkitItem;

    public BukkitItemStack(String mat, int amount) {
        bukkitItem = new org.bukkit.inventory.ItemStack(Material.valueOf(mat), amount);
    }

    public BukkitItemStack(org.bukkit.inventory.ItemStack child) {
        bukkitItem = child;
    }

    public org.bukkit.inventory.ItemStack getBukkitItem() {
        return bukkitItem;
    }

    @Override
    public String getType() {
        return bukkitItem.getType().name();
    }

    @Override
    public void setType(String val) {
        bukkitItem.setType(Material.valueOf(val));
    }

    @Override
    public int getAmount() {
        return bukkitItem.getAmount();
    }

    @Override
    public void setAmount(int val) {
        bukkitItem.setAmount(val);
    }

    @Override
    public void addEnchantment(String enchantment, int level) {
        bukkitItem.addUnsafeEnchantment(Objects.requireNonNull(Registry.ENCHANTMENT.match(enchantment)), level);
    }

    @Override
    public EsItemMeta getItemMeta() {
        return new BukkitItemMeta(bukkitItem.getItemMeta());
    }

    @Override
    public void setItemMeta(EsItemMeta meta) {
        bukkitItem.setItemMeta(((BukkitItemMeta) meta).getBukkitMeta());
    }
}

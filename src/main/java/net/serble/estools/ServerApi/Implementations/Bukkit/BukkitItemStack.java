package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class BukkitItemStack implements EsItemStack {
    private final org.bukkit.inventory.ItemStack bukkitItem;

    public BukkitItemStack(String mat, int amount) {
        bukkitItem = new ItemStack(Material.valueOf(mat), amount);
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
        bukkitItem.addUnsafeEnchantment(BukkitHelper.getBukkitEnchantment(enchantment), level);
    }

    @Override
    public void removeEnchantment(String enchantment) {
        bukkitItem.removeEnchantment(BukkitHelper.getBukkitEnchantment(enchantment));
    }

    @Override
    public EsItemMeta getItemMeta() {
        return new BukkitItemMeta(bukkitItem.getItemMeta());
    }

    @Override
    public void setItemMeta(EsItemMeta meta) {
        bukkitItem.setItemMeta(((BukkitItemMeta) meta).getBukkitMeta());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setDamage(int val) {
        if (Main.minecraftVersion.getMinor() > 12) {
            ItemMeta meta = bukkitItem.getItemMeta();
            if (meta instanceof Damageable) {
                ((Damageable) meta).setDamage(val);
            }
        } else {
            if (val > Short.MAX_VALUE) {
                val = Short.MAX_VALUE;
            }

            bukkitItem.setDurability((short) val);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getDamage() {
        if (Main.minecraftVersion.getMinor() > 12) {
            return bukkitItem.getDurability();
        }

        ItemMeta meta = bukkitItem.getItemMeta();
        if (meta instanceof Damageable) {
            return ((Damageable) meta).getDamage();
        }
        return 0;
    }

    @Override
    public EsItemStack clone() {
        return new BukkitItemStack(bukkitItem.clone());
    }

    @Override
    public boolean isSimilar(EsItemStack stack) {
        return ((BukkitItemStack) stack).getBukkitItem().isSimilar(bukkitItem);
    }

    @Override
    public int getMaxStackSize() {
        return bukkitItem.getMaxStackSize();
    }
}

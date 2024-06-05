package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class BukkitItemStack implements EsItemStack {
    private final ItemStack bukkitItem;

    public BukkitItemStack(String mat, int amount) {
        bukkitItem = new ItemStack(Material.valueOf(mat), amount);
    }

    public BukkitItemStack(ItemStack child) {
        bukkitItem = child;
    }

    public ItemStack getBukkitItem() {
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
            bukkitItem.setItemMeta(meta);
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

    @SuppressWarnings("MethodDoesntCallSuperMethod")  // I don't care, it doesn't work like that
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

    @Override
    public Object getInternalObject() {
        return bukkitItem;
    }

    @Override
    public Map<String, Integer> getEnchantments() {
        Map<String, Integer> enchs = new HashMap<>();
        for (Map.Entry<Enchantment, Integer> ench : bukkitItem.getEnchantments().entrySet()) {
            enchs.put(BukkitHelper.fromBukkitEnchantment(ench.getKey()), ench.getValue());
        }

        return enchs;
    }
}

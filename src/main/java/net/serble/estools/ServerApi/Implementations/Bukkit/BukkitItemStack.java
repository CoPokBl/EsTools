package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEnchantment;
import net.serble.estools.ServerApi.EsMaterial;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitEnchantmentHelper;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BukkitItemStack implements EsItemStack {
    private final ItemStack bukkitItem;

    public BukkitItemStack(EsMaterial mat, int amount) {
        bukkitItem = new ItemStack(BukkitHelper.toBukkitMaterial(mat), amount);
    }

    public BukkitItemStack(ItemStack child) {
        bukkitItem = child;
    }

    public ItemStack getBukkitItem() {
        return bukkitItem;
    }

    @Override
    public EsMaterial getType() {
        return BukkitHelper.fromBukkitMaterial(bukkitItem.getType());
    }

    @Override
    public void setType(EsMaterial val) {
        bukkitItem.setType(BukkitHelper.toBukkitMaterial(val));
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
    public void addEnchantment(EsEnchantment enchantment, int level) {
        bukkitItem.addUnsafeEnchantment(BukkitEnchantmentHelper.toBukkitEnchantment(enchantment), level);
    }

    @Override
    public void removeEnchantment(EsEnchantment enchantment) {
        bukkitItem.removeEnchantment(BukkitEnchantmentHelper.toBukkitEnchantment(enchantment));
    }

    @Override
    public EsItemMeta getItemMeta() {
        return new BukkitItemMeta(bukkitItem.getItemMeta());
    }

    @Override
    public String exportItemMeta() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("item", bukkitItem.getItemMeta());
        return config.saveToString();
    }

    @Override
    public void importItemMeta(String meta) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(meta);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        ItemMeta itemMeta = (ItemMeta)config.get("item");
        bukkitItem.setItemMeta(itemMeta);
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
        return BukkitHelper.fromBukkitItem(bukkitItem.clone());
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
    public Map<EsEnchantment, Integer> getEnchantments() {
        Set<Map.Entry<Enchantment, Integer>> bEnchs = bukkitItem.getEnchantments().entrySet();
        Map<EsEnchantment, Integer> enchs = new HashMap<>(bEnchs.size());
        for (Map.Entry<Enchantment, Integer> ench : bEnchs) {
            enchs.put(BukkitEnchantmentHelper.fromBukkitEnchantment(ench.getKey()), ench.getValue());
        }

        return enchs;
    }
}

package net.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.Implementations.Bukkit.BukkitItemMeta;
import net.estools.ServerApi.Interfaces.EsItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

/**
 * Min version: 1.4
 */
public class BukkitMetaHelper {

    public static void setPotionType(ItemStack pot, EsPotionEffect effect) {
        PotionMeta meta = (PotionMeta) pot.getItemMeta();
        assert meta != null;
        meta.addCustomEffect(BukkitHelper.toBukkitPotionEffect(effect), true);
        pot.setItemMeta(meta);
    }

    public static EsItemMeta fromBukkitItemMeta(ItemMeta meta) {
        if (meta == null) {
            return null;
        }

        return new BukkitItemMeta(meta);
    }
}

package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.ServerApi.EsPotionEffect;
import org.bukkit.inventory.ItemStack;
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
}

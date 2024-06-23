package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsPotType;
import net.serble.estools.ServerApi.EsPotionEffect;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitPotion;
import net.serble.estools.ServerApi.Interfaces.EsPotion;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 * Min version: 1.4
 */
@SuppressWarnings("deprecation")  // Old versions go brrrr
public class BukkitPotionsHelper {

    /** Used for 1.4-1.8 */
    public static EsPotion createPotion1_4(EsPotType potType, EsPotionEffect effect, int amount) {
        PotionType type = BukkitEffectHelper.getPotionFromEffectType(effect.getType());
        if (type == null) { // This can fail if the effect doesn't have a potion for it
            return null;
        }

        // in 1.7 and below potions start at amplifier 1, and can only be 1 or 2, after that it starts at 0
        int amplifier = effect.getAmp();
        if (Main.minecraftVersion.getMinor() <= 8) {
            amplifier++;
            if (amplifier < 1 || amplifier > 2) {
                return null;
            }
        }

        Potion potion = new Potion(type, amplifier);
        potion.setSplash(potType == EsPotType.splash);

        return new BukkitPotion(potion.toItemStack(amount));
    }

    /** Used for 1.4-1.8 */
    public static EsPotion createPotion1_4(EsPotType potType) {
        Potion potion = Potion.fromItemStack(new ItemStack(Material.valueOf("POTION")));
        potion.setSplash(potType == EsPotType.splash);

        return new BukkitPotion(potion.toItemStack(1));
    }
}

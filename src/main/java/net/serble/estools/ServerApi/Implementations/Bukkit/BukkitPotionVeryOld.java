package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Bitmask;
import net.serble.estools.ServerApi.EsPotType;
import net.serble.estools.ServerApi.EsPotionEffect;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitPotionsHelper1_3;
import net.serble.estools.ServerApi.Interfaces.EsPotion;
import org.bukkit.inventory.ItemStack;

/**
 * Potions didn't used to have special code qualities in 1.3 and prior.
 * The potion was dictated by its durability value.
 * So in this case we need just a simple wrapper of BukkitItemStack that does
 * nothing extra.
 */
@SuppressWarnings("deprecation")  // This class is for 1.3 and below
public class BukkitPotionVeryOld extends BukkitItemStack implements EsPotion {
    private final Bitmask data;
    private static final short splashBit = 14;  // Decimal val: 16384. 2^14=16384
    private static final short potionNameBits = 6;  // There are max of 64 vals, 2^6=64 https://minecraft.wiki/w/Java_Edition_data_values/Pre-flattening#%22Potion_name%22_bits

    public BukkitPotionVeryOld(ItemStack is) {
        super(is);
        data = new Bitmask(is.getDurability());
    }

    @Override
    public EsPotionEffect[] getEffects() {
        return new EsPotionEffect[] {
                new EsPotionEffect(
                        BukkitPotionsHelper1_3.getEffectTypeFromRawId((short) data.getValueOfFirstBits(potionNameBits)),
                        0, 1)  // Amp and duration are ignored because they can't be customised
        };
    }

    @Override
    public EsPotType getPotionType() {
        if (data.getBit(splashBit)) {
            return EsPotType.splash;
        }
        return EsPotType.drink;
    }

    @Override
    public void addEffect(EsPotionEffect effect) {
        // This isn't possible because custom effects don't exist
    }
}

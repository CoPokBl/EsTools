package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.ServerApi.EsPotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BukkitPotionsHelper1_3 {
    private static final Map<EsPotionEffectType, Short> POTIONS = new HashMap<>();

    static {  // Potion Type IDs https://minecraft.wiki/w/Java_Edition_data_values/Pre-flattening#%22Potion_effect%22_bits
        POTIONS.put(EsPotionEffectType.createUnchecked("regeneration"), (short) 1);
        POTIONS.put(EsPotionEffectType.createUnchecked("speed"), (short) 2);
        POTIONS.put(EsPotionEffectType.createUnchecked("fire_resistance"), (short) 3);
        POTIONS.put(EsPotionEffectType.createUnchecked("poison"), (short) 4);
        POTIONS.put(EsPotionEffectType.createUnchecked("instant_health"), (short) 5);
        POTIONS.put(EsPotionEffectType.createUnchecked("weakness"), (short) 8);
        POTIONS.put(EsPotionEffectType.createUnchecked("strength"), (short) 9);
        POTIONS.put(EsPotionEffectType.createUnchecked("slowness"), (short) 10);
        POTIONS.put(EsPotionEffectType.createUnchecked("instant_damage"), (short) 12);
    }

    public static short getPotionIdFromEffectType(EsPotionEffectType type) {
        return POTIONS.get(type);
    }

    public static EsPotionEffectType getEffectTypeFromRawId(short id) {
        for (Map.Entry<EsPotionEffectType, Short> entry : POTIONS.entrySet()) {
            if (entry.getValue() == id) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Set<EsPotionEffectType> getPotionList() {
        return POTIONS.keySet();
    }
}

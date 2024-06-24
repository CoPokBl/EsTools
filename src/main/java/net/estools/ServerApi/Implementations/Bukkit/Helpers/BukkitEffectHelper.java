package net.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.estools.Main;
import net.estools.ServerApi.EsPotionEffectType;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BukkitEffectHelper {
    private static final Map<EsPotionEffectType, PotionType> OLD_POTIONS = new HashMap<>();

    // Potion effect types, because getKey() doesnt exist pre 1.18
    private static final Map<EsPotionEffectType, PotionEffectType> ES_TO_BUKKIT = new HashMap<>();
    private static final Map<PotionEffectType, EsPotionEffectType> BUKKIT_TO_ES = new HashMap<>();

    public static PotionEffectType toBukkitEffectType(EsPotionEffectType effect) {
        return ES_TO_BUKKIT.get(effect);
    }

    public static EsPotionEffectType fromBukkitEffectType(PotionEffectType effect) {
        return BUKKIT_TO_ES.get(effect);
    }

    public static PotionType getPotionFromEffectType(EsPotionEffectType type) {
        return OLD_POTIONS.get(type);
    }

    public static Set<EsPotionEffectType> getEffectList() {
        return ES_TO_BUKKIT.keySet();
    }

    public static Set<EsPotionEffectType> getPotionList() {
        return OLD_POTIONS.keySet();
    }

    @SuppressWarnings("deprecation")  // It's for old versions
    public static void load() {
        // in 1.8 and below, there were no custom potions, just some hardcoded ones.
        // these are PotionTypes, and we need to generate them based on EsPotionEffectTypes
        if (Main.minecraftVersion.getMinor() <= 8) {
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("regeneration"), PotionType.REGEN);
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("speed"), PotionType.SPEED);
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("fire_resistance"), PotionType.FIRE_RESISTANCE);
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("poison"), PotionType.POISON);
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("instant_health"), PotionType.INSTANT_HEAL);
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("weakness"), PotionType.WEAKNESS);
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("strength"), PotionType.STRENGTH);
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("slowness"), PotionType.SLOWNESS);
            OLD_POTIONS.put(EsPotionEffectType.createUnchecked("instant_damage"), PotionType.INSTANT_DAMAGE);

            if (Main.minecraftVersion.getMinor() >= 4 && Main.minecraftVersion.getMinor() >= 2) {  // Night vision was added in 1.4.2
                OLD_POTIONS.put(EsPotionEffectType.createUnchecked("night_vision"), PotionType.NIGHT_VISION);
                OLD_POTIONS.put(EsPotionEffectType.createUnchecked("invisibility"), PotionType.INVISIBILITY);
            }

            if (Main.minecraftVersion.getMinor() >= 7 && Main.minecraftVersion.getMinor() >= 2) {  // 1.7.2
                OLD_POTIONS.put(EsPotionEffectType.createUnchecked("water_breathing"), PotionType.WATER);
            }

            if (Main.minecraftVersion.getMinor() >= 8) {  // 1.8
                OLD_POTIONS.put(EsPotionEffectType.createUnchecked("jump_boost"), PotionType.JUMP);
            }
        }

        final HashMap<String, String> nameReplacers = new HashMap<String, String>() {{
            put("confusion", "nausea");
            put("damage_resistance", "resistance");
            put("fast_digging", "haste");
            put("harm", "instant_damage");
            put("heal", "instant_health");
            put("increase_damage", "strength");
            put("slow", "slowness");
            put("slow_digging", "mining_fatigue");
            put("jump", "jump_boost");
        }};

        // Registry.EFFECT was added in 1.20.3...
        if (Main.minecraftVersion.isAtLeast(1, 20, 3)) {
            for (PotionEffectType type : Registry.EFFECT) {
                EsPotionEffectType esType = EsPotionEffectType.createUnchecked(type.getKey().getKey());
                ES_TO_BUKKIT.put(esType, type);
                BUKKIT_TO_ES.put(type, esType);
            }
        } else {
            for (PotionEffectType type : PotionEffectType.values()) {
                if (type == null) {
                    continue;
                }

                String name;
                if (Main.minecraftVersion.getMinor() >= 18) {
                    name = type.getKey().getKey();
                } else {
                    name = type.getName();

                    if (nameReplacers.containsKey(name)) {
                        name = nameReplacers.get(name);
                    }
                }

                EsPotionEffectType esType = EsPotionEffectType.createUnchecked(name);
                ES_TO_BUKKIT.put(esType, type);
                BUKKIT_TO_ES.put(type, esType);
            }
        }
    }
}

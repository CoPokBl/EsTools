package net.serble.estools;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Effects {
    private static final Map<String, PotionEffectType> EFFECTS = new HashMap<>();
    private static final Map<String, PotionType> POTIONS = new HashMap<>();

    public static PotionEffectType getByName(String name) {
        return EFFECTS.get(name.toLowerCase(Locale.ENGLISH));
    }

    public static PotionType getPotionByName(String name) {
        return POTIONS.get(name.toLowerCase(Locale.ENGLISH));
    }

    public static String getName(PotionEffectType effect) {
        for (Map.Entry<String, PotionEffectType> a : EFFECTS.entrySet()) {
            if (a.getValue().equals(effect)) {
                return a.getKey();
            }
        }

        return "invalid";
    }

    public static Set<Map.Entry<String, PotionEffectType>> entrySet() {
        return EFFECTS.entrySet();
    }

    public static Set<Map.Entry<String, PotionType>> potionEntrySet() {
        return POTIONS.entrySet();
    }

    static {
        if (Main.version <= 8) {  // 1.8 only potions be funky
            POTIONS.put("regeneration", PotionType.REGEN);
            POTIONS.put("speed", PotionType.SPEED);
            POTIONS.put("fire_resistance", PotionType.FIRE_RESISTANCE);
            POTIONS.put("poison", PotionType.POISON);
            POTIONS.put("instant_health", PotionType.INSTANT_HEAL);
            POTIONS.put("weakness", PotionType.WEAKNESS);
            POTIONS.put("strength", PotionType.STRENGTH);
            POTIONS.put("slowness", PotionType.SLOWNESS);
            POTIONS.put("instant_damage", PotionType.INSTANT_DAMAGE);

            if (Main.version >= 4 && Main.minorVersion >= 2) {  // Night vision was added in 1.4.2
                POTIONS.put("night_vision", PotionType.NIGHT_VISION);
                POTIONS.put("invisibility", PotionType.INVISIBILITY);
            }

            if (Main.version >= 7 && Main.minorVersion >= 2) {  // 1.7.2
                POTIONS.put("water_breathing", PotionType.WATER);
            }

            if (Main.version >= 8) {  // 1.8
                POTIONS.put("jump_boost", PotionType.JUMP);
            }
        }

        EFFECTS.put("blindness", PotionEffectType.BLINDNESS);
        EFFECTS.put("nausea", PotionEffectType.CONFUSION);
        EFFECTS.put("resistance", PotionEffectType.DAMAGE_RESISTANCE);
        EFFECTS.put("haste", PotionEffectType.FAST_DIGGING);
        EFFECTS.put("fire_resistance", PotionEffectType.FIRE_RESISTANCE);
        EFFECTS.put("instant_damage", PotionEffectType.HARM);
        EFFECTS.put("instant_health", PotionEffectType.HEAL);
        EFFECTS.put("hunger", PotionEffectType.HUNGER);
        EFFECTS.put("strength", PotionEffectType.INCREASE_DAMAGE);
        EFFECTS.put("invisibility", PotionEffectType.INVISIBILITY);
        EFFECTS.put("night_vision", PotionEffectType.NIGHT_VISION);
        EFFECTS.put("poison", PotionEffectType.POISON);
        EFFECTS.put("regeneration", PotionEffectType.REGENERATION);
        EFFECTS.put("slowness", PotionEffectType.SLOW);
        EFFECTS.put("mining_fatigue", PotionEffectType.SLOW_DIGGING);
        EFFECTS.put("speed", PotionEffectType.SPEED);
        EFFECTS.put("water_breathing", PotionEffectType.WATER_BREATHING);
        EFFECTS.put("weakness", PotionEffectType.WEAKNESS);

        if (Main.version >= 5) {
            EFFECTS.put("wither", PotionEffectType.WITHER);
        }

        if (Main.version >= 7) {
            EFFECTS.put("health_boost", PotionEffectType.HEALTH_BOOST);
            EFFECTS.put("absorption", PotionEffectType.ABSORPTION);
            EFFECTS.put("saturation", PotionEffectType.SATURATION);
        }

        if (Main.version >= 8) {
            EFFECTS.put("jump_boost", PotionEffectType.JUMP);
        }

        if (Main.version >= 9) {
            EFFECTS.put("levitation", PotionEffectType.LEVITATION);
            EFFECTS.put("glowing", PotionEffectType.GLOWING);
            EFFECTS.put("luck", PotionEffectType.LUCK);
            EFFECTS.put("unluck", PotionEffectType.UNLUCK);
        }

        if (Main.version >= 13) {
            EFFECTS.put("slow_falling", PotionEffectType.SLOW_FALLING);
            EFFECTS.put("conduit_power", PotionEffectType.CONDUIT_POWER);
            EFFECTS.put("dolphins_grace", PotionEffectType.DOLPHINS_GRACE);
        }

        if (Main.version >= 14) {
            EFFECTS.put("bad_omen", PotionEffectType.BAD_OMEN);
            EFFECTS.put("hero_of_the_village", PotionEffectType.HERO_OF_THE_VILLAGE);
        }
    }
}

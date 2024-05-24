package net.serble.estools;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.*;

public class Effects {
    private static final Map<String, String> EFFECTS = new HashMap<>();
    private static final Map<String, PotionType> POTIONS = new HashMap<>();

    public static String getByName(String name) {
        return EFFECTS.get(name);
    }

    public static PotionType getPotionByName(String name) {
        return POTIONS.get(name.toLowerCase(Locale.ENGLISH));
    }

    public static String getName(String effect) {
        for (Map.Entry<String, String> a : EFFECTS.entrySet()) {
            if (a.getValue().equals(effect)) {
                return a.getKey();
            }
        }

        return "invalid";
    }

    public static Set<Map.Entry<String, String>> entrySet() {
        return EFFECTS.entrySet();
    }

    public static Set<Map.Entry<String, PotionType>> getPotionList() {
        return POTIONS.entrySet();
    }

    public static void load() {
        if (Main.minecraftVersion.getMinor() <= 8) {  // 1.8 only potions be funky
            POTIONS.put("regeneration", PotionType.REGEN);
            POTIONS.put("speed", PotionType.SPEED);
            POTIONS.put("fire_resistance", PotionType.FIRE_RESISTANCE);
            POTIONS.put("poison", PotionType.POISON);
            POTIONS.put("instant_health", PotionType.INSTANT_HEAL);
            POTIONS.put("weakness", PotionType.WEAKNESS);
            POTIONS.put("strength", PotionType.STRENGTH);
            POTIONS.put("slowness", PotionType.SLOWNESS);
            POTIONS.put("instant_damage", PotionType.INSTANT_DAMAGE);

            if (Main.minecraftVersion.getMinor() >= 4 && Main.minecraftVersion.getMinor() >= 2) {  // Night vision was added in 1.4.2
                POTIONS.put("night_vision", PotionType.NIGHT_VISION);
                POTIONS.put("invisibility", PotionType.INVISIBILITY);
            }

            if (Main.minecraftVersion.getMinor() >= 7 && Main.minecraftVersion.getMinor() >= 2) {  // 1.7.2
                POTIONS.put("water_breathing", PotionType.WATER);
            }

            if (Main.minecraftVersion.getMinor() >= 8) {  // 1.8
                POTIONS.put("jump_boost", PotionType.JUMP);
            }
        }

        HashMap<String, String> nameReplacers = new HashMap<String, String>() {{
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

        for (String s : Main.server.getPotionEffectTypes()) {
            String name = s.toLowerCase();

            if (nameReplacers.containsKey(name)) {
                name = nameReplacers.get(name);
            }

            EFFECTS.put(name, s.toLowerCase());
        }
    }
}

package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

// This class exists because the registry doesn't exist pre 1.13, so all the enchantments names have to be hardcoded.
// only enchantments before 1.13 need to be here because this class isn't used past 1.13
public class BukkitEnchantmentsHelper {
    private static final Map<String, String> ENCHANTMENTS = new HashMap<>();

    /** Converts the natural vanilla names to the Bukkit enum values */
    public static String getByName(String name) {
        return ENCHANTMENTS.get(name.toLowerCase(Locale.ENGLISH));
    }

    public static String getNameFromValue(String val) {
        for (Map.Entry<String, String> entry : ENCHANTMENTS.entrySet()) {
            if (val.toLowerCase().equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static Set<Map.Entry<String, String>> entrySet() {
        return ENCHANTMENTS.entrySet();
    }

    static { AddEnchants(); }

    private static void AddEnchants() {
        ENCHANTMENTS.put("sharpness", "DAMAGE_ALL");
        ENCHANTMENTS.put("bane_of_arthropods", "DAMAGE_ARTHROPODS");
        ENCHANTMENTS.put("smite", "DAMAGE_UNDEAD");
        ENCHANTMENTS.put("efficiency", "DIG_SPEED");
        ENCHANTMENTS.put("unbreaking", "DURABILITY");
        ENCHANTMENTS.put("fire_aspect", "FIRE_ASPECT");
        ENCHANTMENTS.put("knockback", "KNOCKBACK");
        ENCHANTMENTS.put("looting", "LOOT_BONUS_MOBS");
        ENCHANTMENTS.put("respiration", "OXYGEN");
        ENCHANTMENTS.put("protection", "PROTECTION_ENVIRONMENTAL");
        ENCHANTMENTS.put("blast_protection", "PROTECTION_EXPLOSIONS");
        ENCHANTMENTS.put("feather_falling", "PROTECTION_FALL");
        ENCHANTMENTS.put("projectile_protection", "PROTECTION_PROJECTILE");
        ENCHANTMENTS.put("silk_touch", "SILK_TOUCH");
        ENCHANTMENTS.put("aqua_affinity", "WATER_WORKER");
        ENCHANTMENTS.put("flame", "ARROW_FIRE");
        ENCHANTMENTS.put("power", "ARROW_DAMAGE");
        ENCHANTMENTS.put("punch", "ARROW_KNOCKBACK");
        ENCHANTMENTS.put("infinity", "ARROW_INFINITE");
        ENCHANTMENTS.put("fortune", "LOOT_BONUS_BLOCKS");

        if (Main.minecraftVersion.getMinor() <= 3) return;
        ENCHANTMENTS.put("thorns", "THORNS");

        if (Main.minecraftVersion.getMinor() <= 6) return;
        ENCHANTMENTS.put("luck", "LUCK");
        ENCHANTMENTS.put("lure", "LURE");

        if (Main.minecraftVersion.getMinor() <= 7) return;
        ENCHANTMENTS.put("depth_strider", "DEPTH_STRIDER");

        if (Main.minecraftVersion.getMinor() <= 8) return;
        ENCHANTMENTS.put("frost_walker", "FROST_WALKER");
        ENCHANTMENTS.put("mending", "MENDING");

        if (Main.minecraftVersion.getMinor() <= 10) return;
        ENCHANTMENTS.put("binding_curse", "BINDING_CURSE");
        ENCHANTMENTS.put("vanishing_curse", "VANISHING_CURSE");
        ENCHANTMENTS.put("sweeping_edge", "SWEEPING_EDGE");
    }
}

package net.serble.estools;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

// This class exists because the registry doesn't exist pre 1.13, so all the enchantments names have to be hardcoded.
// only enchantments before 1.13 need to be here because this class isn't used past 1.13
public class Enchantments {
    private static final Map<String, String> ENCHANTMENTS = new HashMap<>();

    public static String getByName(String name) {
        return ENCHANTMENTS.get(name.toLowerCase(Locale.ENGLISH));
    }

    public static Set<Map.Entry<String, String>> entrySet() {
        return ENCHANTMENTS.entrySet();
    }

    static { AddEnchants(); }

    private static void AddEnchants() {
        ENCHANTMENTS.put("sharpness", "DAMAGE_ALL");
        ENCHANTMENTS.put("baneofarthropods", "DAMAGE_ARTHROPODS");
        ENCHANTMENTS.put("smite", "DAMAGE_UNDEAD");
        ENCHANTMENTS.put("efficiency", "DIG_SPEED");
        ENCHANTMENTS.put("unbreaking", "DURABILITY");
        ENCHANTMENTS.put("fireaspect", "FIRE_ASPECT");
        ENCHANTMENTS.put("knockback", "KNOCKBACK");
        ENCHANTMENTS.put("looting", "LOOT_BONUS_MOBS");
        ENCHANTMENTS.put("respiration", "OXYGEN");
        ENCHANTMENTS.put("protection", "PROTECTION_ENVIRONMENTAL");
        ENCHANTMENTS.put("blastprotect", "PROTECTION_EXPLOSIONS");
        ENCHANTMENTS.put("featherfall", "PROTECTION_FALL");
        ENCHANTMENTS.put("projectileprotection", "PROTECTION_PROJECTILE");
        ENCHANTMENTS.put("silktouch", "SILK_TOUCH");
        ENCHANTMENTS.put("aquaaffinity", "WATER_WORKER");
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
        ENCHANTMENTS.put("depthstrider", "DEPTH_STRIDER");

        if (Main.minecraftVersion.getMinor() <= 8) return;
        ENCHANTMENTS.put("frostwalker", "FROST_WALKER");
        ENCHANTMENTS.put("mending", "MENDING");

        if (Main.minecraftVersion.getMinor() <= 10) return;
        ENCHANTMENTS.put("bindingcurse", "BINDING_CURSE");
        ENCHANTMENTS.put("vanishingcurse", "VANISHING_CURSE");
        ENCHANTMENTS.put("sweepingedge", "SWEEPING_EDGE");
    }
}

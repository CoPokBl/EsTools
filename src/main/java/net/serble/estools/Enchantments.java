package net.serble.estools;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Enchantments {
    private static final Map<String, Enchantment> ENCHANTMENTS = new HashMap<>();

    public static Enchantment getByName(String name) {
        return ENCHANTMENTS.get(name.toLowerCase(Locale.ENGLISH));
    }

    public static Set<Map.Entry<String, Enchantment>> entrySet() {
        return ENCHANTMENTS.entrySet();
    }

    static { AddEnchants(); }

    private static void AddEnchants() {
        ENCHANTMENTS.put("sharpness", Enchantment.DAMAGE_ALL);
        ENCHANTMENTS.put("baneofarthropods", Enchantment.DAMAGE_ARTHROPODS);
        ENCHANTMENTS.put("smite", Enchantment.DAMAGE_UNDEAD);
        ENCHANTMENTS.put("efficiency", Enchantment.DIG_SPEED);
        ENCHANTMENTS.put("unbreaking", Enchantment.DURABILITY);
        ENCHANTMENTS.put("fireaspect", Enchantment.FIRE_ASPECT);
        ENCHANTMENTS.put("knockback", Enchantment.KNOCKBACK);
        ENCHANTMENTS.put("looting", Enchantment.LOOT_BONUS_MOBS);
        ENCHANTMENTS.put("respiration", Enchantment.OXYGEN);
        ENCHANTMENTS.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        ENCHANTMENTS.put("blastprotect", Enchantment.PROTECTION_EXPLOSIONS);
        ENCHANTMENTS.put("featherfall", Enchantment.PROTECTION_FALL);
        ENCHANTMENTS.put("projectileprotection", Enchantment.PROTECTION_PROJECTILE);
        ENCHANTMENTS.put("silktouch", Enchantment.SILK_TOUCH);
        ENCHANTMENTS.put("aquaaffinity", Enchantment.WATER_WORKER);
        ENCHANTMENTS.put("flame", Enchantment.ARROW_FIRE);
        ENCHANTMENTS.put("power", Enchantment.ARROW_DAMAGE);
        ENCHANTMENTS.put("punch", Enchantment.ARROW_KNOCKBACK);
        ENCHANTMENTS.put("infinity", Enchantment.ARROW_INFINITE);
        ENCHANTMENTS.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);

        if (Main.version <= 3) return;
        ENCHANTMENTS.put("thorns", Enchantment.THORNS);

        if (Main.version <= 6) return;
        ENCHANTMENTS.put("luck", Enchantment.LUCK);
        ENCHANTMENTS.put("lure", Enchantment.LURE);

        if (Main.version <= 7) return;
        ENCHANTMENTS.put("depthstrider", Enchantment.DEPTH_STRIDER);

        if (Main.version <= 8) return;
        ENCHANTMENTS.put("frostwalker", Enchantment.FROST_WALKER);
        ENCHANTMENTS.put("mending", Enchantment.MENDING);

        if (Main.version <= 10) return;
        ENCHANTMENTS.put("bindingcurse", Enchantment.BINDING_CURSE);
        ENCHANTMENTS.put("vanishingcurse", Enchantment.VANISHING_CURSE);
        ENCHANTMENTS.put("sweepingedge", Enchantment.SWEEPING_EDGE);

        if (Main.version <= 15) return;
        ENCHANTMENTS.put("soulspeed", Enchantment.SOUL_SPEED);

        if (Main.version <= 18) return;
        ENCHANTMENTS.put("loyalty", Enchantment.LOYALTY);
    }
}

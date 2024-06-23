package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEnchantment;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// This class exists because the registry doesn't exist pre 1.13, so all the enchantments names have to be hardcoded.
// only enchantments before 1.13 need to be here because this class isn't used past 1.13
public class BukkitEnchantmentHelper {
    private static final Map<EsEnchantment, Enchantment> ES_TO_BUKKIT = new HashMap<>();
    private static final Map<Enchantment, EsEnchantment> BUKKIT_TO_ES = new HashMap<>();

    public static EsEnchantment fromBukkitEnchantment(Enchantment ench) {
        return BUKKIT_TO_ES.get(ench);
    }

    public static Enchantment toBukkitEnchantment(EsEnchantment ench) {
        return ES_TO_BUKKIT.get(ench);
    }

    public static Set<EsEnchantment> getEnchantmentList() {
        return ES_TO_BUKKIT.keySet();
    }

    static {
        final HashMap<String, String> nameReplacers = new HashMap<String, String>() {{
            put("damage_all", "sharpness");
            put("damage_undead", "smite");
            put("damage_arthropods", "bane_of_arthropods");
            put("dig_speed", "efficiency");
            put("durability", "unbreaking");
            put("loot_bonus_mobs", "looting");
            put("oxygen", "respiration");
            put("protection_environmental", "protection");
            put("protection_explosions", "blast_protection");
            put("protection_fall", "feather_falling");
            put("protection_projectile", "projectile_protection");
            put("water_worker", "aqua_affinity");
            put("arrow_fire", "flame");
            put("arrow_damage", "power");
            put("arrow_knockback", "punch");
            put("arrow_infinite", "infinity");
            put("loot_bonus_blocks", "fortune");
        }};

        // Registry.ENCHANTMENT was added in 1.14
        if (Main.minecraftVersion.getMinor() > 13) {
            for (Enchantment ench : Registry.ENCHANTMENT) {
                EsEnchantment esEnch = EsEnchantment.createUnchecked(ench.getKey().getKey());
                ES_TO_BUKKIT.put(esEnch, ench);
                BUKKIT_TO_ES.put(ench, esEnch);
            }
        } else {
            for (Enchantment ench : Enchantment.values()) {
                String name;
                if (Main.minecraftVersion.getMinor() >= 13) {
                    name = ench.getKey().getKey();
                } else {
                    name = ench.getName().toLowerCase();

                    if (nameReplacers.containsKey(name)) {
                        name = nameReplacers.get(name);
                    }
                }

                EsEnchantment esType = EsEnchantment.createUnchecked(name);
                ES_TO_BUKKIT.put(esType, ench);
                BUKKIT_TO_ES.put(ench, esType);
            }
        }
    }
}

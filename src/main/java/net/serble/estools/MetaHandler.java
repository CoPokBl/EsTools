package net.serble.estools;

import net.serble.estools.Commands.Potion;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// This class exists because Minecraft < 1.4 doesn't have ItemMeta. It just encapsulates all ItemMeta methods.
public class MetaHandler {

    public static ItemStack getPotion(CommandSender sender, Potion.PotType potType, String effect, int duration, int amp, int amount) {
        if (Main.majorVersion >= 9) {
            String type = potType == Potion.PotType.drink ?
                    "POTION" :
                    potType.toString().toUpperCase() + "_POTION";
            ItemStack pot = new ItemStack(Material.valueOf(type), amount);

            PotionEffectType effType;
            try {
                effType = Effects.getByName(effect);
            } catch (IllegalArgumentException e) {
                EsToolsCommand.send(sender, "&cInvalid potion effect");
                return null;
            }

            PotionMeta meta = (PotionMeta) pot.getItemMeta();
            assert meta != null;
            meta.addCustomEffect(new PotionEffect(effType, duration, amp-1), true);
            pot.setItemMeta(meta);
            return pot;
        } else if (Main.majorVersion >= 4) {
            PotionType effType;
            try {
                effType = Effects.getPotionByName(effect);
            } catch (IllegalArgumentException e) {
                EsToolsCommand.send(sender, "&cInvalid potion effect");
                return null;
            }

            @SuppressWarnings("deprecation")
            org.bukkit.potion.Potion potion = new org.bukkit.potion.Potion(effType, amp);
            potion.setSplash(potType == Potion.PotType.splash);
            return potion.toItemStack(1);
        } else {  // This isn't possible to get to because this class won't load on 1.3 and below
            EsToolsCommand.send(sender, "&cPotions are not yet supported in this version, they may be in the future.");
            return null;
        }
    }

    public static void renameItem(ItemStack item, String name) {
        ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    public static List<String> getLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return new ArrayList<>();
        }

        return meta.getLore();
    }

    public static void setLore(ItemStack item, List<String> lore) {
        ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}

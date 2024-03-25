package net.serble.estools;

import net.serble.estools.Commands.Potion;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import static net.serble.estools.CMD.s;

// This class exists because Minecraft < 1.4 doesn't have ItemMeta. It just encapsulates the potion creation logic.
public class MetaHandler {

    public static ItemStack potHelper(Potion.PotType potType, PotionType potionType, int amp) {
        @SuppressWarnings("deprecation")
        org.bukkit.potion.Potion potion = new org.bukkit.potion.Potion(potionType, amp);
        potion.setSplash(potType == Potion.PotType.splash);
        return potion.toItemStack(1);
    }

    public static ItemStack newPotHelper(CommandSender sender, Potion.PotType potType, int duration, int amp, int amount, String eff) {
        String type = potType == Potion.PotType.drink ?
                "POTION" :
                potType.toString().toUpperCase() + "_POTION";
        ItemStack pot = new ItemStack(Material.valueOf(type), amount);
        PotionEffectType potion;
        try {
            potion = Effects.getByName(eff);
        } catch (IllegalArgumentException e) {
            s(sender, "&cInvalid potion type");
            return null;
        }
        PotionMeta meta = (PotionMeta) pot.getItemMeta();
        assert meta != null;
        meta.addCustomEffect(new PotionEffect(potion, duration, amp-1), true);
        pot.setItemMeta(meta);
        return pot;
    }

}

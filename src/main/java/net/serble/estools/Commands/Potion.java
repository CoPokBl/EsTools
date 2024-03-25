package net.serble.estools.Commands;

import net.serble.estools.Effects;
import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Potion extends EntityCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            s(sender, genUsage("/potion <potion> [amplifier] [duration] [amount] [drink/splash/lingering] [player]"));
            return false;
        }

        int amount = 1;
        if (args.length >= 4) {
            amount = tryParseInt(args[3], 1);
        }

        int amp = 1;
        if (args.length >= 2) {
            amp = tryParseInt(args[1], 1);
        }

        int duration = 60*20*5;
        if (args.length >= 3) {
            duration = tryParseInt(args[2], 60*5)*20;
        }

        PotType potType = PotType.drink;
        if (args.length >= 5) {
            try {
                potType = PotType.valueOf(args[4].toLowerCase());
            } catch (IllegalArgumentException ignored) { }

            // Check versions
            if (Main.version < 9 && potType == PotType.lingering) {
                // Doesn't exist
                s(sender, "&cLingering potions don't exist in this version");
                return false;
            }
        }

        Player player;
        if (args.length >= 6) {
            player = getPlayer(sender, args[5]);
            if (player == null) return false;
        } else {
            if (sender instanceof Player) {
                // Give to them
                player = (Player) sender;
            } else {
                // They are console
                s(sender, "&cConsole must specify a player");
                return false;
            }
        }

        ItemStack pot;
        if (Main.version > 8) {
            String type = potType == PotType.drink ?
                    "POTION" :
                    potType.toString().toUpperCase() + "_POTION";
            pot = new ItemStack(Material.valueOf(type), amount);
            PotionEffectType potion;
            try {
                potion = Effects.getByName(args[0]);
            } catch (IllegalArgumentException e) {
                s(sender, "&cInvalid potion type");
                return false;
            }
            PotionMeta meta = (PotionMeta) pot.getItemMeta();
            assert meta != null;
            meta.addCustomEffect(new PotionEffect(potion, duration, amp-1), true);
            pot.setItemMeta(meta);
        } else {
            PotionType potionType = Effects.getPotionByName(args[0]);
            if (potionType == null) {
                s(sender, "&cInvalid potion type");
                return false;
            }

            @SuppressWarnings("deprecation")
            org.bukkit.potion.Potion potion = new org.bukkit.potion.Potion(potionType, amp);
            potion.setSplash(potType == PotType.splash);
            pot = potion.toItemStack(amount);
        }


        player.getInventory().addItem(pot);

        return true;
    }

    enum PotType {
        drink,
        splash,
        lingering
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();
        // /potion <potion> [amplifier] [duration] [amount] [drink/splash/lingering] [player]

        switch (args.length) {
            case 1:
                for (Map.Entry<String, PotionType> e : Effects.potionEntrySet()) {
                    tab.add(e.getKey().toLowerCase());
                }
                break;

            case 2:
            case 4:
                tab.add("1");
                break;

            case 3:
                tab.add("300");
                break;

            case 5:
                tab.add("drink");
                tab.add("splash");
                if (Main.version >= 9) tab.add("lingering");
                break;

            case 6:
                return super.tabComplete(sender, args, lArg);
        }

        return tab;
    }

}

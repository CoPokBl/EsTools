package net.serble.estools.Commands;

import net.serble.estools.Effects;
import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import net.serble.estools.MetaHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
            } catch (IllegalArgumentException ignored) {
                s(sender, "&cInvalid potion type, must be drink, splash, or lingering (if on 1.9+)");
                return false;
            }

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

        if (Main.version <= 3) {
            s(sender, "&cPotions are not yet supported in this version, they may be in the future.");
            return false;
        }

        ItemStack pot = MetaHandler.getPotion(sender, potType, args[0], duration, amp, amount);
        if (pot == null) {
            return false;
        }

        player.getInventory().addItem(pot);

        if (player == sender) {
            s(sender, "&aThere you go!");
        } else {
            s(sender, "&aGave &6%s &aa potion", player.getName());
        }
        return true;
    }

    public enum PotType {
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

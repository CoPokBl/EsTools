package net.serble.estools.Commands;

import net.serble.estools.Effects;
import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import net.serble.estools.MetaHandler;
import net.serble.estools.ServerApi.EsPotType;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Potion extends EntityCommand {
    private static final String usage = genUsage("/potion <potion> [amplifier] [duration] [amount] [drink/splash/lingering] [player]");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (args.length == 0) {
            send(sender, usage);
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

        EsPotType potType = EsPotType.drink;
        if (args.length >= 5) {
            try {
                potType = EsPotType.valueOf(args[4].toLowerCase());
            } catch (IllegalArgumentException ignored) {
                send(sender, "&cInvalid potion type, must be drink, splash, or lingering (if on 1.9+)");
                return false;
            }

            // Check versions
            if (Main.minecraftVersion.getMinor() < 9 && potType == EsPotType.lingering) {
                // Doesn't exist
                send(sender, "&cLingering potions don't exist in this version");
                return false;
            }
        }

        EsPlayer player;
        if (args.length >= 6) {
            player = getPlayer(sender, args[5]);
            if (player == null) return false;
        } else {
            if (sender instanceof EsPlayer) {
                // Give to them
                player = (EsPlayer) sender;
            } else {
                // They are console
                send(sender, "&cConsole must specify a player");
                return false;
            }
        }

        if (Main.minecraftVersion.getMinor() <= 3) {
            send(sender, "&cPotions are not yet supported in this version, they may be in the future.");
            return false;
        }

        EsItemStack pot = MetaHandler.getPotion(sender, potType, args[0], duration, amp, amount);
        if (pot == null) {
            return false;
        }

        player.getInventory().addItem(pot);

        if (player == sender) {
            send(sender, "&aThere you go!");
        } else {
            send(sender, "&aGave &6%s &aa potion", player.getName());
        }
        return true;
    }

    @Override
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();
        // /potion <potion> [amplifier] [duration] [amount] [drink/splash/lingering] [player]

        switch (args.length) {
            case 1:
                if (Main.minecraftVersion.getMinor() <= 8) {
                    for (Map.Entry<String, String> e : Effects.getPotionList()) {
                        tab.add(e.getKey().toLowerCase());
                    }
                }
                else {
                    for (Map.Entry<String, String> e : Effects.entrySet()) {
                        tab.add(e.getKey().toLowerCase());
                    }
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
                if (Main.minecraftVersion.getMinor() >= 9) tab.add("lingering");
                break;

            case 6:
                return super.tabComplete(sender, args, lArg);
        }

        return tab;
    }
}

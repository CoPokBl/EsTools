package net.estools.Commands;

import net.estools.EntityCommand;
import net.estools.Main;
import net.estools.ServerApi.EsPotType;
import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.EsPotionEffectType;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;

public class Potion extends EntityCommand {
    private static final String usage = genUsage("/potion <potion> [amplifier] [duration] [amount] [drink/splash/lingering] [player]");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (args.length == 0) {
            send(sender, usage);
            return false;
        }

        EsPotionEffectType type = EsPotionEffectType.fromKey(args[0]);
        if (type == null) {
            send(sender, "&cPotion type does not exist!");
            return false;
        }

        int amount = 1;
        if (args.length >= 4) {
            amount = tryParseInt(args[3], 1);
        }

        int amp = 0;
        if (args.length >= 2) {
            // amp 0 is level 1, so subtract 1 from player input
            amp = tryParseInt(args[1], 1) - 1;

            if (Main.minecraftVersion.getMinor() <= 7 && (amp < 0 || amp > 1)) {
                send(sender, "&cAmplifier must be 1 or 2!");
                return false;
            }
        }

        int duration = 60*20*5;
        if (args.length >= 3) {
            duration = (int) (tryParseDouble(args[2], 60d*5d)*20d);
        }

        EsPotType potType = EsPotType.drink;
        if (args.length >= 5) {
            try {
                potType = EsPotType.valueOf(args[4].toLowerCase());
            } catch (IllegalArgumentException ignored) {
                if (Main.minecraftVersion.getMinor() >= 9) {
                    send(sender, "&cInvalid potion type, must be drink, splash, or lingering");
                } else {
                    send(sender, "&cInvalid potion type, must be drink or splash");
                }

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

        EsItemStack pot = Main.server.createPotion(potType, new EsPotionEffect(type, amp, duration), amount);
        if (pot == null) {
            send(sender, "&cInvalid potion effect!");
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
                    for (EsPotionEffectType e : Main.server.getOldPotionTypes()) {
                        tab.add(e.getKey().toLowerCase());
                    }
                }
                else {
                    for (EsPotionEffectType e : Main.server.getPotionEffectTypes()) {
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

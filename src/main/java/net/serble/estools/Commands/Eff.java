package net.serble.estools.Commands;

import net.serble.estools.Effects;
import net.serble.estools.Main;
import net.serble.estools.MultiPlayerCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Eff extends MultiPlayerCommand {
    private static final String usage = genUsage("/eff <effect> [amplifier] [duration] [players]");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (args.length == 0) {
            send(sender, usage);
            return false;
        }

        String effect = Effects.getByName(args[0]);

        if (effect == null) {
            send(sender, "&cEffect not found!");
            return false;
        }

        int amplifier = 0;
        if (args.length > 1) {
            try {
                amplifier = Integer.parseInt(args[1]) - 1;
            } catch (Exception x) {
                send(sender, usage);
                return false;
            }
        }

        int duration = 1200;
        String durationStr = "60 seconds";

        if (args.length > 2) {
            duration = calculateDuration(args[2]);

            if (duration == -1) {
                send(sender, usage);
                return false;
            }

            durationStr = String.format("%s seconds", duration / 20);

            if (duration == 32767) {
                if (Main.minecraftVersion.getMinor() >= 19) {
                    duration = -1;
                }

                durationStr = "forever";
            }
        }

        ArrayList<EsPlayer> players = new ArrayList<>();

        if (args.length < 4) {
            if (isNotPlayer(sender, usage)) {
                return false;
            }

            players.add((EsPlayer) sender);
        } else {
            players = getPlayers(sender, removeArgs(args, 3));

            if (players.isEmpty()) {
                return false;
            }
        }

        for (EsPlayer p : players) {
            p.removePotionEffect(effect);
            p.addPotionEffect(effect, duration, amplifier);
        }

        send(sender, "&aAdded effect &6%s&a at level &6%s&a for &6%s", Effects.getName(effect), amplifier + 1, durationStr);
        return true;
    }

    private int calculateDuration(String input) {
        if (input.equalsIgnoreCase("infinite")) {
            return 32767;
        }

        try {
            int duration = (int)(Double.parseDouble(input) * 20);

            if (duration >= 0) {
                return duration;
            }

            return 32767;
        } catch (NumberFormatException x) {
            return -1;
        }
    }

    @Override
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        switch (args.length) {
            case 1:
                for (Map.Entry<String, String> e : Effects.entrySet()) {
                    tab.add(e.getKey().toLowerCase());
                }
                break;

            case 2:
                tab.add("1");
                break;

            case 3:
                tab.add("60");
                tab.add("infinite");
                break;

            case 4:
                return super.tabComplete(sender, args, lArg);
        }

        return tab;
    }
}

package net.serble.estools.Commands.MoveSpeed;

import net.serble.estools.MultiPlayerCommand;
import net.serble.estools.Tuple;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class MoveSpeed extends MultiPlayerCommand {
    protected abstract String getUsage();

    public Tuple<List<Player>, Float> getTargets(CommandSender sender, String[] args) {
        if (args.length == 0) {
            send(sender, getUsage());
            return null;
        }

        ArrayList<Player> players = new ArrayList<>();

        if (args.length == 1) {
            if (isNotPlayer(sender, getUsage())) {
                return null;
            }

            players.add((Player)sender);
        } else {
            players = getPlayers(sender, removeArgs(args, 1));

            if (players.isEmpty()) {
                return null;
            }
        }

        float amount;
        try {
            amount = Float.parseFloat(args[0]) / 10f;
        } catch (Exception e) {
            send(sender, getUsage());
            return null;
        }

        if (amount > 1) {
            amount = 1;
        } else if (amount < 0) {
            amount = 0;
        }

        return new Tuple<>(players, amount);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        if (args.length == 1) {
            tab.add("2"); tab.add("10");
        } else if (args.length == 2) {
            return super.tabComplete(sender, args, lArg);
        }

        return tab;
    }
}

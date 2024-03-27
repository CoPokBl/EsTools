package net.serble.estools.Commands;

import net.serble.estools.MultiPlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlySpeed extends MultiPlayerCommand {
    private static final String usage = genUsage("/flyspeed <speed> [players]");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            s(sender, usage);
            return true;
        }

        ArrayList<Player> p = new ArrayList<>();

        if (args.length == 1) {
            if (isNotPlayer(sender, usage))
                return true;

            p.add((Player)sender);
        } else {
            p = getPlayers(sender, removeArgs(args, 1));

            if (p.isEmpty())
                return true;
        }

        float amount;

        try {
            amount = Float.parseFloat(args[0]) / 10f;
        } catch (Exception e) {
            s(sender, usage);
            return true;
        }

        if (amount > 1) {
            amount = 1;
        } else if (amount < 0) {
            amount = 0;
        }


        for (Player ip : p) {
            ip.setFlySpeed(amount);
        }

        s(sender, "&aSet fly speed to &6%s", String.valueOf(amount * 10));
        return true;
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

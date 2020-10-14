package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.MultiPlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WalkSpeed extends MultiPlayerCommand {

    private static String usage = genUsage("/walkspeed <speed> [players]");

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
            amount = Float.parseFloat(args[0]);
        } catch (Exception e) {
            s(sender, usage);
            return true;
        }

        for (Player ip : p) {
            ip.setWalkSpeed(amount);
        }
        return true;
    }

}

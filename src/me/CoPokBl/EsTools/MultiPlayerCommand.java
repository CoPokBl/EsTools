package me.CoPokBl.EsTools;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiPlayerCommand extends CMD {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tab = new ArrayList<String>();

        String latestArg = args[args.length - 1].toLowerCase();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().toLowerCase().startsWith(latestArg))
                tab.add(p.getName());
        }

        if ("*".startsWith(latestArg))
            tab.add("*");

        return tab;
    }

    public static ArrayList<Player> getPlayers(CommandSender sender, String[] names) {
        if (names.length == 0) {
            return new ArrayList<Player>();
        }

        if (names[0].equals("*")) {
            return new ArrayList<Player>(Bukkit.getOnlinePlayers());
        }

        ArrayList<Player> players = new ArrayList<Player>();

        for (String name : names) {
            Player p = Bukkit.getPlayer(name);

            if (p == null) {
                s(sender, "&cPlayer &6%s&c not found.", name);
            } else {
                players.add(p);
            }
        }

        return players;
    }

    public static ArrayList<Player> getPlayers(CommandSender sender, String name) {
        if (name.equals("*")) {
            return new ArrayList<Player>(Bukkit.getOnlinePlayers());
        }

        ArrayList<Player> players = new ArrayList<Player>();

        Player p = Bukkit.getPlayer(name);

        if (p == null) {
            s(sender, "&cPlayer &6%s&c not found.", name);
        } else {
            players.add(p);
        }

        return players;
    }
}

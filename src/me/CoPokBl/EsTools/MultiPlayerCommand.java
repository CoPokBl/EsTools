package me.CoPokBl.EsTools;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiPlayerCommand extends CMD {

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<String>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            tab.add(p.getName());
        }

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

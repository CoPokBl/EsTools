package net.serble.estools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class MultiPlayerCommand extends CMD {

    public static ArrayList<Player> getPlayers(CommandSender sender, String[] names) {
        if (names.length == 0) {
            return new ArrayList<>();
        }

        if (names[0].equals("*")) {
            return new ArrayList<>(getOnlinePlayers());
        }

        ArrayList<Player> players = new ArrayList<>();

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
            return new ArrayList<>(getOnlinePlayers());
        }

        ArrayList<Player> players = new ArrayList<>();

        Player p = Bukkit.getPlayer(name);

        if (p == null) {
            s(sender, "&cPlayer &6%s&c not found.", name);
        } else {
            players.add(p);
        }

        return players;
    }
}

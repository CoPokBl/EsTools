package net.estools;

import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class MultiPlayerCommand extends EsToolsCommand {

    @Override
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        for (EsPlayer p : Main.server.getOnlinePlayers()) {
            tab.add(p.getName());
        }

        tab.add("*");

        return tab;
    }

    public static ArrayList<EsPlayer> getPlayers(EsCommandSender sender, String[] names) {
        if (names.length == 0) {
            return new ArrayList<>();
        }

        if (names[0].equals("*")) {
            return new ArrayList<>(Main.server.getOnlinePlayers());
        }

        ArrayList<EsPlayer> players = new ArrayList<>();

        for (String name : names) {
            getPlayer(sender, name, players);
        }

        return players;
    }

    public static ArrayList<EsPlayer> getPlayers(EsCommandSender sender, String name) {
        if (name.equals("*")) {
            return new ArrayList<>(Main.server.getOnlinePlayers());
        }

        ArrayList<EsPlayer> players = new ArrayList<>();

        getPlayer(sender, name, players);

        return players;
    }

    /**
     * Gets a player from an input (username or UUID) and adds it to a list. Sends an error if one of the
     * players is invalid.
     * @param sender The person to complain to.
     * @param name The input (username or UUID).
     * @param players The array to append players to.
     */
    private static void getPlayer(EsCommandSender sender, String name, ArrayList<EsPlayer> players) {
        EsPlayer p;
        try {
            UUID uuid = UUID.fromString(name);
            p = Main.server.getPlayer(uuid);
        } catch (IllegalArgumentException unused) {
            p = Main.server.getPlayer(name);
        }

        if (p == null) {
            send(sender, "&cPlayer &6%s&c not found.", name);
        } else {
            players.add(p);
        }
    }
}

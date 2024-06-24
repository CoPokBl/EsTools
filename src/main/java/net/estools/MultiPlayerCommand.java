package net.estools;

import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;

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
            EsPlayer p = Main.server.getPlayer(name);

            if (p == null) {
                send(sender, "&cPlayer &6%s&c not found.", name);
            } else {
                players.add(p);
            }
        }

        return players;
    }

    public static ArrayList<EsPlayer> getPlayers(EsCommandSender sender, String name) {
        if (name.equals("*")) {
            return new ArrayList<>(Main.server.getOnlinePlayers());
        }

        ArrayList<EsPlayer> players = new ArrayList<>();

        EsPlayer p = Main.server.getPlayer(name);

        if (p == null) {
            send(sender, "&cPlayer &6%s&c not found.", name);
        } else {
            players.add(p);
        }

        return players;
    }
}

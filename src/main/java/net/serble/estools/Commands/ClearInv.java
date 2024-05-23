package net.serble.estools.Commands;

import net.serble.estools.MultiPlayerCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;

public class ClearInv extends MultiPlayerCommand {
    private static final String usage = genUsage("/ci <player1> [player2]");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        ArrayList<EsPlayer> players = new ArrayList<>();

        if (args.length == 0) {
            if (isNotPlayer(sender, usage)) {
                return false;
            }

            players.add((EsPlayer) sender);
        } else {
            players = getPlayers(sender, args);

            if (players.isEmpty()) {
                return false;
            }
        }

        for (EsPlayer p : players) {
            p.getInventory().clear();
        }

        send(sender, "&aCleared Inventory!");
        return true;
    }
}

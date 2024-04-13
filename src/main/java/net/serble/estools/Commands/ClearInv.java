package net.serble.estools.Commands;

import net.serble.estools.MultiPlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClearInv extends MultiPlayerCommand {
    private static final String usage = genUsage("/ci <player1> [player2]");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<Player> players = new ArrayList<>();

        if (args.length == 0) {
            if (isNotPlayer(sender, usage)) {
                return false;
            }

            players.add((Player)sender);
        } else {
            players = getPlayers(sender, args);

            if (players.isEmpty()) {
                return false;
            }
        }

        for (Player p : players) {
            p.getInventory().clear();
        }

        send(sender, "&aCleared Inventory!");
        return true;
    }
}

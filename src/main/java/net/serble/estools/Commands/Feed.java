package net.serble.estools.Commands;

import net.serble.estools.MultiPlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Feed extends MultiPlayerCommand {
	private static final String usage = genUsage("/feed <Player>");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<Player> players = new ArrayList<>();
		
		if (args.length == 0) {
			if (isNotPlayer(sender, usage)) {
                return false;
            }
			
			players.add((Player) sender);
		} else {
			players = getPlayers(sender, args);
			
			if (players.isEmpty()) {
                return false;
            }
		}

		for (Player p : players) {
			p.setFoodLevel(20);
			p.setSaturation(20);
		}

		if (args.length == 0) {
			send(sender, "&aFed!");
		} else {
			send(sender, "&aFed &6%s&a!", args[0]);
		}
		return true;
	}

}

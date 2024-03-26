package net.serble.estools.Commands;

import net.serble.estools.MultiPlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Feed extends MultiPlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		ArrayList<Player> ps = new ArrayList<>();
		
		if (args.length == 0) {
			if (isNotPlayer(sender, genUsage("/feed <Player>")))
				return false;
			
			ps.add((Player) sender);
		} else {
			ps = getPlayers(sender, args);
			
			if (ps.isEmpty())
				return false;
		}

		for (Player p : ps) {
			p.setFoodLevel(20);
			p.setSaturation(20);
		}

		if (args.length == 0) {
			s(sender, "&aFed!");
		} else {
			s(sender, "&aFed &6%s&a!", args[0]);
		}
		return true;
	}

}

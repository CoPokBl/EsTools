package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.MultiPlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Feed extends MultiPlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		ArrayList<Player> ps = new ArrayList<Player>();
		
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
		return true;
	}

}

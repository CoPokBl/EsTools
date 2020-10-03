package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class Feed extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "feed"))
			return false;
		
		Player p;
		
		if (args.length == 0) {
			if (isNotPlayer(sender, genUsage("/feed <Player>")))
				return false;
			
			p = (Player) sender;
		} else {
			p = getPlayer(sender, args[0]);
			
			if (p == null)
				return false;
		}

		p.setFoodLevel(20);
		p.setSaturation(20);
		return true;
	}

}

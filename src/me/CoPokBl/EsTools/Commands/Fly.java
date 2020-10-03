package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class Fly extends CMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "fly"))
			return false;
		
		Player p;
		
		if (args.length == 0) {
			if (isNotPlayer(sender, genUsage("/fly [Player]"))) 
				return false;
			
			p = (Player) sender;
		} else {
			p = getPlayer(sender, args[0]);
			
			if (p == null)
				return false;
		}
		
		boolean isFly = p.getAllowFlight();
		
		if (isFly)
			s(sender, "&aFly Disabled for &6%s", p.getName());
		else
			s(sender, "&aFly Enabled for &6%s", p.getName());
		
		p.setAllowFlight(!isFly);
		return true;
	}

}

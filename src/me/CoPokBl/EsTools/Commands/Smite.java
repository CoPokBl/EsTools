package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class Smite extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "smite"))
			return false;
		
		if (args.length == 0) {
			s(sender, genUsage("/smite <player1> [player2] [player3]..."));
			return false;
		}
		
		
		for (String arg : args) {
			Player target = getPlayer(sender, arg);
			
			if (target != null) {
				target.getWorld().strikeLightning(target.getLocation());
			}
				
		}

		if (args.length > 0)
			s(sender, "&aBAM!");
		return true;
	}

}

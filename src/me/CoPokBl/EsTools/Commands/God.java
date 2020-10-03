package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class God extends CMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "god"))
			return false;
		
		Player p;
		
		if (args.length == 0) {
			if (isNotPlayer(sender, genUsage("/god <player>")))
				return false;
			
			p = (Player) sender;
		} else {
			p = getPlayer(sender, args[0]);
			
			if (p == null)
				return false;
		}
		
		if (p.isInvulnerable()) {
			p.setInvulnerable(false);
			s(sender, "&aGod mode &6disabled&a for &6%s", p.getName());
		}
		else {
			p.setInvulnerable(true);
			s(sender, "&aGod mode &6enabled&a for &6%s", p.getName());
		}
		
		
		
		return true;
	}

}

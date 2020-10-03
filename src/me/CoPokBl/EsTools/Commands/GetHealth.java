package me.CoPokBl.EsTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class GetHealth extends CMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "gethealth"))
			return false;
		
		if (args.length == 0) {
			s(sender, genUsage("/gethealth <player>"));
			return false;
		}
		
		Player p = Bukkit.getPlayer(args[0]);
		
		s(sender, "&6%s's&a health is &6%d", p.getName(), (int)p.getHealth());
		return true;
	}

}


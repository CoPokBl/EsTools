package me.CoPokBl.EsTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class Sudo extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "sudo"))
			return false;
		
		if (args.length < 2) {
			s(sender, genUsage("/sudo <player> <command>"));
			return false;
		}
		
		Player p = getPlayer(sender, args[0]);
		
		if (p == null)
			return true;
		
		String com = "";
		
		for (int i = 1; i < args.length; i++) {
			com += args[i] + " ";
		}
		
		com = com.trim();
		
		Bukkit.dispatchCommand(p, com);
		
		return true;
	}

}

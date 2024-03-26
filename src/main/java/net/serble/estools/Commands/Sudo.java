package net.serble.estools.Commands;

import net.serble.estools.CMD;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sudo extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length < 2) {
			s(sender, genUsage("/sudo <player> <command>"));
			return false;
		}
		
		Player p = getPlayer(sender, args[0]);
		
		if (p == null)
			return true;
		
		StringBuilder com = new StringBuilder();
		
		for (int i = 1; i < args.length; i++) {
			com.append(args[i]).append(" ");
		}
		
		com = new StringBuilder(com.toString().trim());
		
		Bukkit.dispatchCommand(p, com.toString());
		
		return true;
	}

}

package net.serble.estools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.serble.estools.PlayerCommand;

public class Sudo extends PlayerCommand {
	private static final String usage = genUsage("/sudo <player> <command>");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length < 2) {
			s(sender, usage);
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
		s(sender, "&aExecuted command &6%s&a as &6%s", com.toString(), p.getName());
		return true;
	}

}

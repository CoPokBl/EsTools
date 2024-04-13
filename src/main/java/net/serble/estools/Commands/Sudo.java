package net.serble.estools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.serble.estools.PlayerCommand;

public class Sudo extends PlayerCommand {
	private static final String usage = genUsage("/sudo <player> <command>");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 2) {
			send(sender, usage);
			return false;
		}
		
		Player p = getPlayer(sender, args[0]);
		
		if (p == null) {
            return false;
        }
		
		String command = argsToString(args, 1);
		
		Bukkit.dispatchCommand(p, command);
		send(sender, "&aExecuted command &6%s&a as &6%s", command, p.getName());
		return true;
	}

}

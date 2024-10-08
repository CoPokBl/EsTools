package net.estools.Commands;

import net.estools.Main;
import net.estools.PlayerCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class Sudo extends PlayerCommand {
	private static final String usage = genUsage("/sudo <player> <command>");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length < 2) {
			send(sender, usage);
			return false;
		}
		
		EsPlayer p = getPlayer(sender, args[0]);
		
		if (p == null) {
            return false;
        }
		
		String command = argsToString(args, 1);

		Main.server.dispatchCommand(p, command);
		send(sender, "&aExecuted command &6%s&a as &6%s", command, p.getName());
		return true;
	}

}

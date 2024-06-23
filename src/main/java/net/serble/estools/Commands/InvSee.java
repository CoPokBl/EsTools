package net.serble.estools.Commands;

import net.serble.estools.PlayerCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class InvSee extends PlayerCommand {
	private static final String usage = genUsage("/invsee <player>");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		EsPlayer target = getPlayer(sender, args[0]);
		
		if (target == null) {
            return false;
        }

		((EsPlayer) sender).openInventory(target.getInventory());
        send(sender, "&aOpened &6%s's &aInventory", target.getName());
		return true;
	}
}

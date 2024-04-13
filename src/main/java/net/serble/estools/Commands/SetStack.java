package net.serble.estools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.serble.estools.EsToolsCommand;

public class SetStack extends EsToolsCommand {
	private static final String usage = genUsage("/setstack <amount>");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		int amount;
		try {
			amount = Integer.parseInt(args[0]);
		} catch (Exception e) {
			send(sender, usage);
			return false;
		}

		getMainHand(((Player)sender)).setAmount(amount);
		send(sender, "&aSet stack size to &6%d", amount);
		return true;
	}

}

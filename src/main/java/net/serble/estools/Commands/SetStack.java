package net.serble.estools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.serble.estools.CMD;

public class SetStack extends CMD {
	private static final String usage = genUsage("/setstack <amount>");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return true;
		
		if (args.length == 0) {
			s(sender, usage);
			return true;
		}
		
		int amount;
		
		try {
			amount = Integer.parseInt(args[0]);
		} catch(Exception e) {
			s(sender, usage);
			return false;
		}

		getMainHand(((Player)sender)).setAmount(amount);

		s(sender, "&aSet stack size to &6%d", amount);
		return true;
	}

}

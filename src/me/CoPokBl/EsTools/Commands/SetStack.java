package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class SetStack extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return true;
		
		if (args.length == 0) {
			s(sender, genUsage("/setstack <amount>"));
			return true;
		}
		
		int amount = 0;
		
		try {
			amount = Integer.valueOf(args[0]);
		} catch(Exception e) {
			s(sender, genUsage("/setstack <amount>"));
			return false;
		}

		getMainHand(((Player)sender)).setAmount(amount);
		
		return true;
	}

}

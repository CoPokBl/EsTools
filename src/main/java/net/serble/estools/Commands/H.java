package net.serble.estools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.serble.estools.CMD;
import net.serble.estools.Give;

public class H extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return false;
		
		if (args.length == 0) {
			s(sender, genUsage("/h <item> [amount]"));
			return false;
		}
		
		Player p = (Player) sender;
		
		int amount = 1;
		
		if (args.length > 1) {
			try {
				amount = Integer.parseInt(args[1]);
			} catch(Exception e) {
				s(sender, genUsage("/h <item> [amount]"));
				return false;
			}
		}
		
		ItemStack is = Give.getItem(args[0], amount);
		
		if (is != null)
			setMainHand(p, is);
		else
			s(sender, "&cItem &6%s&c not found.", args[0]);
		
		return true;
	}

}

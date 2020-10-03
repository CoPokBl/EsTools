package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Give;

public class H extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "give"))
			return false;
		
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
				amount = Integer.valueOf(args[1]);
			} catch(Exception e) {
				s(sender, genUsage("/h <item> [amount]"));
				return false;
			}
		}
		
		ItemStack is = Give.getItem(args[0], amount);
		
		if (is != null)
			p.getInventory().setItemInMainHand(is);
		else
			s(sender, "&4Item &6%s&4 not found.", args[0]);
		
		return true;
	}

}

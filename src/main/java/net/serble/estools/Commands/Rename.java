package net.serble.estools.Commands;

import net.serble.estools.MetaHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.serble.estools.CMD;

public class Rename extends CMD {
	
	public static final String usage = genUsage("/rename <name>");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return true;
		
		if (args.length == 0) {
			s(sender, usage);
		}
		
		Player p = (Player)sender;
		
		String name = ChatColor.translateAlternateColorCodes('&', "&r" + String.join(" ", args));
		
		ItemStack is = getMainHand(p);

		MetaHandler.renameItem(is, name);
		
		s(sender, "&aItem renamed to &6%s", name);
		return true;
	}
}

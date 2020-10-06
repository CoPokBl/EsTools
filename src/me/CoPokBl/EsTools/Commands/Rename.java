package me.CoPokBl.EsTools.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.CoPokBl.EsTools.CMD;

public class Rename extends CMD {
	
	public static final String usage = genUsage("/rename <name>");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "rename"))
			return false;
		
		if (isNotPlayer(sender))
			return true;
		
		if (args.length == 0) {
			s(sender, usage);
		}
		
		Player p = (Player)sender;
		
		String name = ChatColor.translateAlternateColorCodes('&', "&r" + String.join(" ", args));
		
		ItemStack is = p.getInventory().getItemInMainHand();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		
		s(sender, "&aItem renamed to &6%s", im.getDisplayName());
		return true;
	}
}

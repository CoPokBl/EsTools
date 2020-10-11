package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.PlayerCommand;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Invsee extends PlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return false;
		
		if (args.length == 0) {
			s(sender, genUsage("/invsee <player>"));
			return false;
		}
		
		Player target = getPlayer(sender, args[0]);
		
		if (target == null)
			return false;

		((Player) sender).openInventory(target.getInventory());

        s(sender, "&aOpened %s's Inventory", target.getName());
		return true;
	}

}

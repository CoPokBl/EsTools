package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.PlayerCommand;

public class Invsee extends PlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "invsee"))
			return false;
		
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

package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class Suicide extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "suicide"))
			return false;
		
		if (isNotPlayer(sender))
			return false;
		
		Player p = (Player) sender;

		p.setHealth(0);
		return true;
	}

}

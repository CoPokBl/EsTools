package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.PlayerCommand;

public class Suicide extends PlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return false;
		
		LivingEntity p = (LivingEntity) sender;

		p.setHealth(0);
		return true;
	}

}

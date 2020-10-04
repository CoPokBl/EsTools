package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;

public class GetHealth extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "gethealth"))
			return false;
		
		if (args.length == 0) {
			s(sender, genUsage("/gethealth <entity>"));
			return false;
		}
		
		LivingEntity p = getEntity(sender, args[0]);
		
		if (p == null) 
			return false;
		
		s(sender, "&6%s's&a health is &6%d", p.getName(), (int)p.getHealth());
		return true;
	}

}


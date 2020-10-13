package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;

public class Smite extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, genUsage("/smite <entity1> [entity2] [entity3]..."));
			return false;
		}
		
		
		for (String arg : args) {
			LivingEntity target = getEntity(sender, arg);
			
			if (target != null) {
				target.getWorld().strikeLightning(target.getLocation());
			}
				
		}

		if (args.length > 0)
			s(sender, "&aBAM!");
		return true;
	}

}

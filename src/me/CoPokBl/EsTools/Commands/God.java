package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;

public class God extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "god"))
			return false;
		
		LivingEntity p;
		
		if (args.length == 0) {
			if (isNotEntity(sender, genUsage("/god <entity>")))
				return false;
			
			p = (LivingEntity) sender;
		} else {
			p = getEntity(sender, args[0]);
			
			if (p == null)
				return false;
		}
		
		if (p.isInvulnerable()) {
			p.setInvulnerable(false);
			s(sender, "&aGod mode &6disabled&a for &6%s", p.getName());
		}
		else {
			p.setInvulnerable(true);
			s(sender, "&aGod mode &6enabled&a for &6%s", p.getName());
		}
		
		
		
		return true;
	}

}

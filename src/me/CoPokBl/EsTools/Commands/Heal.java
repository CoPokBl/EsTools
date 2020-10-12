package me.CoPokBl.EsTools.Commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;

public class Heal extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "heal"))
			return false;
		
		LivingEntity p;
		
		if (args.length == 0) {
			if (isNotEntity(sender, genUsage("/heal <entity>")))
				return false;
			
			p = (LivingEntity) sender;
			
			s(sender, "&aHealed!");
		} else {
			p = getEntity(sender, args[0]);
			
			if (p == null)
				return false;
			
			s(sender, "&aHealed &6%s", p.getName());
		}
		
		p.setHealth(getMaxHealth(p));
		return true;
	}

}

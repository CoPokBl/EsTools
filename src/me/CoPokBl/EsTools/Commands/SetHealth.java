package me.CoPokBl.EsTools.Commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import me.CoPokBl.EsTools.EntityCommand;

public class SetHealth extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "sethealth"))
			return false;
		
		if (args.length == 0) {
			s(sender, genUsage("/sethealth <amount> [entity]"));
		}
		
		LivingEntity p;
		double health;
		
		if (args.length > 1) {
			p = getEntity(sender, args[1]);
			
			if (p == null)
				return false;
		} else {
			if (isNotEntity(sender))
				return false;
			
			p = (LivingEntity) sender;
		}
		
		try {
			health = Double.valueOf(args[0]);
		} catch (Exception e) {
			s(sender, genUsage("/sethealth <amount> [entity]"));
			return false;
		}
		
		if (health >= 0) {
			double maxhealth = getMaxHealth(p);

			if (maxhealth < health)
				health = maxhealth;
			
			p.setHealth(health);
			
			if (args.length > 1)
				s(sender, "&aSet health for &6%s&a to &6%s", p.getName(), String.valueOf(health));
			else
				s(sender, "&aSet health to &6%s", String.valueOf(health));
		}
		else
			s(sender, "&cCannot set health to less than 0");
		return true;
	}
}

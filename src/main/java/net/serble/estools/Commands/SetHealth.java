package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class SetHealth extends EntityCommand {
	private static final String usage = genUsage("/sethealth <amount> [entity]");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, usage);
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
			health = Double.parseDouble(args[0]);
		} catch (Exception e) {
			s(sender, usage);
			return false;
		}
		
		if (health >= 0) {
			double maxhealth = getMaxHealth(p);

			if (maxhealth < health)
				health = maxhealth;
			
			setHealth(p, health);
			
			if (args.length > 1) {
                s(sender, "&aSet health for &6%s&a to &6%s", getEntityName(p), String.valueOf(health));
            } else {
                s(sender, "&aSet health to &6%s", String.valueOf(health));
            }
		}
		else
			s(sender, "&cCannot set health to less than 0");
		return true;
	}
}

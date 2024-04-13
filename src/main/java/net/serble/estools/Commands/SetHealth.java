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
			send(sender, usage);
		}
		
		LivingEntity entity;
		if (args.length > 1) {
			entity = getEntity(sender, args[1]);
			
			if (entity == null) {
                return false;
            }
		} else {
			if (isNotEntity(sender)) {
                return false;
            }
			
			entity = (LivingEntity) sender;
		}

		double health;
		try {
			health = Double.parseDouble(args[0]);
		} catch (Exception e) {
			send(sender, usage);
			return false;
		}
		
		if (health < 0) {
			send(sender, "&cCannot set health to less than 0");
			return false;
		}

		double maxhealth = getMaxHealth(entity);
		if (maxhealth < health) {
			health = maxhealth;
		}

		setHealth(entity, health);

		if (args.length > 1) {
			send(sender, "&aSet health for &6%s&a to &6%s", getEntityName(entity), String.valueOf(health));
		} else {
			send(sender, "&aSet health to &6%s", String.valueOf(health));
		}
		return true;
	}
}

package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;

public class SetHealth extends EntityCommand {
	private static final String usage = genUsage("/sethealth <amount> [entity]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		EsLivingEntity entity;
		if (args.length > 1) {
			entity = getEntity(sender, args[1]);
			
			if (entity == null) {
                return false;
            }
		} else {
			if (isNotEntity(sender)) {
                return false;
            }
			
			entity = (EsLivingEntity) sender;
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

		double maxhealth = entity.getMaxHealth();
		if (maxhealth < health) {
			health = maxhealth;
		}

		entity.setHealth(health);

		if (args.length > 1) {
			send(sender, "&aSet health for &6%s&a to &6%s", entity.getName(), String.valueOf(health));
		} else {
			send(sender, "&aSet health to &6%s", String.valueOf(health));
		}
		return true;
	}
}

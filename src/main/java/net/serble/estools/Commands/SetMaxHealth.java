package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;

public class SetMaxHealth extends EntityCommand {
	private static final String usage = genUsage("/setmaxhealth <amount> [entity]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
		}

		double health;
		try {
			health = Double.parseDouble(args[0]);
		} catch (Exception e) {
			send(sender, usage);
			return false;
		}

		EsLivingEntity entity;
		if (args.length > 1) {
			entity = getEntity(sender, args[1]);
			
			if (entity == null) {
                return false;
            }
			
			send(sender, "&aSet max health for &6%s&a to &6%s", entity.getName(), String.valueOf(health));
		} else {
			if (isNotEntity(sender)) {
                return false;
            }
			
			entity = (EsLivingEntity) sender;
			
			send(sender, "&aSet max health to &6%s", String.valueOf(health));
		}

		entity.setMaxHealth(health);
		return true;
	}

}

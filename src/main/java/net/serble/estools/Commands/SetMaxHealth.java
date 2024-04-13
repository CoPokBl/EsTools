package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class SetMaxHealth extends EntityCommand {
	private static final String usage = genUsage("/setmaxhealth <amount> [entity]");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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

		LivingEntity entity;
		if (args.length > 1) {
			entity = getEntity(sender, args[1]);
			
			if (entity == null) {
                return false;
            }
			
			send(sender, "&aSet max health for &6%s&a to &6%s", getEntityName(entity), String.valueOf(health));
		} else {
			if (isNotEntity(sender)) {
                return false;
            }
			
			entity = (LivingEntity) sender;
			
			send(sender, "&aSet max health to &6%s", String.valueOf(health));
		}

		setMaxHealth(entity, health);
		return true;
	}

}

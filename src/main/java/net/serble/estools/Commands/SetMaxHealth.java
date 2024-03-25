package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class SetMaxHealth extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, genUsage("/setmaxhealth <amount> [entity]"));
		}
		
		LivingEntity p;
		double health;
		
		try {
			health = Double.valueOf(args[0]);
		} catch (Exception e) {
			s(sender, genUsage("/sethealth <amount> [entity]"));
			return false;
		}
		
		if (args.length > 1) {
			p = getEntity(sender, args[1]);
			
			if (p == null)
				return false;
			
			s(sender, "&aSet max health for &6%s&a to &6%s", getEntityName(p), String.valueOf(health));
		} else {
			if (isNotEntity(sender))
				return false;
			
			p = (LivingEntity) sender;
			
			s(sender, "&aSet max health to &6%s", String.valueOf(health));
		}

		setMaxHealth(p, health);
		return true;
	}

}

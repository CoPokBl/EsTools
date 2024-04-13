package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class Heal extends EntityCommand {
	private static final String usage = genUsage("/heal [entity]");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		LivingEntity entity;
		if (args.length == 0) {
			if (isNotEntity(sender, usage)) {
                return false;
            }
			
			entity = (LivingEntity) sender;
			send(sender, "&aHealed!");
		} else {
			entity = getEntity(sender, args[0]);
			
			if (entity == null) {
                return false;
            }
			
			send(sender, "&aHealed &6%s", getEntityName(entity));
		}

		setHealth(entity, getMaxHealth(entity));
		entity.setFireTicks(0);
		return true;
	}

}

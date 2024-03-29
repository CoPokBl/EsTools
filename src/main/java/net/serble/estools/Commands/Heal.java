package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class Heal extends EntityCommand {
	private static final String usage = genUsage("/heal [entity]");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		LivingEntity p;
		
		if (args.length == 0) {
			if (isNotEntity(sender, usage))
				return false;
			
			p = (LivingEntity) sender;
			
			s(sender, "&aHealed!");
		} else {
			p = getEntity(sender, args[0]);
			
			if (p == null) {
                return false;
            }
			
			s(sender, "&aHealed &6%s", getEntityName(p));
		}

		setHealth(p, getMaxHealth(p));
		p.setFireTicks(0);
		return true;
	}

}

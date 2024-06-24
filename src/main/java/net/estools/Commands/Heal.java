package net.estools.Commands;

import net.estools.EntityCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsLivingEntity;

public class Heal extends EntityCommand {
	private static final String usage = genUsage("/heal [entity]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		EsLivingEntity entity;
		if (args.length == 0) {
			if (isNotEntity(sender, usage)) {
                return false;
            }
			
			entity = (EsLivingEntity) sender;
			send(sender, "&aHealed!");
		} else {
			entity = getEntity(sender, args[0]);
			
			if (entity == null) {
                return false;
            }
			
			send(sender, "&aHealed &6%s", entity.getName());
		}

		entity.setHealth(entity.getMaxHealth());
		entity.setOnFireTicks(0);
		return true;
	}

}

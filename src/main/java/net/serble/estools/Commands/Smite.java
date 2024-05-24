package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;

public class Smite extends EntityCommand {
	private static final String usage = genUsage("/smite <entity1> [entity2] [entity3]...");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		for (String arg : args) {
			EsLivingEntity target = getEntity(sender, arg);
			
			if (target == null) {
				return false;
			}

			target.getWorld().strikeLightning(target.getLocation());
		}

        send(sender, "&aBAM!");
		return true;
	}

}

package net.estools.Commands;

import net.estools.EntityCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEntity;

import java.util.ArrayList;
import java.util.List;

public class Mount extends EntityCommand {
	private static final String usage = genUsage("/mount <ridee> [rider1] [rider2]...");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}

		EsEntity target = getNonLivingEntity(sender, args[0]);

		if (target == null) {
			return false;
		}

		List<EsEntity> riders = new ArrayList<>();
		for (int i = 1; i < args.length; i++) {
			EsEntity rider = getNonLivingEntity(sender, args[i]);
			if (rider == null) {
				return false;
			}
			riders.add(rider);
		}

		if (riders.isEmpty()) {
			if (!(sender instanceof EsEntity)) {
				send(sender, "&cConsole must specify a rider");
				return false;
			}
			riders.add((EsEntity) sender);
		}

		for (EsEntity entity : riders) {
			if (entity.getUniqueId() == target.getUniqueId()) {
				send(sender, "&cSorry, but you cant ride yourself! (the world ends)");
				return false;
			}

			target.addPassenger(entity);
			target = entity;
		}

        send(sender, "&aMounted!");
		return true;
	}

}

package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsEntity;

import java.util.ArrayList;
import java.util.List;

public class Dismount extends EntityCommand {

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		List<EsEntity> targets = new ArrayList<>();
		for (String arg : args) {  // Can only be living Entity in pre 1.2
			EsEntity entity = Main.minecraftVersion.getMinor() >= 2 ? getNonLivingEntity(sender, arg) : getEntity(sender, arg);
			if (entity == null) {
				return false;
			}
			targets.add(entity);
		}

		if (targets.isEmpty()) {
			if (!(sender instanceof EsEntity)) {
				send(sender, "&cConsole must specify a rider");
				return false;
			}

			targets.add((EsEntity) sender);
		}

		boolean anySuccess = false;
		for (EsEntity entity : targets) {
			anySuccess = anySuccess || entity.leaveVehicle();
		}

		if (!anySuccess) {
			send(sender, "&cNone of the targets were riding an entity");
			return false;
		}

		send(sender, "&aDismounted!");
		return true;
	}

}

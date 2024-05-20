package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Dismount extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<Entity> targets = new ArrayList<>();
		for (String arg : args) {  // Can only be living Entity in pre 1.2
			Entity entity = Main.majorVersion >= 2 ? getNonLivingEntity(sender, arg) : getEntity(sender, arg);
			if (entity == null) {
				return false;
			}
			targets.add(entity);
		}

		if (targets.isEmpty()) {
			if (!(sender instanceof Entity)) {
				send(sender, "&cConsole must specify a rider");
				return false;
			}

			targets.add((Entity) sender);
		}

		boolean anySuccess = false;
		for (Entity entity : targets) {
			if (Main.majorVersion >= 2) {
				anySuccess = anySuccess || entity.leaveVehicle();
			} else {
				anySuccess = anySuccess || ((LivingEntity) sender).leaveVehicle();
			}
		}

		if (!anySuccess) {
			send(sender, "&cNone of the targets were riding an entity");
			return false;
		}

		send(sender, "&aDismounted!");
		return true;
	}

}

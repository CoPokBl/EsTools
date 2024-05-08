package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Mount extends EntityCommand {
	private static final String usage = genUsage("/mount <ridee> [rider1] [rider2]...");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}

		Entity target = getNonLivingEntity(sender, args[0]);

		if (target == null) {
			return false;
		}

		List<Entity> riders = new ArrayList<>();
		for (int i = 1; i < args.length; i++) {
			riders.add(getNonLivingEntity(sender, args[i]));
		}

		if (riders.isEmpty()) {
			if (!(sender instanceof Entity)) {
				send(sender, "&cConsole must specify a rider");
				return false;
			}
			riders.add((Entity) sender);
		}

		for (Entity entity : riders) {
			if (Main.majorVersion > 11) {
				target.addPassenger(entity);
			} else {
                //noinspection deprecation
                target.setPassenger(entity);
			}
			target = entity;
		}

        send(sender, "&aMounted!");
		return true;
	}

}

package net.serble.estools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import net.serble.estools.EntityCommand;

public class Smite extends EntityCommand {
	private static final String usage = genUsage("/smite <entity1> [entity2] [entity3]...");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		for (String arg : args) {
			LivingEntity target = getEntity(sender, arg);
			
			if (target == null) {
				return false;
			}

			target.getWorld().strikeLightning(target.getLocation());
		}

        send(sender, "&aBAM!");
		return true;
	}

}

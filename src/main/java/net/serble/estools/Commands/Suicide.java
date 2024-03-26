package net.serble.estools.Commands;

import net.serble.estools.EntityCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class Suicide extends EntityCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isNotPlayer(sender))
			return false;

		LivingEntity p;
		if (args.length == 0) {
			p = (LivingEntity) sender;
		}
		else {
			p = getEntity(sender, args[0]);
			if (p == null) {
				s(sender, "&cPlayer does not exist!");
				return false;
			}
		}

		setHealth(p, 0);
		s(sender, "&aRest in peace");
		return true;
	}

}

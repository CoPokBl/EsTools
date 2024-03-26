package net.serble.estools.Commands;

import net.serble.estools.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class Suicide extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (isNotPlayer(sender))
			return false;
		
		LivingEntity p = (LivingEntity) sender;

		setHealth(p, 0);
		return true;
	}

}

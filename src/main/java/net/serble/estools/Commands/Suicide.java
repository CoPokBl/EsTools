package net.serble.estools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import net.serble.estools.PlayerCommand;

public class Suicide extends PlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		LivingEntity p = (LivingEntity) sender;

		setHealth(p, 0);
		send(sender, "&aRest in peace");
		return true;
	}
}

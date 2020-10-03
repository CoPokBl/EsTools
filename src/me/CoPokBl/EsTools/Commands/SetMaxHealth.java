package me.CoPokBl.EsTools.Commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class SetMaxHealth extends CMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "setmaxhealth"))
			return false;
		
		if (args.length == 0) {
			s(sender, genUsage("/setmaxhealth <amount> [player]"));
		}
		
		Player p;
		double health;
		
		if (args.length > 1) {
			p = getPlayer(sender, args[1]);
			
			if (p == null)
				return false;
		} else {
			if (isNotPlayer(sender))
				return false;
			
			p = (Player) sender;
		}
		
		try {
			health = Double.valueOf(args[0]);
		} catch (Exception e) {
			s(sender, genUsage("/sethealth <amount> [player]"));
			return false;
		}
		
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
		return true;
	}

}

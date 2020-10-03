package me.CoPokBl.EsTools.Commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CoPokBl.EsTools.CMD;

public class SetHealth extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "sethealth"))
			return false;
		
		if (args.length == 0) {
			s(sender, genUsage("/sethealth <amount> [player]"));
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
		
		if (health >= 0) {
			double maxhealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
			
			if (maxhealth < health)
				health = maxhealth;
			
			p.setHealth(health);
			
			if (args.length > 1)
				s(sender, "&aSet health for %s to %s", p.getName(), String.valueOf(health));
			else
				s(sender, "&aSet health to %s", String.valueOf(health));
		}
		else
			s(sender, "&4Cannot set health to less than 0");
		return true;
	}
}

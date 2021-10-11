package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.PlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSaturation extends PlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, genUsage("/sethunger <amount> [entity]"));
		}
		
		Player p;
		float saturation;
		
		if (args.length > 1) {
			p = CMD.getPlayer(sender, args[1]);
			
			if (p == null)
				return false;
		} else {
			if (isNotPlayer(sender))
				return false;
			
			p = (Player)sender;
		}
		
		try {
			saturation = Float.parseFloat(args[0]);
		} catch (Exception e) {
			s(sender, genUsage("/sethunger <amount> [entity]"));
			return false;
		}
		
		if (saturation >= 0) {
			float maxSaturation = 20f;

			if (maxSaturation < saturation)
				saturation = maxSaturation;
			
			p.setSaturation(saturation);
			
			if (args.length > 1)
				s(sender, "&aSet saturation for &6%s&a for &6%s", p.getName(), String.valueOf(saturation));
			else
				s(sender, "&aSet saturation to &6%s", String.valueOf(saturation));
		}
		else
			s(sender, "&cCannot set saturation to less than 0");
		return true;
	}
}

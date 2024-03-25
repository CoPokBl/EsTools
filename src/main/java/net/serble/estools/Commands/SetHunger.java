package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.PlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHunger extends PlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, genUsage("/sethunger <amount> [entity]"));
		}
		
		Player p;
		int hunger;
		
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
			hunger = Integer.parseInt(args[0]);
		} catch (Exception e) {
			s(sender, genUsage("/sethunger <amount> [entity]"));
			return false;
		}
		
		if (hunger >= 0) {
			int maxHunger = 20;

			if (maxHunger < hunger)
				hunger = maxHunger;
			
			p.setFoodLevel(hunger);
			
			if (args.length > 1)
				s(sender, "&aSet hunger for &6%s&a for &6%d", p.getName(), hunger);
			else
				s(sender, "&aSet hunger to &6%d", hunger);
		}
		else
			s(sender, "&cCannot set hunger to less than 0");
		return true;
	}
}

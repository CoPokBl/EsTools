package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.PlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHunger extends PlayerCommand {
	private static final String usage = genUsage("/sethunger <amount> [player]");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		Player p;
		int hunger;
		
		if (args.length > 1) {
			p = EsToolsCommand.getPlayer(sender, args[1]);
			
			if (p == null) {
                return false;
            }
		} else {
			if (isNotPlayer(sender)) {
                return false;
            }
			
			p = (Player)sender;
		}
		
		try {
			hunger = Integer.parseInt(args[0]);
		} catch (Exception e) {
			send(sender, usage);
			return false;
		}

        if (hunger < 0) {
			send(sender, "&cCannot set hunger to less than 0");
			return false;
		}

		int maxHunger = 20;

		if (maxHunger < hunger) {
			hunger = maxHunger;
		}

		p.setFoodLevel(hunger);

		if (args.length > 1) {
			send(sender, "&aSet hunger for &6%s&a for &6%d", p.getName(), hunger);
		} else {
			send(sender, "&aSet hunger to &6%d", hunger);
		}
        return true;
	}
}

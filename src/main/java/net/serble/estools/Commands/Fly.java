package net.serble.estools.Commands;

import net.serble.estools.MultiPlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class Fly extends MultiPlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		ArrayList<Player> ps = new ArrayList<>();
		
		if (args.length == 0) {
			if (isNotPlayer(sender, genUsage("/fly [Player]"))) 
				return false;
			
			ps.add((Player) sender);
		} else {
			ps = getPlayers(sender, args[0]);
			
			if (ps.isEmpty())
				return false;
		}

        for (Player p : ps) {
			boolean isFly = p.getAllowFlight();

			if (args.length != 0 && !Objects.equals(args[0], "*")) {
				if (isFly)
					s(sender, "&aFly Disabled for &6%s", p.getName());
				else
					s(sender, "&aFly Enabled for &6%s", p.getName());
			}

			p.setAllowFlight(!isFly);

			if (isFly)
				s(p, "&aFly Disabled!");
			else
				s(p, "&aFly Enabled!");
		}
		return true;
	}

}

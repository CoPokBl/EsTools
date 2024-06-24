package net.estools.Commands;

import net.estools.MultiPlayerCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.Objects;

public class Fly extends MultiPlayerCommand {
	private static final String usage = genUsage("/fly [Player]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		ArrayList<EsPlayer> players = new ArrayList<>();
		
		if (args.length == 0) {
			if (isNotPlayer(sender, usage)) {
                return false;
            }
			
			players.add((EsPlayer) sender);
		} else {
			players = getPlayers(sender, args[0]);
			
			if (players.isEmpty()) {
                return false;
            }
		}

        for (EsPlayer p : players) {
			boolean isFly = p.getAllowFlight();

			if (args.length != 0 && !Objects.equals(args[0], "*")) {
				if (isFly) {
                    send(sender, "&aFly Disabled for &6%s", p.getName());
                } else {
                    send(sender, "&aFly Enabled for &6%s", p.getName());
                }
			}

			p.setAllowFlight(!isFly);

			if (isFly) {
                send(p, "&aFly Disabled!");
            } else {
                send(p, "&aFly Enabled!");
            }
		}

		return true;
	}
}

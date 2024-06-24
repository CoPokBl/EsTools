package net.estools.Commands;

import net.estools.MultiPlayerCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;

public class Feed extends MultiPlayerCommand {
	private static final String usage = genUsage("/feed <Player>");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		ArrayList<EsPlayer> players = new ArrayList<>();
		
		if (args.length == 0) {
			if (isNotPlayer(sender, usage)) {
                return false;
            }
			
			players.add((EsPlayer) sender);
		} else {
			players = getPlayers(sender, args);
			
			if (players.isEmpty()) {
                return false;
            }
		}

		for (EsPlayer p : players) {
			p.setFoodLevel(20);
			p.setSaturation(20);
		}

		if (args.length == 0) {
			send(sender, "&aFed!");
		} else {
			send(sender, "&aFed &6%s&a!", args[0]);
		}
		return true;
	}

}

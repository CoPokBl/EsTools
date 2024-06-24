package net.estools;

import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerCommand extends EsToolsCommand {
	
	@Override
	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();

		for (EsPlayer p : Main.server.getOnlinePlayers()) {
			tab.add(p.getName());
		}

		return tab;
	}
}

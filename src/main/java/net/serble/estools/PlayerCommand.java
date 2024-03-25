package net.serble.estools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerCommand extends CMD {
	
	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();

		for (Player p : Bukkit.getOnlinePlayers()) {
			tab.add(p.getName());
		}

		return tab;
	}
}

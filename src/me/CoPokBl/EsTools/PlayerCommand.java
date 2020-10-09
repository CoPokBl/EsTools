package me.CoPokBl.EsTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends CMD {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return getTabComplete(args);
	}
	
	public static List<String> getTabComplete(String[] args) {
		List<String> tab = new ArrayList<String>();

		String latestArg = args[args.length - 1].toLowerCase();

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().toLowerCase().startsWith(latestArg))
				tab.add(p.getName());
		}
		
		return tab;
	}
}

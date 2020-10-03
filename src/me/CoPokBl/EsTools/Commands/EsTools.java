package me.CoPokBl.EsTools.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Give;
import me.CoPokBl.EsTools.Main;

public class EsTools extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, "&aEsTools v" + Main.current.getDescription().getVersion());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (checkPerms(sender, "reload"))
				return false;
			
			s(sender, "&aReloading...");
			
			Give.init();
			
			s(sender, "&aReloaded!");
		} else {
			s(sender, "&aEsTools v" + Main.current.getDescription().getVersion());
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> tab = new ArrayList<String>();
		
		if (args.length == 1) {
			tab.add("reload");
			tab.add("version");
		}
		
		return tab;
	}
}

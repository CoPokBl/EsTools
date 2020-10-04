package me.CoPokBl.EsTools.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			
			Give.enable();
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				CChest.savePlayer(p);
			}
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				CChest.loadPlayer(p);
			}
			
			s(sender, "&aReloaded!");
		} else if (args[0].equalsIgnoreCase("help")) {
			s(sender, "&aCommands are:&6\n"
					+ "editsign\nench\nestools\n"
					+ "feed\nfix\nfly\ngethealth\n"
					+ "gms\ngmc\ngma\ngmsp\n"
					+ "god\nh\nheal\ni\ninvsee\n"
					+ "music\nsethealth\nsetmaxhealth\n"
					+ "smite\nsudo\nsuicide\ntp");
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
			tab.add("help");
		}
		
		return tab;
	}
}

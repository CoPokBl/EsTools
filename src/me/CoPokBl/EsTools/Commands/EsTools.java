package me.CoPokBl.EsTools.Commands;

import java.io.File;
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
		} else if (args[0].equalsIgnoreCase("reset")) {
			if (checkPerms(sender, "reset"))
				return false;
			
			if (args.length > 1 && args[1].equalsIgnoreCase("confirm")) {				
				File f = new File(Main.current.getDataFolder(), "give.yml");
				if (!f.delete()) {
					s(sender, "&cFailed to delete data.");
					return false;
				}
				
				Give.enable();
				s(sender, "&cAll data deleted!");
				return true;
			}
			s(sender, "&cWarning, you will lose data, use /estools reset confirm to reset");
		} else {
			s(sender, "&aEsTools v" + Main.current.getDescription().getVersion());
		}
		
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<String>();
		
		if (args.length == 1) {
			tab.add("reload");
			tab.add("version");
			tab.add("reset");
		}
		
		return tab;
	}
}

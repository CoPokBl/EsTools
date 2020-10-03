package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Give;
import me.CoPokBl.EsTools.Main;

public class EsTools extends CMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, "&aEsTools v" + Main.current.getDescription().getVersion());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (checkPerms(sender, "reload"))
				return false;
			
			Give.init();
		}
		
		return true;
	}

}

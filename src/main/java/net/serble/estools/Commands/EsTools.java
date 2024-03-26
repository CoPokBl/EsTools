package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.Give;
import net.serble.estools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
			
			s(sender, "&aReloaded!");
		} else {
			s(sender, "&aEsTools v" + Main.current.getDescription().getVersion());
		}
		
		return true;
	}

}

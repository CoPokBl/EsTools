package net.serble.estools.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.serble.estools.Tester;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Commands.Give.Give;
import net.serble.estools.Main;

public class EsTools extends EsToolsCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			send(sender, "&aEsTools v" + Main.plugin.getDescription().getVersion());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")) {  // Reloads the plugin
			if (checkPerms(sender, "reload")) {
                return false;
            }
			
			send(sender, "&aReloading...");
			
			Give.enable();
			
			for (Player p : getOnlinePlayers()) {
				CChest.savePlayer(p);
			}
			
			for (Player p : getOnlinePlayers()) {
				CChest.loadPlayer(p);
			}
			
			send(sender, "&aReloaded!");
		} else if (args[0].equalsIgnoreCase("reset")) {  // Resets all configuration files
			if (checkPerms(sender, "reset")) {
                return false;
            }
			
			if (args.length > 1 && args[1].equalsIgnoreCase("confirm")) {
				File f = new File(Main.plugin.getDataFolder(), "give.yml");
				if (!f.delete()) {
					send(sender, "&cFailed to delete data.");
					return false;
				}
				
				Give.enable();
				send(sender, "&cAll data deleted!");
				return true;
			}

			send(sender, "&cWarning, this will reset all configuration files, use /estools reset confirm to reset");
		} else if (args[0].equalsIgnoreCase("test")) {
			if (!(sender instanceof Player)) {
				send(sender, "&cYou must be a player to use this command.");
				return false;
			}
			Tester tester = new Tester((Player) sender);
			tester.startTests();
		} else if (args[0].equalsIgnoreCase("throw")) {
			throw new RuntimeException("Test exception");
		} else {
			send(sender, "&aEsTools v" + Main.plugin.getDescription().getVersion());
		}
		
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();
		
		if (args.length == 1) {
			tab.add("reload");
			tab.add("version");
			tab.add("reset");
			tab.add("test");
		}
		
		return tab;
	}
}

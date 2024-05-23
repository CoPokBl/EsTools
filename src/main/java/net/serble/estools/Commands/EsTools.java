package net.serble.estools.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.serble.estools.Tester;
import net.serble.estools.Updater;
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
			if (checkPerms(sender, "version")) {
				return false;
			}

			sendVersion(sender);
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
			if (checkPerms(sender, "test")) {
				return false;
			}

			if (!(sender instanceof Player)) {
				send(sender, "&cYou must be a player to use this command.");
				return false;
			}

			Player p = (Player) sender;
			Tester tester = Tester.runningTests.get(p.getUniqueId());

			if (tester != null) {
				tester.continueTests();
				return true;
			}

			tester = new Tester(p);
			Tester.runningTests.put(p.getUniqueId(), tester);
			tester.startTests();
		} else if (args[0].equalsIgnoreCase("throw")) {  // This isn't in tab complete because it's a dev command
			if (checkPerms(sender, "throw")) {
				return false;
			}
			throw new RuntimeException("Test exception");
		} else if (args[0].equalsIgnoreCase("update")) {
			if (checkPerms(sender, "update")) {
				return false;
			}

			if (Main.newVersionReady) {
				send(sender, "&cThe new version has already been downloaded and is waiting on a server restart/reload");
				return false;
			}

			if (Main.newVersion == null) {
				send(sender, "&cThere are no available updates, use &6/estools forceupdate &cto force a download of the latest version");
				return false;
			}

			send(sender, "&aDownloading version: &6" + Main.newVersion.getString());
			Updater.downloadNewUpdate(sender);
		} else if (args[0].equalsIgnoreCase("forceupdate")) {
			send(sender, "&aForce updating &c(This might downgrade your plugin)");
			Updater.downloadNewUpdate(sender);
		} else {
			sendVersion(sender);
		}
		
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();
		
		if (args.length == 1) {
			if (hasPerm(sender, "reload")) tab.add("reload");
			if (hasPerm(sender, "version")) tab.add("version");
			if (hasPerm(sender, "reset")) tab.add("reset");
			if (hasPerm(sender, "test")) tab.add("test");
			if (hasPerm(sender, "update")) {
				tab.add("update");
				tab.add("forceupdate");
			}
		}
		
		return tab;
	}

	private void sendVersion(CommandSender sender) {
		if (Main.newVersion == null) {
			send(sender, "&aEsTools v" + Main.plugin.getDescription().getVersion());
		} else {
			send(sender, "&aEsTools &cv" + Main.plugin.getDescription().getVersion() + ".&c An update is available, use " +
					"&6/estools update&c to update to &6v" + Main.newVersion.getString());
		}
	}

}

package net.estools.Commands;

import net.estools.*;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.ServerPlatform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EsTools extends EsToolsCommand {

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
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
			
			Main.plugin.enable();
			
			send(sender, "&aReloaded!");
		} else if (args[0].equalsIgnoreCase("reset")) {  // Resets all configuration files
			if (checkPerms(sender, "reset")) {
                return false;
            }
			
			if (args.length > 1 && args[1].equalsIgnoreCase("confirm")) {
				File f = Main.server.getDataFolder();
				if (!Utils.deleteFolder(f)) {
					send(sender, "&cFailed to delete data.");
					return false;
				}

				send(sender, "&cAll data has been deleted, &6/estools reload &cto apply changes!");
				return true;
			}

			send(sender, "&cWarning, this will reset all configuration files, use /estools reset confirm to reset");
		} else if (args[0].equalsIgnoreCase("test")) {
			if (checkPerms(sender, "test")) {
				return false;
			}

			if (!(sender instanceof EsPlayer)) {
				send(sender, "&cYou must be a player to use this command.");
				return false;
			}

			if (Main.platform == ServerPlatform.Folia) {  // TODO: Fix
				send(sender, "&cDue to scheduler limitations, /estools test is not currently supported on Folia");
				return false;
			}

			EsPlayer p = (EsPlayer) sender;
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
		} else if (args[0].equalsIgnoreCase("throwunimplemented")) {
			if (checkPerms(sender, "throw")) {
				return false;
			}
			throw new NotImplementedException("Test exception");
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

			send(sender, "&aDownloading version: &6" + Main.newVersion);
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
	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
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

	private void sendVersion(EsCommandSender sender) {
		if (Main.newVersion == null) {
			send(sender, "&aEsTools v" + Main.server.getPluginVersion().toString());
		} else {
			send(sender, "&aEsTools &cv" + Main.server.getPluginVersion().toString() + ".&c An update is available, use " +
					"&6/estools update&c to update to &6v" + Main.newVersion.toString());
		}
	}

}

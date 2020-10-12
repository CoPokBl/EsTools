package me.CoPokBl.EsTools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import me.CoPokBl.EsTools.Commands.*;

public class Main extends JavaPlugin {
	
	public static Main current;
	public static int version;
	
	// TODO: /infinite (makes things not get consumed when you use), fix shift click cchest bug, /sun
	
	@Override
	public void onEnable() {
		getVersion();

		// Commands
		
		sc("gms", "gm", new GM());
		sc("gmc", "gm", new GM());
		sc("gma", "gm", new GM());
		sc("gmsp", "gm", new GM());
		sc("tphere", "tp", new TP());
		sc("tpall", "tp", new TP());
		sc("feed", "feed", new Feed());
		sc("heal", "heal", new Heal());
		sc("sudo", "sudo", new Sudo());
		sc("fly", "fly", new Fly());
		sc("suicide", "suicide", new Suicide());
		sc("smite", "smite", new Smite());
		sc("sethealth", "sethealth", new SetHealth());
		sc("setmaxhealth", "setmaxhealth", new SetMaxHealth());
		sc("invsee", "invsee", new Invsee());
		sc("i", "give", new I(), new Give());
		sc("h", "give", new H(), new Give());
		sc("estools", new EsTools());
		sc("ench", "ench", new Ench());
		sc("fix", "fix", new Fix());
		sc("getinfo", "getinfo", new GetInfo());
		sc("editsign", "editsign", new EditSign());
		sc("cchest", new CChest());
		sc("rename", "rename", new Rename());
		sc("back", "back", new Back());
		sc("setstack", "setstack", new SetStack());
		sc("ci", "clearinv", new ClearInv());
		sc("powerpick", "powerpick", new PowerPick());

		if (version > 8) {
			sc("god", "god", new God());

			if (version > 12) {
				sc("music", "music", new Music());
				Music.init();
			} else {
				sc("music", "music", new WrongVersion());
			}
		} else {
			sc("god", "god", new WrongVersion());
		}




		// Other
		
		Bukkit.getServer().getPluginManager().registerEvents(new CChest(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Back(), this);
		
		current = this;
		Give.enable();
		PowerPick.init();
	}
	
	@Override
	public void onDisable() {
		Give.disable();
	}
	
	public PluginCommand sc(String name, CommandExecutor ce) {
		PluginCommand cmd = getCommand(name);
		cmd.setExecutor(ce);
		return cmd;
	}
	
	public PluginCommand sc(String name, CommandExecutor ce, TabCompleter tc) {
		PluginCommand cmd = sc(name, ce);
		cmd.setTabCompleter(tc);
		return cmd;
	}
	
	public PluginCommand sc(String name, String perm, CommandExecutor ce) {
		PluginCommand cmd = sc(name, ce);
		cmd.setPermission("estools." + perm);
		cmd.setPermissionMessage(CMD.t("&cYou do not have permission to run this command."));
		return cmd;
	}
	
	public PluginCommand sc(String name, String perm, CommandExecutor ce, TabCompleter tc) {
		PluginCommand cmd = sc(name, perm, ce);
		cmd.setTabCompleter(tc);
		return cmd;
	}

	private void getVersion() {
		String versionS = Bukkit.getVersion();

		       if (versionS.contains("1.16")) {
			version = 16;
		} else if (versionS.contains("1.15")) {
			version = 15;
		} else if (versionS.contains("1.14")) {
			version = 14;
		} else if (versionS.contains("1.13")) {
			version = 13;
		} else if (versionS.contains("1.12")) {
			version = 12;
		} else if (versionS.contains("1.11")) {
			version = 11;
		} else if (versionS.contains("1.10")) {
			version = 10;
		} else if (versionS.contains("1.9")) {
			version = 9;
		} else if (versionS.contains("1.8")) {
		    version = 8;
	    } else if (versionS.contains("1.7")) {
	    	version = 7;
	    } else if (versionS.contains("1.6")) {
		    version = 6;
 	    } else if (versionS.contains("1.5")) {
 		    version = 5;
 	    } else if (versionS.contains("1.4")) {
 		    version = 4;
 	    } else if (versionS.contains("1.3")) {
		    version = 3;
	    } else if (versionS.contains("1.2")) {
	 	    version = 2;
	    } else if (versionS.contains("1.1")) {
		    version = 1;
	    } else if (versionS.contains("1.0")) {
		    version = 0;
	    } else {
			// idk more than supported?
			version = 17;
	    }
	}
}


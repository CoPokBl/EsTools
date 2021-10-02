package me.CoPokBl.EsTools;

import me.CoPokBl.EsTools.Commands.GameMode.*;
import me.CoPokBl.EsTools.Commands.PowerPick.*;
import me.CoPokBl.EsTools.Commands.Teleport.TPAll;
import me.CoPokBl.EsTools.Commands.Teleport.TPHere;
import me.CoPokBl.EsTools.Signs.SignMain;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import me.CoPokBl.EsTools.Commands.*;

public class Main extends JavaPlugin {
	
	public static Main current;
	public static int version;
	
	// TODO: "normal" effects names, MultiEntityCommand
	
	@Override
	public void onEnable() {
		current = this;

		try {
			Vault.setupEconomy();
		} catch (Exception e) {
			getLogger().warning("No Vault plugin found, please install vault for economy functionality");
		}

		getVersion();

		// Commands
		
		sc("gms", "gm", new gms());
		sc("gmc", "gm", new gmc());
		sc("gma", "gm", new gma());
		sc("gmsp", "gm", new gmsp());
		sc("tphere", "tp", new TPHere());
		sc("tpall", "tp", new TPAll());
		sc("feed", "feed", new Feed());
		sc("fly", "fly", new Fly());
		sc("smite", "smite", new Smite());
		sc("invsee", "invsee", new Invsee());
		sc("i", "give", new I(), new Give());
		sc("h", "give", new H(), new Give());
		sc("estools", new EsTools());
		sc("ench", "ench", new Ench());
		sc("fix", "fix", new Fix());
		sc("cchest", new CChest());
		sc("back", "back", new Back());
		sc("setstack", "setstack", new SetStack());
		sc("ci", "clearinv", new ClearInv());
		sc("sun", "time", new Sun());
		sc("moon", "time", new Night());
		sc("walkspeed", "walkspeed", new WalkSpeed());
		sc("flyspeed", "flyspeed", new FlySpeed());
		sc("setunbreakable", "setunbreakable", new SetUnbreakable());
		sc("hideflags", "hideflags", new HideFlags());
		sc("eff", "effect", new Eff());
		sc("safetp", "safetp", new SafeTP());
		sc("infinite", "infinite", new Infinite());

		sc("powerpick", "powerpick", new PowerPick());
		sc("poweraxe", "powerpick", new PowerAxe());
		sc("powersword", "powerpick", new PowerSword());
		sc("powershovel", "powerpick", new PowerShovel());
		sc("powerhoe", "powerpick", new PowerHoe());

		if (version > 3) {
			sc("rename", "rename", new Rename());
			sc("sudo", "sudo", new Sudo());

			if (version > 5) {
				sc("heal", "heal", new Heal());
				sc("suicide", "suicide", new Suicide());
				sc("sethealth", "sethealth", new SetHealth());
				sc("setmaxhealth", "setmaxhealth", new SetMaxHealth());
				sc("getinfo", "getinfo", new GetInfo());

				if (version > 7) {
					sc("editsign", "editsign", new EditSign());

					if (version > 8) {
						sc("god", "god", new God());

						if (version > 12) {
							sc("music", "music", new Music());
							Music.init();
						}
					}
				}
			}
		} else {
			sc("heal", "heal", new WrongVersion());
			sc("suicide", "suicide", new WrongVersion());
			sc("sethealth", "sethealth", new WrongVersion());
			sc("setmaxhealth", "setmaxhealth", new WrongVersion());
			sc("getinfo", "getinfo", new WrongVersion());
			sc("music", "music", new WrongVersion());
			sc("god", "god", new WrongVersion());
			sc("editsign", "editsign", new WrongVersion());
			sc("rename", "rename", new WrongVersion());
			sc("sudo", "sudo", new WrongVersion());
		}

		// Other

		SignMain.init();

		if (version > 4) {
			Bukkit.getServer().getPluginManager().registerEvents(new CChest(), this);
		}

		Bukkit.getServer().getPluginManager().registerEvents(new Back(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SafeTP(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SignMain(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Infinite(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new God(), this);

		PowerPick.initall();

		if (Main.version < 7) {
			Main.current.getLogger().info("Saving not supported in 1.6 or below.");
		} else {
			Give.enable();
		}
	}
	
	@Override
	public void onDisable() {}
	
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

		for (int i = 0; i < 99; i++) {
			if (versionS.contains("1." + i))  version = i;
		}

	    getLogger().info("version detected as: " + version + " from: " + versionS);
	}
}


package net.serble.estools;

import net.serble.estools.Commands.GameMode.*;
import net.serble.estools.Commands.PowerPick.*;
import net.serble.estools.Commands.Teleport.TPAll;
import net.serble.estools.Commands.Teleport.TPHere;
import net.serble.estools.Signs.SignMain;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import net.serble.estools.Commands.*;

public class Main extends JavaPlugin {
	
	public static Main current;
	public static int version;
	public static int minorVersion;
	public static boolean tabCompleteEnabled = true;

	// MultiEntityCommand
	
	@Override
	public void onEnable() {
		current = this;

		try {
			Vault.setupEconomy();
		} catch (Exception e) {
			Bukkit.getLogger().warning("No Vault plugin found, please install vault for economy functionality");
		}

		getVersion();

		if (version > 0) {  // Doesn't exist in 1.0 lol
			saveDefaultConfig();
		}

		if (version <= 2) {
			Bukkit.getLogger().info("Tab completion is not supported for versions 1.2 and below.");
			tabCompleteEnabled = false;
		}

		// Commands
		
		sc("gms", "gm", new gms());
		sc("gmc", "gm", new gmc());
		sc("gma", "gm", new gma(), 3);
		sc("gmsp", "gm", new gmsp(), 8);
		sc("tphere", "tp", new TPHere());
		sc("tpall", "tp", new TPAll());
		sc("feed", "feed", new Feed());
		sc("fly", "fly", new Fly(), 2);
		sc("smite", "smite", new Smite());
		sc("invsee", "invsee", new Invsee(), 2);
		sc("i", "give", new I(), new Give());
		sc("h", "give", new H(), new Give());
		sc("estools", new EsTools());
		sc("ench", "ench", new Ench());
		sc("fix", "fix", new Fix());
		sc("cchest", new CChest(), 7);
		sc("back", "back", new Back());
		sc("setstack", "setstack", new SetStack());
		sc("ci", "clearinv", new ClearInv());
		sc("sun", "time", new Sun());
		sc("moon", "time", new Night());
		sc("walkspeed", "walkspeed", new WalkSpeed(), 4);
		sc("flyspeed", "flyspeed", new FlySpeed(), 3);
		sc("setunbreakable", "setunbreakable", new SetUnbreakable());
		sc("hideflags", "hideflags", new HideFlags(), 8);
		sc("eff", "effect", new Eff());
		sc("safetp", "safetp", new SafeTP());
		sc("infinite", "infinite", new Infinite());
		sc("sethunger", "sethunger", new SetHunger());
		sc("setsaturation", "setsaturation", new SetSaturation());

		sc("powerpick", "powerpick", new PowerPick());
		sc("poweraxe", "powerpick", new PowerAxe());
		sc("powersword", "powerpick", new PowerSword());
		sc("powershovel", "powerpick", new PowerShovel());
		sc("powerhoe", "powerpick", new PowerHoe());

		sc("rename", "rename", new Rename(), null, 4, 6);
		sc("sudo", "sudo", new Sudo());

		sc("heal", "heal", new Heal());
		sc("suicide", "suicide", new Suicide());
		sc("sethealth", "sethealth", new SetHealth());
		sc("setmaxhealth", "setmaxhealth", new SetMaxHealth(), 3);
		sc("getinfo", "getinfo", new GetInfo());

		sc("editsign", "editsign", new EditSign());

		sc("god", "god", new God());

		sc("music", "music", new Music(), 13);
		sc("potion", "potion", new Potion(), 4);

		sc("setpersistentdata", "setpersistentdata", new SetPersistentData(), 14);
		sc("getpersistentdata", "getpersistentdata", new GetPersistentData(), 14);
		sc("removepersistentdata", "removepersistentdata", new RemovePersistentData(), 14);

		// Other

		SignMain.init();

		Bukkit.getServer().getPluginManager().registerEvents(new SignMain(), this);

		PowerPick.initall();

		if (Main.version < 7) {
			Bukkit.getLogger().info("Saving not supported in 1.6 or below.");
		}

		Give.enable();
	}
	
	@Override
	public void onDisable() {}
	
	public PluginCommand sc(String name, CMD ce) {
		PluginCommand cmd = getCommand(name);
		cmd.setExecutor(ce);
		ce.onEnable();

		if (tabCompleteEnabled && cmd.getTabCompleter() == null) {
			ce.register(cmd);
		}
		return cmd;
	}

	public PluginCommand sc(String name, CMD ce, int minVer) {
		if (Main.version >= minVer) return sc(name, ce);
		else return sc(name, new WrongVersion(minVer));
	}
	
	public PluginCommand sc(String name, CMD ce, EsToolsTabCompleter tc) {
		PluginCommand cmd = sc(name, ce);
		if (tabCompleteEnabled) {
			tc.register(cmd);
		}
		return cmd;
	}

	public PluginCommand sc(String name, CMD ce, EsToolsTabCompleter tc, int minVer) {
		if (Main.version >= minVer) return sc(name, ce, tc);
		else return sc(name, new WrongVersion(minVer), new WrongVersion(minVer));
	}

	public PluginCommand sc(String name, String perm, CMD ce, EsToolsTabCompleter tc, int minMajor, int minMinor) {
		String versionName = minMajor + "." + minMinor;
		if (Main.version > minMajor || (Main.version == minMajor && Main.minorVersion >= minMinor)) {
			if (tc == null) {
				return sc(name, perm, ce);
			}

            return sc(name, perm, ce, tc);
        } else return sc(name, new WrongVersion(versionName), new WrongVersion(versionName));
	}

	public PluginCommand sc(String name, String perm, CMD ce) {
		PluginCommand cmd = sc(name, ce);
		cmd.setPermission("estools." + perm);

		if (Main.version > 0) {  // Permission errors weren't a thing in 1.0
			cmd.setPermissionMessage(CMD.t("&cYou do not have permission to run this command."));
		}

		if (tabCompleteEnabled && cmd.getTabCompleter() == null) {  // Give every command tab complete if they haven't already registered it
			ce.register(cmd);
		}
		return cmd;
	}

	public PluginCommand sc(String name, String perm, CMD ce, int minVer) {
		if (Main.version >= minVer) return sc(name, perm, ce);
		else return sc(name, perm, new WrongVersion(minVer));
	}
	
	public PluginCommand sc(String name, String perm, CMD ce, EsToolsTabCompleter tc) {
		PluginCommand cmd = sc(name, perm, ce);
		if (tabCompleteEnabled) {
			tc.register(cmd);
		}
		return cmd;
	}

	public PluginCommand sc(String name, String perm, CMD ce, EsToolsTabCompleter tc, int minVer) {
		if (Main.version >= minVer) return sc(name, perm, ce, tc);
		else return sc(name, perm, new WrongVersion(minVer), new WrongVersion(minVer));
	}

	private void getVersion() {
		String versionS = Bukkit.getVersion();

		if (versionS.contains("(MC: 1.0.0)")) {
			version = 0;
			minorVersion = 0;
			return;
		}

		for (int i = 0; i < 99; i++) {
			if (versionS.contains("1." + i)) {
				version = i;
			}
		}

		for (int i = 0; i < 99; i++) {
			if (versionS.contains("1." + version + '.' + i)) {
				minorVersion = i;
			}
		}

		Bukkit.getLogger().info("Version detected as: 1." + version + '.' + minorVersion + " from: " + versionS);
	}
}


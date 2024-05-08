package net.serble.estools;

import net.serble.estools.Commands.GameMode.*;
import net.serble.estools.Commands.Give.Give;
import net.serble.estools.Commands.Give.GiveItem;
import net.serble.estools.Commands.Give.SetHandItem;
import net.serble.estools.Commands.MoveSpeed.FlySpeed;
import net.serble.estools.Commands.MoveSpeed.WalkSpeed;
import net.serble.estools.Commands.PowerPick.*;
import net.serble.estools.Commands.Teleport.*;
import net.serble.estools.Commands.Warps.*;
import net.serble.estools.Signs.SignMain;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import net.serble.estools.Commands.*;

@SuppressWarnings("UnusedReturnValue")
public class Main extends JavaPlugin {
	public static Main plugin;
	public static int majorVersion;
	public static int minorVersion;
	public static boolean tabCompleteEnabled = true;

	private static final int bStatsId = 21760;
	
	@Override
	public void onEnable() {
		plugin = this;

		try {
			Vault.setupEconomy();
		} catch (Exception e) {
			Bukkit.getLogger().warning("No Vault plugin found, please install vault for economy functionality.");
		}

		getVersion();  // Set the major and minor version variables

		if (majorVersion > 0) {  // Config doesn't exist in 1.0 lol
			saveDefaultConfig();
		}

		// Metrics
		if (getConfig().getBoolean("metrics", true)) {
			Metrics metrics = new Metrics(this, bStatsId);
			metrics.addCustomChart(new SimplePie("vault_enabled", () -> String.valueOf(Vault.economy != null)));
			Bukkit.getLogger().info("Started bStat metrics");
		} else {
			Bukkit.getLogger().info("Metrics are disabled");
		}

		if (majorVersion <= 2) {
			Bukkit.getLogger().info("Tab completion is not supported for versions 1.2 and below.");
			tabCompleteEnabled = false;
		}

		// Commands
		sc("gms", "gm", new Gms());
		sc("gmc", "gm", new Gmc());
		sc("gma", "gm", new Gma(), 3);
		sc("gmsp", "gm", new Gmsp(), 8);
		sc("tphere", "tp", new TpHere());
		sc("tpall", "tp", new TpAll());
		sc("feed", "feed", new Feed());
		sc("fly", "fly", new Fly(), 2);
		sc("smite", "smite", new Smite());
		sc("invsee", "invsee", new InvSee(), 2);
		sc("i", "give", new GiveItem(), new Give());
		sc("h", "give", new SetHandItem(), new Give());
		sc("estools", new EsTools());
		sc("ench", "ench", new Ench(), 1);
		sc("fix", "fix", new Fix());
		sc("cchest", new CChest(), 7);
		sc("back", "back", new Back(), 1);
		sc("setstack", "setstack", new SetStack());
		sc("ci", "clearinv", new ClearInv());
		sc("day", "time", new Day());
		sc("moon", "time", new Night());
		sc("noon", "time", new Noon());
		sc("midnight", "time", new Midnight());
		sc("sun", "weather", new Sun());
		sc("rain", "weather", new Rain());
		sc("thunder", "weather", new Thunder());
		sc("walkspeed", "walkspeed", new WalkSpeed(), 4);
		sc("flyspeed", "flyspeed", new FlySpeed(), 3);
		sc("setunbreakable", "setunbreakable", new SetUnbreakable(), 1);
		sc("hideflags", "hideflags", new HideFlags(), 8);
		sc("eff", "effect", new Eff(), 1);
		sc("safetp", "safetp", new SafeTp(), 1);
		sc("infinite", "infinite", new Infinite(), 1);
		sc("sethunger", "sethunger", new SetHunger());
		sc("setsaturation", "setsaturation", new SetSaturation());

		sc("powerpick", "powerpick", new PowerPick(), 1);
		sc("poweraxe", "powerpick", new PowerAxe(), 1);
		sc("powersword", "powerpick", new PowerSword(), 1);
		sc("powershovel", "powerpick", new PowerShovel(), 1);
		sc("powerhoe", "powerpick", new PowerHoe(), 1);

		sc("lore", "lore", new Lore(), null, 4, 6);
		sc("rename", "rename", new Rename(), null, 4, 6);
		sc("sudo", "sudo", new Sudo());

		sc("heal", "heal", new Heal());
		sc("suicide", "suicide", new Suicide());
		sc("sethealth", "sethealth", new SetHealth());
		sc("setmaxhealth", "setmaxhealth", new SetMaxHealth(), 3);
		sc("getinfo", "getinfo", new GetInfo());

		sc("editsign", "editsign", new EditSign());

		sc("god", "god", new God(), 1);

		sc("music", "music", new Music(), 9);
		sc("potion", "potion", new Potion(), 4);

		sc("setpersistentdata", "setpersistentdata", new SetPersistentData(), 14);
		sc("getpersistentdata", "getpersistentdata", new GetPersistentData(), 14);
		sc("removepersistentdata", "removepersistentdata", new RemovePersistentData(), 14);

		sc("warp", "warps.use", new Warp());
		sc("warps", "warps.manage", new WarpManager());

		sc("mount", "mount", new Mount());
		sc("dismount", "mount", new Dismount());

		// Load other features
		if (majorVersion > 0) {  // Enchants and events don't work on 1.0.0
			PowerTool.init();
			SignMain.init();
		}

		if (Main.majorVersion < 7) {
			Bukkit.getLogger().info("Saving configs not supported in 1.6 or below.");
		}

		Give.enable();
	}

	@Override
	public void onDisable() { /* Needed for older versions, which require an onDisable method */ }


	// Setup Command Overloads

	public PluginCommand sc(String name, EsToolsCommand ce) {
		PluginCommand cmd = getCommand(name);
        assert cmd != null;
        cmd.setExecutor(ce);
		ce.onEnable();

		if (tabCompleteEnabled && cmd.getTabCompleter() == null) {
			ce.register(cmd);
		}
		return cmd;
	}

	public PluginCommand sc(String name, EsToolsCommand ce, int minVer) {
		if (Main.majorVersion >= minVer) return sc(name, ce);
		else return sc(name, new WrongVersion(minVer));
	}
	
	public PluginCommand sc(String name, EsToolsCommand ce, EsToolsTabCompleter tc) {
		PluginCommand cmd = sc(name, ce);
		if (tabCompleteEnabled) {
			tc.register(cmd);
		}
		return cmd;
	}

	public PluginCommand sc(String name, String perm, EsToolsCommand ce, EsToolsTabCompleter tc, int minMajor, int minMinor) {
		String versionName = minMajor + "." + minMinor;
		if (Main.majorVersion > minMajor || (Main.majorVersion == minMajor && Main.minorVersion >= minMinor)) {
			if (tc == null) {
				return sc(name, perm, ce);
			}

            return sc(name, perm, ce, tc);
        } else return sc(name, new WrongVersion(versionName), new WrongVersion(versionName));
	}

	public PluginCommand sc(String name, String perm, EsToolsCommand ce) {
		PluginCommand cmd = sc(name, ce);
		cmd.setPermission("estools." + perm);

		if (Main.majorVersion > 0) {  // Permission errors weren't a thing in 1.0
            //noinspection deprecation, is still useful in pre 1.13 and technically is useful in rare situations post 1.13
            cmd.setPermissionMessage(EsToolsCommand.translate("&cYou do not have permission to run this command."));
		}

		if (tabCompleteEnabled && cmd.getTabCompleter() == null) {  // Give every command tab complete if they haven't already registered it
			ce.register(cmd);
		}
		return cmd;
	}

	public PluginCommand sc(String name, String perm, EsToolsCommand ce, int minVer) {
		if (Main.majorVersion >= minVer) return sc(name, perm, ce);
		else return sc(name, perm, new WrongVersion(minVer));
	}
	
	public PluginCommand sc(String name, String perm, EsToolsCommand ce, EsToolsTabCompleter tc) {
		PluginCommand cmd = sc(name, perm, ce);
		if (tabCompleteEnabled) {
			tc.register(cmd);
		}
		return cmd;
	}

	private void getVersion() {  // Parse the minecraft version from the Bukkit version string
		String versionS = Bukkit.getVersion();

		if (versionS.contains("(MC: ")) {
			int posOfMC = versionS.indexOf("(MC: ") + 5;
			versionS = versionS.substring(posOfMC, versionS.indexOf(')', posOfMC));
		} else {
			Bukkit.getLogger().warning("Could not detect version from: " + versionS);
			return;
		}

		for (int i = 0; i < 99; i++) {
			if (versionS.contains("1." + i)) {
				majorVersion = i;
			}
		}

		for (int i = 0; i < 99; i++) {
			if (versionS.contains("1." + majorVersion + '.' + i)) {
				minorVersion = i;
			}
		}

		Bukkit.getLogger().info("Version detected as: 1." + majorVersion + '.' + minorVersion + " from: " + versionS);
	}
}


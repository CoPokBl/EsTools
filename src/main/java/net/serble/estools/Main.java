package net.serble.estools;

import net.serble.estools.Commands.GameMode.*;
import net.serble.estools.Commands.PowerPick.*;
import net.serble.estools.Commands.Teleport.TPAll;
import net.serble.estools.Commands.Teleport.TPHere;
import net.serble.estools.Signs.SignMain;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import net.serble.estools.Commands.*;

public class Main extends JavaPlugin {
	
	public static Main current;
	public static int version;

	// MultiEntityCommand
	
	@Override
	public void onEnable() {
		current = this;

		try {
			Vault.setupEconomy();
		} catch (Exception e) {
			getLogger().warning("No Vault plugin found, please install vault for economy functionality");
		}

		getVersion();

		saveDefaultConfig();

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

		sc("rename", "rename", new Rename(), 4);
		sc("sudo", "sudo", new Sudo(), 4);

		sc("heal", "heal", new Heal(), 6);
		sc("suicide", "suicide", new Suicide(), 6);
		sc("sethealth", "sethealth", new SetHealth(), 6);
		sc("setmaxhealth", "setmaxhealth", new SetMaxHealth(), 6);
		sc("getinfo", "getinfo", new GetInfo(), 6);

		sc("editsign", "editsign", new EditSign(), 8);

		sc("god", "god", new God(), 9);

		sc("music", "music", new Music(), 13);
		sc("potion", "potion", new Potion());

		sc("setpersistentdata", "setpersistentdata", new SetPersistentData(), 14);
		sc("getpersistentdata", "getpersistentdata", new GetPersistentData(), 14);
		sc("removepersistentdata", "removepersistentdata", new RemovePersistentData(), 14);

		// Other

		SignMain.init();

		Bukkit.getServer().getPluginManager().registerEvents(new SignMain(), this);

		PowerPick.initall();

		if (Main.version < 7) {
			Main.current.getLogger().info("Saving not supported in 1.6 or below.");
		} else {
			Give.enable();
		}
	}
	
	@Override
	public void onDisable() {}
	
	public PluginCommand sc(String name, CMD ce) {
		PluginCommand cmd = getCommand(name);
		cmd.setExecutor(ce);
		ce.onEnable();
		return cmd;
	}

	public PluginCommand sc(String name, CMD ce, int minVer) {
		if (Main.version >= minVer) return sc(name, ce);
		else return sc(name, new WrongVersion());
	}
	
	public PluginCommand sc(String name, CMD ce, TabCompleter tc) {
		PluginCommand cmd = sc(name, ce);
		cmd.setTabCompleter(tc);
		return cmd;
	}

	public PluginCommand sc(String name, CMD ce, TabCompleter tc, int minVer) {
		if (Main.version >= minVer) return sc(name, ce, tc);
		else return sc(name, new WrongVersion(), new WrongVersion());
	}
	
	public PluginCommand sc(String name, String perm, CMD ce) {
		PluginCommand cmd = sc(name, ce);
		cmd.setPermission("estools." + perm);
		cmd.setPermissionMessage(CMD.t("&cYou do not have permission to run this command."));
		return cmd;
	}

	public PluginCommand sc(String name, String perm, CMD ce, int minVer) {
		if (Main.version >= minVer) return sc(name, perm, ce);
		else return sc(name, perm, new WrongVersion());
	}
	
	public PluginCommand sc(String name, String perm, CMD ce, TabCompleter tc) {
		PluginCommand cmd = sc(name, perm, ce);
		cmd.setTabCompleter(tc);
		return cmd;
	}

	public PluginCommand sc(String name, String perm, CMD ce, TabCompleter tc, int minVer) {
		if (Main.version >= minVer) return sc(name, perm, ce, tc);
		else return sc(name, perm, new WrongVersion(), new WrongVersion());
	}

	private void getVersion() {
		String versionS = Bukkit.getVersion();

		for (int i = 0; i < 99; i++) {
			if (versionS.contains("1." + i))  version = i;
		}

	    getLogger().info("version detected as: " + version + " from: " + versionS);
	}
}


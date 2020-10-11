package me.CoPokBl.EsTools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import me.CoPokBl.EsTools.Commands.*;

public class Main extends JavaPlugin {
	
	public static Main current;
	
	// Things to add: /infinite (makes things not get consumed when you use)
	
	@Override
	public void onEnable() {		
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
		sc("god", "god", new God());
		sc("music", "music", new Music());
		sc("cchest", new CChest());
		sc("rename", "rename", new Rename());
		sc("back", "back", new Back());
		sc("setstack", "setstack", new SetStack());
		sc("ci", "clearinv", new ClearInv());
		sc("powerpick", "powerpick", new PowerPick());

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
}


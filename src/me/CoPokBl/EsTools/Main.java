package me.CoPokBl.EsTools;

import org.bukkit.plugin.java.JavaPlugin;

import me.CoPokBl.EsTools.Commands.*;

public class Main extends JavaPlugin {
	
	public static Main current;
	
	@Override
	public void onEnable() {		
		// Commands
		
		getCommand("gms").setExecutor(new GM());
		getCommand("gmc").setExecutor(new GM());
		getCommand("gma").setExecutor(new GM());
		getCommand("gmsp").setExecutor(new GM());
		getCommand("tphere").setExecutor(new TP());
		getCommand("tpall").setExecutor(new TP());
		getCommand("feed").setExecutor(new Feed());
		getCommand("heal").setExecutor(new Heal());
		getCommand("sudo").setExecutor(new Sudo());
		getCommand("fly").setExecutor(new Fly());
		getCommand("suicide").setExecutor(new Suicide());
		getCommand("smite").setExecutor(new Smite());
		getCommand("sethealth").setExecutor(new SetHealth());
		getCommand("setmaxhealth").setExecutor(new SetMaxHealth());
		getCommand("invsee").setExecutor(new Invsee());
		getCommand("i").setExecutor(new I());
		getCommand("h").setExecutor(new H());
		getCommand("estools").setExecutor(new EsTools());
		getCommand("ench").setExecutor(new Ench());
		getCommand("fix").setExecutor(new Fix());
		getCommand("gethealth").setExecutor(new GetHealth());
		getCommand("editsign").setExecutor(new EditSign());
		getCommand("god").setExecutor(new God());

		// Other
		
		current = this;
		Give.init();
	}
	
	@Override
	public void onDisable() {
		Give.disable();
	}
}


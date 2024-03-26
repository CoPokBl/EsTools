package net.serble.estools;

import net.serble.estools.Commands.*;
import net.serble.estools.Commands.Teleport.TPAll;
import net.serble.estools.Commands.Teleport.TPHere;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main current;
	
	@Override
	public void onEnable() {
		current = this;

		// Commands

		sc("tphere", "tp", new TPHere());
		sc("tpall", "tp", new TPAll());
		sc("smite", "smite", new Smite());
		sc("i", "give", new I());
		sc("h", "give", new H());
		sc("estools", new EsTools());
		sc("fix", "fix", new Fix());
		sc("setstack", "setstack", new SetStack());
		sc("ci", "clearinv", new ClearInv());
		sc("sun", "time", new Sun());
		sc("moon", "time", new Night());

		sc("sudo", "sudo", new Sudo());

		sc("heal", "heal", new Heal());
		sc("suicide", "suicide", new Suicide());
		sc("sethealth", "sethealth", new SetHealth());
		sc("getinfo", "getinfo", new GetInfo());

		sc("editsign", "editsign", new EditSign());

		Give.enable();
	}
	
	@Override
	public void onDisable() {}
	
	public PluginCommand sc(String name, CMD ce) {
		PluginCommand cmd = getCommand(name);
        assert cmd != null;
        cmd.setExecutor(ce);
		ce.onEnable();
		return cmd;
	}

	public PluginCommand sc(String name, String perm, CMD ce) {
		PluginCommand cmd = sc(name, ce);
		cmd.setPermission("estools." + perm);
		return cmd;
	}

}

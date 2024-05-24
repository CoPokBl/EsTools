package net.serble.estools.Entrypoints;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.ServerPlatform;
import org.bukkit.plugin.java.JavaPlugin;

public class EsToolsBukkit extends JavaPlugin {
    /** Will be null if plugin is not Bukkit */
    public static EsToolsBukkit plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Main main = new Main(ServerPlatform.Bukkit, this);
        main.enable();
    }

    @Override
    public void onDisable() { /* Needed for older versions, which require an onDisable method */ }
}

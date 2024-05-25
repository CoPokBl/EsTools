package net.serble.estools.Entrypoints;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.ServerPlatform;
import org.bukkit.plugin.java.JavaPlugin;

/** This will be called for Bukkit and Folia platforms */
public class EsToolsBukkit extends JavaPlugin {
    /** Will be null if plugin is not Bukkit */
    public static EsToolsBukkit plugin;

    @Override
    public void onEnable() {
        plugin = this;

        ServerPlatform platform = isFolia() ? ServerPlatform.Folia : ServerPlatform.Bukkit;

        Main main = new Main(platform, this);
        main.enable();
    }

    @Override
    public void onDisable() { /* Needed for older versions, which require an onDisable method */ }

    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

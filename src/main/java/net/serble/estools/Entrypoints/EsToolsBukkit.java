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
        // This is needed for SnakeYAML to work on Spigot, see https://www.spigotmc.org/threads/cannot-use-shaded-snakeyaml-to-construct-class-object.136707/#post-4329381
        Thread.currentThread().setContextClassLoader(this.getClassLoader());

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

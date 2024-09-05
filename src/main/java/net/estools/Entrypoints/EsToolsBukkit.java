package net.estools.Entrypoints;

import net.estools.Main;
import net.estools.ServerApi.Implementations.Bukkit.BukkitCommandManager;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitEffectHelper;
import net.estools.ServerApi.ServerPlatform;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/** This will be called for Bukkit and Folia platforms */
public class EsToolsBukkit extends JavaPlugin {
    /** Will be null if plugin is not Bukkit */
    public static EsToolsBukkit plugin;
    private BukkitCommandManager commandManager;

    @Override
    public void onEnable() {
        // This is needed for SnakeYAML to work on Spigot, see https://www.spigotmc.org/threads/cannot-use-shaded-snakeyaml-to-construct-class-object.136707/#post-4329381
        Thread.currentThread().setContextClassLoader(this.getClassLoader());

        plugin = this;

        ServerPlatform platform = isFolia() ? ServerPlatform.Folia : ServerPlatform.Bukkit;

        Main main = new Main(platform, this);
        main.enable();

        commandManager = (BukkitCommandManager) Main.server.getCommandManager();

        // Init any Bukkit helpers that need it.
        // It's best to do it here because Folia and Bukkit
        // need it.
        // This must come after Main.enable() so that mcVersion is set.
        // It also only works on 1.1+
        if (Main.minecraftVersion.isAtLeast(1, 1, 0)) {
            BukkitEffectHelper.load();
        }
    }

    @Override
    public void onDisable() { /* Needed for older versions, which require an onDisable method */ }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        commandManager.onCommand(sender, command, label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return commandManager.onTabComplete(sender, command.getLabel(), args);
    }

    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsLogger;
import org.bukkit.Bukkit;

public class BukkitLogger implements EsLogger {
    @Override
    public void debug(String msg) {
        Bukkit.getLogger().info(msg);
    }

    @Override
    public void info(String msg) {
        Bukkit.getLogger().info(msg);
    }

    @Override
    public void warning(String msg) {
        Bukkit.getLogger().warning(msg);
    }

    @Override
    public void severe(String msg) {
        Bukkit.getLogger().severe(msg);
    }
}

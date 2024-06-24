package net.estools.ServerApi.Implementations.Folia;

import net.estools.ServerApi.Implementations.Bukkit.BukkitConsoleSender;
import org.bukkit.command.ConsoleCommandSender;

public class FoliaConsoleSender extends BukkitConsoleSender {

    public FoliaConsoleSender(ConsoleCommandSender child) {
        super(child);
    }
}

package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitConsoleSender;
import org.bukkit.command.ConsoleCommandSender;

public class FoliaConsoleSender extends BukkitConsoleSender {
    private final ConsoleCommandSender bukkitSender;

    public FoliaConsoleSender(ConsoleCommandSender child) {
        super(child);
        bukkitSender = child;
    }
}

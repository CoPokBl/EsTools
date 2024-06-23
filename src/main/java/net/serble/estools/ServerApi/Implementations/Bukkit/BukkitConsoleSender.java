package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsConsoleSender;
import org.bukkit.command.ConsoleCommandSender;

public class BukkitConsoleSender implements EsConsoleSender {
    private final ConsoleCommandSender bukkitSender;

    public BukkitConsoleSender(ConsoleCommandSender child) {
        bukkitSender = child;
    }

    @Override
    public void sendMessage(String... msg) {
        if (Main.minecraftVersion.isAtLeast(1, 2, 0)) {
            bukkitSender.sendMessage(msg);
            return;
        }

        // The sendMessage(String[]) method doesn't exist
        // So combine the args into one String
        StringBuilder sb = new StringBuilder();
        for (String s : msg) {
            sb.append(s);
        }
        bukkitSender.sendMessage(sb.toString());
    }

    @Override
    public boolean hasPermission(String node) {
        return bukkitSender.hasPermission(node);
    }

    @Override
    public boolean isPermissionSet(String node) {
        return bukkitSender.isPermissionSet(node);
    }
}

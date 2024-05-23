package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsConsoleSender;
import org.bukkit.command.BlockCommandSender;

public class BukkitCommandBlockSender implements EsConsoleSender {
    private final BlockCommandSender bukkitSender;

    public BukkitCommandBlockSender(BlockCommandSender child) {
        bukkitSender = child;
    }

    @Override
    public void sendMessage(String... msg) {
        bukkitSender.sendMessage(msg);
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

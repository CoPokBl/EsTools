package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.ServerApi.Interfaces.EsCommandBlockSender;
import org.bukkit.command.BlockCommandSender;

public class BukkitCommandBlockSender implements EsCommandBlockSender {
    private final BlockCommandSender bukkitSender;

    public BukkitCommandBlockSender(BlockCommandSender child) {
        bukkitSender = child;
    }

    public BlockCommandSender getBukkitSender() {
        return bukkitSender;
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

package net.estools.ServerApi.Implementations.Minestom;

import net.estools.ServerApi.Interfaces.EsConsoleSender;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;

public class MinestomConsoleSender implements EsConsoleSender {
    private final ConsoleSender sender = MinecraftServer.getCommandManager().getConsoleSender();

    @Override
    public void sendMessage(String... msg) {
        sender.sendMessage(msg);
    }

    @Override
    public boolean hasPermission(String node) {
        return true;
    }

    @Override
    public boolean isPermissionSet(String node) {
        return false;
    }
}

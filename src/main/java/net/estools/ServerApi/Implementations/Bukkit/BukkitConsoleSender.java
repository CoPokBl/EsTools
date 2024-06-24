package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.Main;
import net.estools.ServerApi.Interfaces.EsConsoleSender;
import net.estools.Utils;
import org.bukkit.command.ConsoleCommandSender;

// We have to execute methods using reflection because
// in 1.0 ConsoleCommandSender is a class not an interface
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

        if (Main.minecraftVersion.isLowerThan(1, 1, 0)) {
            try {
                ConsoleCommandSender.class.getMethod("sendMessage", String.class).invoke(bukkitSender, sb.toString());
            } catch (Exception e) {
                Main.logger.severe(Utils.getStacktrace(e));
            }
            return;
        }
        bukkitSender.sendMessage(sb.toString());
    }

    @Override
    public boolean hasPermission(String node) {
        if (Main.minecraftVersion.isLowerThan(1, 1, 0)) {
            try {
                return (boolean) ConsoleCommandSender.class
                        .getMethod("hasPermission", String.class)
                        .invoke(bukkitSender, node);
            } catch (Exception e) {
                Main.logger.severe(Utils.getStacktrace(e));
            }
            return false;
        }

        return bukkitSender.hasPermission(node);
    }

    @Override
    public boolean isPermissionSet(String node) {
        if (Main.minecraftVersion.isLowerThan(1, 1, 0)) {
            try {
                return (boolean) ConsoleCommandSender.class
                        .getMethod("isPermissionSet", String.class)
                        .invoke(bukkitSender, node);
            } catch (Exception e) {
                Main.logger.severe(Utils.getStacktrace(e));
            }
            return false;
        }

        return bukkitSender.isPermissionSet(node);
    }
}

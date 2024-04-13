package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SafeTp extends EsToolsCommand implements Listener {
    private static boolean enabled = true;

    @Override
    public void onEnable() {
        enabled = Main.plugin.getConfig().getBoolean("safetp", true);
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        if (enabled) {
            enabled = false;
            send(sender, "&aTeleporting now &6&lWILL&a make you take fall damage!");
        } else {
            enabled = true;
            send(sender, "&aTeleporting now &6&lWILL NOT&a make you take fall damage!");
        }

        Main.plugin.getConfig().set("safetp", enabled);
        Main.plugin.saveConfig();
        return true;
    }

    @EventHandler
    public void teleport(final PlayerTeleportEvent e) {
        if (enabled && equalsOr(e.getCause(), PlayerTeleportEvent.TeleportCause.COMMAND,
                PlayerTeleportEvent.TeleportCause.PLUGIN, PlayerTeleportEvent.TeleportCause.UNKNOWN)) {
            e.getPlayer().setFallDistance(0);
        }
    }
}


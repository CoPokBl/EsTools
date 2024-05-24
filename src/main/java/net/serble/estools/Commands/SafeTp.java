package net.serble.estools.Commands;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

// TODO: Events command, needs migrating
public class SafeTp extends EsToolsCommand implements Listener {
    public static boolean enabled = true;

    @Override
    public void onEnable() {
        enabled = Main.plugin.getConfig().getBoolean("safetp", true);
        Bukkit.getServer().getPluginManager().registerEvents(this, EsToolsBukkit.plugin);
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (enabled) {
            enabled = false;
            send(sender, "&aTeleporting now &6&lWILL&a make you take fall damage!");
        } else {
            enabled = true;
            send(sender, "&aTeleporting now &6&lWILL NOT&a make you take fall damage!");
        }

        Main.plugin.getConfig().set("safetp", enabled);
        EsToolsBukkit.plugin.saveConfig();  // TODO: How does this work?
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


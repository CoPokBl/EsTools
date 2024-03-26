package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.EventsHelper;
import net.serble.estools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SafeTP extends CMD implements Listener {

    static boolean enabled = true;

    @Override
    public void onEnable() {
        enabled = Main.current.getConfig().getBoolean("safetp", true);
        EventsHelper.registerEvents(this, EventsHelper.EventType.PlayerTeleport);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotPlayer(sender))
            return true;

        if (enabled) {
            enabled = false;
            s(sender, "&aTeleporting now &6&lWILL&a make you take fall damage!");
        } else {
            enabled = true;
            s(sender, "&aTeleporting now &6&lWILL NOT&a make you take fall damage!");
        }

        Main.current.getConfig().set("safetp", enabled);
        Main.current.saveConfig();
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


package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.UUID;

public class SafeTP extends CMD implements Listener {

    static boolean enabled = true;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotPlayer(sender))
            return true;

        UUID pu = ((Player) sender).getUniqueId();

        if (enabled) {
            enabled = false;
            s(sender, "&aTeleporting will now &6&lWILL&a make you take fall damage!");
        } else {
            enabled = true;
            s(sender, "&aTeleporting will now &6&lWILL NOT&a make you take fall damage!");
        }
        return true;
    }

    @EventHandler
    public void teleport(final PlayerTeleportEvent e) {
        if (enabled)
            if (equalsOr(e.getCause(), PlayerTeleportEvent.TeleportCause.COMMAND, PlayerTeleportEvent.TeleportCause.PLUGIN))
                e.getPlayer().setFallDistance(0);
    }
}


package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.Bukkit;
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
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.current);
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
        if (enabled)
            if (equalsOr(e.getCause(), PlayerTeleportEvent.TeleportCause.COMMAND, PlayerTeleportEvent.TeleportCause.PLUGIN))
                e.getPlayer().setFallDistance(0);
    }
}


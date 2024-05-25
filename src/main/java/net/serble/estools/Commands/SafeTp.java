package net.serble.estools.Commands;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEventListener;
import net.serble.estools.ServerApi.EsTeleportCause;
import net.serble.estools.ServerApi.Events.EsPlayerTeleportEvent;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsEvent;

public class SafeTp extends EsToolsCommand implements EsEventListener {
    public static boolean enabled = true;

    @Override
    public void onEnable() {
        enabled = Main.plugin.getConfig().getBoolean("safetp", true);
        Main.registerEvents(this);
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

    @Override
    public void executeEvent(EsEvent event) {
        if (!(event instanceof EsPlayerTeleportEvent)) {
            return;
        }
        EsPlayerTeleportEvent e = (EsPlayerTeleportEvent) event;

        if (enabled && equalsOr(e.getCause(), EsTeleportCause.Command, EsTeleportCause.Plugin, EsTeleportCause.Unknown)) {
            e.getPlayer().setFallDistance(0);
        }
    }
}


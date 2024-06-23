package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEventListener;
import net.serble.estools.ServerApi.EsTeleportCause;
import net.serble.estools.ServerApi.Events.EsPlayerTeleportEvent;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.ServerPlatform;

public class SafeTp extends EsToolsCommand implements EsEventListener {
    public static boolean enabled = true;

    @Override
    public void onEnable() {
        enabled = Main.plugin.getConfig().isSafeTp();
        Main.registerEvents(this);
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (Main.platform == ServerPlatform.Folia) {
            send(sender, "&cCurrently due to Folia issues SafeTP is not supported");
            return false;
        }

        if (enabled) {
            enabled = false;
            send(sender, "&aTeleporting now &6&lWILL&a make you take fall damage!");
        } else {
            enabled = true;
            send(sender, "&aTeleporting now &6&lWILL NOT&a make you take fall damage!");
        }

        Main.plugin.getConfig().setSafeTp(enabled);
        Main.plugin.saveConfig();
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


package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.Main;
import net.estools.ServerApi.EsEventListener;
import net.estools.ServerApi.EsTeleportCause;
import net.estools.ServerApi.Events.EsPlayerTeleportEvent;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.ServerPlatform;

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


package net.estools.ServerApi.Events;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsTeleportCause;
import net.estools.ServerApi.Interfaces.EsCancellableEvent;
import net.estools.ServerApi.Interfaces.EsPlayer;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class EsPlayerTeleportEvent extends EsCancellableEvent {
    private final EsPlayer player;
    private final EsTeleportCause cause;
    private final EsLocation to;

    public EsPlayerTeleportEvent(EsPlayer p, EsTeleportCause c, EsLocation to) {
        player = p;
        cause = c;
        this.to = to;
    }

    public EsPlayer getPlayer() {
        return player;
    }

    public EsTeleportCause getCause() {
        return cause;
    }
}

package net.serble.estools.ServerApi.Events;

import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.EsTeleportCause;
import net.serble.estools.ServerApi.Interfaces.EsCancellableEvent;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

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

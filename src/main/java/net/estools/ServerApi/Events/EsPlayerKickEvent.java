package net.estools.ServerApi.Events;

import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class EsPlayerKickEvent implements EsEvent {
    private final EsPlayer player;

    public EsPlayerKickEvent(EsPlayer player) {
        this.player = player;
    }

    public EsPlayer getPlayer() {
        return player;
    }
}

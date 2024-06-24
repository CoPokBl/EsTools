package net.estools.ServerApi.Events;

import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class EsPlayerQuitEvent implements EsEvent {
    private final EsPlayer player;

    public EsPlayerQuitEvent(EsPlayer player) {
        this.player = player;
    }

    public EsPlayer getPlayer() {
        return player;
    }
}

package net.serble.estools.ServerApi.Events;

import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class EsPlayerQuitEvent implements EsEvent {
    private final EsPlayer player;

    public EsPlayerQuitEvent(EsPlayer player) {
        this.player = player;
    }

    public EsPlayer getPlayer() {
        return player;
    }
}

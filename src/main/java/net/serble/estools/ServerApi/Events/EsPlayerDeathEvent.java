package net.serble.estools.ServerApi.Events;

import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class EsPlayerDeathEvent implements EsEvent {
    private final EsPlayer player;

    public EsPlayerDeathEvent(EsPlayer p) {
        player = p;
    }

    public EsPlayer getPlayer() {
        return player;
    }
}

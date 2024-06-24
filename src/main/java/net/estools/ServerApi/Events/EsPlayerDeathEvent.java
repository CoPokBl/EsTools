package net.estools.ServerApi.Events;

import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class EsPlayerDeathEvent implements EsEvent {
    private final EsPlayer player;

    public EsPlayerDeathEvent(EsPlayer p) {
        player = p;
    }

    public EsPlayer getPlayer() {
        return player;
    }
}

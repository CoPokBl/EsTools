package net.estools.ServerApi.Events;

import net.estools.ServerApi.Interfaces.EsCancellableEvent;
import net.estools.ServerApi.Interfaces.EsPlayer;

@SuppressWarnings("unused")
public class EsSignChangeEvent extends EsCancellableEvent {
    private final EsPlayer player;
    private final String[] lines;

    public EsSignChangeEvent(EsPlayer player, String[] lines) {
        this.player = player;
        this.lines = lines;
    }

    public EsPlayer getPlayer() {
        return player;
    }

    public String[] getLines() {
        return lines;
    }

    public String getLine(int line) {
        return lines[line];
    }

    public void setLine(int line, String text) {
        lines[line] = text;
    }
}

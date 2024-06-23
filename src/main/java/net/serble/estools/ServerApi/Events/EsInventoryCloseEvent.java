package net.serble.estools.ServerApi.Events;

import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class EsInventoryCloseEvent implements EsEvent {
    private final EsPlayer player;
    private final EsInventory inv;

    public EsInventoryCloseEvent(EsPlayer player, EsInventory inv) {
        this.player = player;
        this.inv = inv;
    }

    public EsPlayer getPlayer() {
        return player;
    }

    public EsInventory getInventory() {
        return inv;
    }
}

package net.estools.ServerApi.Events;

import net.estools.ServerApi.Interfaces.EsCancellableEvent;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsInventoryView;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.Set;

public class EsInventoryDragEvent extends EsCancellableEvent {
    private final EsPlayer player;
    private final EsInventory inv;
    private final Set<Integer> changedSlots;
    private final EsInventoryView view;

    public EsInventoryDragEvent(EsPlayer player, EsInventory inv, Set<Integer> changedSlots, EsInventoryView view) {
        this.player = player;
        this.inv = inv;
        this.changedSlots = changedSlots;
        this.view = view;
    }

    public EsPlayer getPlayer() {
        return player;
    }

    public EsInventory getInventory() {
        return inv;
    }

    public Set<Integer> getChangedSlots() {
        return changedSlots;
    }

    public EsInventoryView getView() {
        return view;
    }
}

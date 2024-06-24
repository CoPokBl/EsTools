package net.estools.ServerApi.Events;

import net.estools.ServerApi.EsEquipmentSlot;
import net.estools.ServerApi.Interfaces.EsCancellableEvent;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class EsBlockPlaceEvent extends EsCancellableEvent {
    private final EsPlayer player;
    private final EsItemStack placedItem;
    private final EsEquipmentSlot hand;

    public EsBlockPlaceEvent(EsPlayer p, EsItemStack item, EsEquipmentSlot ha) {
        player = p;
        placedItem = item;
        hand = ha;
    }

    public EsPlayer getPlayer() {
        return player;
    }

    public EsItemStack getPlacedItem() {
        return placedItem;
    }

    public EsEquipmentSlot getHand() {
        return hand;
    }
}

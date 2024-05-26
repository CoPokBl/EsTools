package net.serble.estools.ServerApi.Events;

import net.serble.estools.ServerApi.EsClickType;
import net.serble.estools.ServerApi.EsInventoryAction;
import net.serble.estools.ServerApi.Interfaces.EsCancellableEvent;
import net.serble.estools.ServerApi.Interfaces.EsInventory;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class EsInventoryClickEvent extends EsCancellableEvent {
    private final EsInventory clickedInv;
    private final EsInventory inv;
    private final EsItemStack currentItem;
    private final EsInventoryAction action;
    private final EsClickType click;
    private final EsPlayer player;
    private final EsItemStack cursor;
    private final int slot;

    public EsInventoryClickEvent(EsPlayer player, int slot, EsItemStack cursor, EsInventory inv, EsInventory clickedInv, EsItemStack currentItem, EsInventoryAction action, EsClickType click) {
        this.clickedInv = clickedInv;
        this.inv = inv;
        this.currentItem = currentItem;
        this.action = action;
        this.click = click;
        this.player = player;
        this.cursor = cursor;
        this.slot = slot;
    }

    public EsInventory getClickedInventory() {
        return clickedInv;
    }

    public EsItemStack getCurrentItem() {
        return currentItem;
    }

    public EsInventoryAction getAction() {
        return action;
    }

    public EsClickType getClick() {
        return click;
    }

    public EsPlayer getPlayer() {
        return player;
    }

    public EsInventory getInventory() {
        return inv;
    }

    public EsItemStack getCursor() {
        return cursor;
    }

    public int getSlot() {
        return slot;
    }
}

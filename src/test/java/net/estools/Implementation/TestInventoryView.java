package net.estools.Implementation;

import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsInventoryView;
import net.estools.ServerApi.Interfaces.EsPlayer;

@SuppressWarnings("unused")
public class TestInventoryView implements EsInventoryView {
    private final EsInventory topInventory;
    private final EsInventory bottomInventory;
    private final EsPlayer player;

    // TEST METHODS

    public TestInventoryView(EsPlayer player, EsInventory top, EsInventory bottom) {
        topInventory = top;
        bottomInventory = bottom;
        this.player = player;
    }

    // IMPLEMENTATION METHODS

    @Override
    public EsInventory getTopInventory() {
        return topInventory;
    }

    @Override
    public EsInventory getBottomInventory() {
        return bottomInventory;
    }

    @Override
    public EsInventory getInventory(int slot) {
        if (slot > bottomInventory.getSize()) {
            return topInventory;
        } else {
            return bottomInventory;
        }
    }

    @Override
    public EsPlayer getPlayer() {
        return player;
    }
}

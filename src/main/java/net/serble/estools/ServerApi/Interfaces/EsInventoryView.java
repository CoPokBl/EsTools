package net.serble.estools.ServerApi.Interfaces;

public interface EsInventoryView {
    EsInventory getTopInventory();
    EsInventory getBottomInventory();
    EsInventory getInventory(int slot);
    EsPlayer getPlayer();
}

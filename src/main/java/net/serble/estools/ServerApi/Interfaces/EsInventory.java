package net.serble.estools.ServerApi.Interfaces;

public interface EsInventory {
    void setItem(int slot, EsItemStack item);
    EsItemStack getItem(int slot);
    void addItem(EsItemStack stack);
    void setContents(EsItemStack[] items);
    EsItemStack[] getContents();
    void clear();
}

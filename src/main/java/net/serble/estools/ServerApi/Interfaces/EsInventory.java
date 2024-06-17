package net.serble.estools.ServerApi.Interfaces;

import java.util.Map;

public interface EsInventory {
    void setItem(int slot, EsItemStack item);
    EsItemStack getItem(int slot);
    void addItem(EsItemStack stack);
    void setContents(EsItemStack[] items);
    EsItemStack[] getContents();
    void clear();
    boolean isEqualTo(EsInventory inv);
    int getSize();

    /**
     * Gets a list of slots and the items in those slots that are of a specific material.
     *
     * @param material
     * The material to look for.
     *
     * @return
     * A list of slot to item mappings.
     */
    Map<Integer, EsItemStack> all(String material);
}

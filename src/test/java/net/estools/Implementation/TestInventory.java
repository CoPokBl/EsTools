package net.estools.Implementation;

import net.estools.NotImplementedException;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsItemStack;

import java.util.Arrays;
import java.util.Map;

public class TestInventory implements EsInventory {
    private EsItemStack[] items;

    // TEST METHODS

    public TestInventory() {
        items = new EsItemStack[54];
    }

    // IMPLEMENTATION METHODS

    @Override
    public void setItem(int slot, EsItemStack item) {
        items[slot] = item;
    }

    @Override
    public EsItemStack getItem(int slot) {
        return items[slot];
    }

    /**
     * Adds item to first null slot.
     */
    @Override
    public void addItem(EsItemStack stack) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = stack;
                return;
            }
        }
    }

    @Override
    public void setContents(EsItemStack[] items) {
        this.items = items;
    }

    @Override
    public EsItemStack[] getContents() {
        return items;
    }

    @Override
    public void clear() {
        Arrays.fill(items, null);
    }

    @Override
    public boolean isEqualTo(EsInventory inv) {
        return false;
    }

    @Override
    public int getSize() {
        return items.length;
    }

    @Override
    public Map<Integer, EsItemStack> all(EsMaterial material) {
        throw new NotImplementedException();
    }
}

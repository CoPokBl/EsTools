package net.estools.ServerApi.Implementations.Minestom;

import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.minestom.server.inventory.Inventory;

import java.util.Map;

public class MinestomInventory implements EsInventory {
    private final Inventory inventory;

    public MinestomInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void setItem(int slot, EsItemStack item) {

    }

    @Override
    public EsItemStack getItem(int slot) {
        return null;
    }

    @Override
    public void addItem(EsItemStack stack) {

    }

    @Override
    public void setContents(EsItemStack[] items) {

    }

    @Override
    public EsItemStack[] getContents() {
        return new EsItemStack[0];
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isEqualTo(EsInventory inv) {
        return false;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Map<Integer, EsItemStack> all(EsMaterial material) {
        return Map.of();
    }
}

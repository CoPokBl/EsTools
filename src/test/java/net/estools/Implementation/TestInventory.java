package net.estools.Implementation;

import net.estools.NotImplementedException;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsItemStack;

import java.util.Arrays;
import java.util.Map;

public class TestInventory implements EsInventory {
    private final EsItemStack[] items;
    private int size = 54;
    private String name;

    // TEST METHODS

    public TestInventory() {
        items = new EsItemStack[size];
    }

    public String getName() {
        return name;
    }

    public TestInventory(int size, String name) {
        items = new EsItemStack[size];
        this.size = size;
        this.name = name;
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
    public boolean addItemInternal(EsItemStack stack) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = stack;
                return true;
            }
        }

        return false;
    }

    @Override
    public void addItem(EsItemStack stack) {
        addItemInternal(stack);
    }

    @Override
    public void addItemOrDrop(EsItemStack stack, EsLocation location) {
        if (!addItemInternal(stack)) {
            TestWorld world = (TestWorld) location.getWorld();

            TestEntity entity = new TestEntity(world, "ITEM");
            entity.teleport(location);
            world.addEntity(entity);
        }
    }

    @Override
    public void setContents(EsItemStack[] items) {
        if (items.length > size) {
            throw new IllegalArgumentException("Inventory size is " + size + " but " + items.length + " items were provided.");
        }

        clear();
        System.arraycopy(items, 0, this.items, 0, items.length);
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
        if (!(inv instanceof TestInventory)) {
            return false;
        }
        return name.equals(((TestInventory) inv).getName());
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

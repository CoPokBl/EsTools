package net.estools.ServerApi.Implementations.Minestom;

import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Implementations.Minestom.Helpers.MinestomHelper;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinestomInventory implements EsInventory {
    private final Inventory inventory;

    public MinestomInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void setItem(int slot, EsItemStack item) {
        inventory.setItemStack(slot, ((MinestomItemStack) item).internal());
    }

    @Override
    public EsItemStack getItem(int slot) {
        return MinestomHelper.toItem(inventory.getItemStack(slot));
    }

    @Override
    public void addItem(EsItemStack stack) {
        inventory.addItemStack(((MinestomItemStack) stack).internal());
    }

    @Override
    public void setContents(EsItemStack[] items) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                inventory.setItemStack(i, null);
                continue;
            }
            inventory.setItemStack(i, ((MinestomItemStack) items[i]).internal());
        }
    }

    @Override
    public EsItemStack[] getContents() {
        List<EsItemStack> items = new ArrayList<>();
        for (ItemStack stack : inventory.getItemStacks()) {
            items.add(MinestomHelper.toItem(stack));
        }
        return items.toArray(new EsItemStack[0]);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public boolean isEqualTo(EsInventory inv) {
        return ((MinestomInventory) inv).inventory.equals(inventory);
    }

    @Override
    public int getSize() {
        return inventory.getSize();
    }

    @Override
    public Map<Integer, EsItemStack> all(EsMaterial material) {
        Map<Integer, EsItemStack> items = new HashMap<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            EsItemStack stack = getItem(i);
            if (stack == null) {
                continue;
            }
            if (stack.getType().equals(material)) {
                items.put(i, stack);
            }
        }
        return items;
    }
}

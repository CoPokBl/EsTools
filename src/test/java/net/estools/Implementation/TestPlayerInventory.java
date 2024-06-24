package net.estools.Implementation;

import net.estools.ServerApi.EsEquipmentSlot;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayerInventory;

@SuppressWarnings("unused")
public class TestPlayerInventory extends TestInventory implements EsPlayerInventory {
    private final TestPlayer owner;
    private int selectedSlot = 0;

    private static final int OffHandSlot = 45;
    private static final int HelmetSlot = 46;
    private static final int ChestplateSlot = 47;
    private static final int LeggingsSlot = 48;
    private static final int BootsSlot = 49;

    // TEST METHODS

    public TestPlayerInventory(TestPlayer owner) {
        this.owner = owner;
        setContents(new EsItemStack[54]);
    }

    public TestPlayer getOwner() {
        return owner;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    // IMPLEMENTATION METHODS

    @Override
    public EsItemStack getOffHand() {
        return getItem(OffHandSlot);
    }

    @Override
    public EsItemStack getMainHand() {
        return getItem(selectedSlot);
    }

    @Override
    public EsItemStack getHelmet() {
        return getItem(HelmetSlot);
    }

    @Override
    public EsItemStack getLeggings() {
        return getItem(LeggingsSlot);
    }

    @Override
    public EsItemStack getChestplate() {
        return getItem(ChestplateSlot);
    }

    @Override
    public EsItemStack getBoots() {
        return getItem(BootsSlot);
    }

    @Override
    public void setItem(EsEquipmentSlot slot, EsItemStack item) {
        switch (slot) {
            case OffHand:
                setItem(OffHandSlot, item);
                break;
            case Hand:
                setItem(selectedSlot, item);
                break;
            case Head:
                setItem(HelmetSlot, item);
                break;
            case Chest:
                setItem(ChestplateSlot, item);
                break;
            case Legs:
                setItem(LeggingsSlot, item);
                break;
            case Feet:
                setItem(BootsSlot, item);
                break;
        }
    }
}

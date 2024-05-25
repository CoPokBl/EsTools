package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsEquipmentSlot;

public interface EsPlayerInventory extends EsInventory {
    EsItemStack getOffHand();
    EsItemStack getMainHand();
    EsItemStack getHelmet();
    EsItemStack getLeggings();
    EsItemStack getChestplate();
    EsItemStack getBoots();
    void setItem(EsEquipmentSlot slot, EsItemStack item);
}

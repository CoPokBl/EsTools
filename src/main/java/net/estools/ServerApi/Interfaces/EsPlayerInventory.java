package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.EsEquipmentSlot;

public interface EsPlayerInventory extends EsInventory {
    EsItemStack getOffHand();
    EsItemStack getMainHand();
    EsItemStack getHelmet();
    EsItemStack getLeggings();
    EsItemStack getChestplate();
    EsItemStack getBoots();
    void setItem(EsEquipmentSlot slot, EsItemStack item);
}

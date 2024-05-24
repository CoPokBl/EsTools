package net.serble.estools.ServerApi.Interfaces;

public interface EsPlayerInventory extends EsInventory {
    EsItemStack getOffHand();
    EsItemStack getMainHand();
    EsItemStack getHelmet();
    EsItemStack getLeggings();
    EsItemStack getChestplate();
    EsItemStack getBoots();
}

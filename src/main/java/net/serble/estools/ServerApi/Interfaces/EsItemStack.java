package net.serble.estools.ServerApi.Interfaces;

public interface EsItemStack {
    String getType();
    void setType(String val);
    int getAmount();
    void setAmount(int amount);
    void addEnchantment(String enchantment, int level);
    void removeEnchantment(String enchantment);
    EsItemMeta getItemMeta();
    void setItemMeta(EsItemMeta meta);
    void setDamage(int val);
    int getDamage();
    EsItemStack clone();
    boolean isSimilar(EsItemStack stack);
    int getMaxStackSize();
    Object getInternalObject();
}

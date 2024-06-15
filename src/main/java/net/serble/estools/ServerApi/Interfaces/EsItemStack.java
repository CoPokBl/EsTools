package net.serble.estools.ServerApi.Interfaces;

import java.util.Map;

public interface EsItemStack {
    String getType();
    void setType(String val);
    int getAmount();
    void setAmount(int amount);
    void addEnchantment(String enchantment, int level);
    void removeEnchantment(String enchantment);
    EsItemMeta getItemMeta();
    String exportItemMeta();
    void importItemMeta(String meta);
    void setItemMeta(EsItemMeta meta);
    void setDamage(int val);
    int getDamage();
    EsItemStack clone();
    boolean isSimilar(EsItemStack stack);
    int getMaxStackSize();
    Object getInternalObject();
    Map<String, Integer> getEnchantments();
}

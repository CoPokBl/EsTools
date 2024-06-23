package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsEnchantment;
import net.serble.estools.ServerApi.EsMaterial;

import java.util.Map;

public interface EsItemStack {
    EsMaterial getType();
    void setType(EsMaterial val);
    int getAmount();
    void setAmount(int amount);
    void addEnchantment(EsEnchantment enchantment, int level);
    void removeEnchantment(EsEnchantment enchantment);
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
    Map<EsEnchantment, Integer> getEnchantments();
}

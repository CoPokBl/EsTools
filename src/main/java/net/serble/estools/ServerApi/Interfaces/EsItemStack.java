package net.serble.estools.ServerApi.Interfaces;

public interface EsItemStack {
    String getType();
    void setType(String val);
    int getAmount();
    void setAmount(int amount);
    void addEnchantment(String enchantment, int level);
    EsItemMeta getItemMeta();
    void setItemMeta(EsItemMeta meta);
}

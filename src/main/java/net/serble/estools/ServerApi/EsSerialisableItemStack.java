package net.serble.estools.ServerApi;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;

import java.util.List;
import java.util.Map;


// TODO: NBT, Potions
public class EsSerialisableItemStack {
    private String material;
    private int amount;
    private String customName;
    private List<String> lore;
    private Map<String, Integer> enchantments;
    private EsItemFlag[] flags;

    /** Public constructor for serialiser */
    public EsSerialisableItemStack() { }

    public static EsSerialisableItemStack generate(EsItemStack stack) {
        EsSerialisableItemStack result = new EsSerialisableItemStack();
        result.setMaterial(stack.getType());
        result.setAmount(stack.getAmount());
        result.setLore(stack.getItemMeta().getLore());
        result.setCustomName(stack.getItemMeta().getDisplayName());
        result.setEnchantments(stack.getEnchantments());
        result.setFlags(stack.getItemMeta().getItemFlags().toArray(new EsItemFlag[0]));
        return result;
    }

    public EsItemStack toItemStack() {
        EsItemStack stack = Main.server.createItemStack(material, amount);
        EsItemMeta meta = stack.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(customName);
        meta.addItemFlags(flags);
        stack.setItemMeta(meta);
        for (Map.Entry<String, Integer> ench : enchantments.entrySet()) {
            stack.addEnchantment(ench.getKey(), ench.getValue());
        }
        return stack;
    }

    public int getAmount() {
        return amount;
    }

    public String getMaterial() {
        return material;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCustomName() {
        return customName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public Map<String, Integer> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(Map<String, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public EsItemFlag[] getFlags() {
        return flags;
    }

    public void setFlags(EsItemFlag[] flags) {
        this.flags = flags;
    }
}

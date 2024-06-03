package net.serble.estools.ServerApi;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPotion;

import java.util.List;
import java.util.Map;


// TODO: NBT
@SuppressWarnings("unused")  // Needs the methods for YAML serialiser
public class EsSerialisableItemStack {
    private String material;
    private int amount;
    private String customName;
    private List<String> lore;
    private Map<String, Integer> enchantments;
    private EsItemFlag[] flags;
    private EsPotionEffect[] potionEffects;
    private EsPotType potType;

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

        if (stack instanceof EsPotion) {
            EsPotion pot = (EsPotion) stack;
            result.setPotionEffects(pot.getEffects());
            result.setPotType(pot.getPotionType());
        }
        return result;
    }

    public EsItemStack toItemStack() {
        EsItemStack stack = potionEffects == null ?
                Main.server.createItemStack(material, amount) :
                Main.server.createPotion(potType);
        EsItemMeta meta = stack.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(customName);
        meta.addItemFlags(flags);
        stack.setItemMeta(meta);
        for (Map.Entry<String, Integer> ench : enchantments.entrySet()) {
            stack.addEnchantment(ench.getKey(), ench.getValue());
        }

        if (potionEffects != null) {
            EsPotion pot = (EsPotion) stack;
            for (EsPotionEffect effect : potionEffects) {
                pot.addEffect(effect);
            }
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

    public EsPotionEffect[] getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(EsPotionEffect[] potionEffects) {
        this.potionEffects = potionEffects;
    }

    public EsPotType getPotType() {
        return potType;
    }

    public void setPotType(EsPotType potType) {
        this.potType = potType;
    }
}

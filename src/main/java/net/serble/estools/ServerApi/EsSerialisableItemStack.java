package net.serble.estools.ServerApi;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPotion;

import java.util.Map;


// TODO: NBT
@SuppressWarnings("unused")  // Needs the methods for YAML serialiser
public class EsSerialisableItemStack {
    private EsMaterial material;
    private int amount;
    private String itemMeta;
    private Map<EsEnchantment, Integer> enchantments;
    private boolean isPotion;
    private EsPotType potType;

    /** Public constructor for serialiser */
    public EsSerialisableItemStack() { }

    public static EsSerialisableItemStack generate(EsItemStack stack) {
        EsSerialisableItemStack result = new EsSerialisableItemStack();
        result.setMaterial(stack.getType());
        result.setAmount(stack.getAmount());
        result.setEnchantments(stack.getEnchantments());
        result.setItemMeta(stack.exportItemMeta());

        if (stack instanceof EsPotion) {
            EsPotion pot = (EsPotion) stack;
            result.setPotType(pot.getPotionType());
            result.setIsPotion(true);
        } else {
            result.setIsPotion(false);
        }

        return result;
    }

    public EsItemStack toItemStack() {
        EsItemStack stack = isPotion ?
                Main.server.createPotion(potType) :
                Main.server.createItemStack(material, 1);

        stack.setAmount(amount);
        stack.importItemMeta(itemMeta);
        return stack;
    }

    public int getAmount() {
        return amount;
    }

    public EsMaterial getMaterial() {
        return material;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMaterial(EsMaterial material) {
        this.material = material;
    }

    public Map<EsEnchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(Map<EsEnchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public String getItemMeta() {
        return itemMeta;
    }

    public void setItemMeta(String itemMeta) {
        this.itemMeta = itemMeta;
    }

    public boolean getIsPotion() {
        return isPotion;
    }

    public void setIsPotion(boolean isPotion) {
        this.isPotion = isPotion;
    }

    public EsPotType getPotType() {
        return potType;
    }

    public void setPotType(EsPotType potType) {
        this.potType = potType;
    }
}

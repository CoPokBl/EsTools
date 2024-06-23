package net.serble.estools.Implementation;

import net.serble.estools.ServerApi.EsEnchantment;
import net.serble.estools.ServerApi.EsMaterial;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;

import java.util.HashMap;
import java.util.Map;

public class TestItemStack implements EsItemStack {
    private EsMaterial type;
    private int amount;
    private int damage = 0;
    private final Map<EsEnchantment, Integer> enchantments = new HashMap<>();
    private TestItemMeta meta = new TestItemMeta();

    public TestItemStack(EsMaterial type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public EsMaterial getType() {
        return type;
    }

    @Override
    public void setType(EsMaterial val) {
        type = val;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void addEnchantment(EsEnchantment enchantment, int level) {
        enchantments.put(enchantment, level);
    }

    @Override
    public void removeEnchantment(EsEnchantment enchantment) {
        enchantments.remove(enchantment);
    }

    @Override
    public EsItemMeta getItemMeta() {
        return meta.clone();
    }

    @Override
    public String exportItemMeta() {
        return "";
    }

    @Override
    public void importItemMeta(String meta) {

    }

    @Override
    public void setItemMeta(EsItemMeta meta) {
        this.meta = (TestItemMeta) meta;
    }

    @Override
    public void setDamage(int val) {
        damage = val;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")  // Not implemented yet
    @Override
    public EsItemStack clone() {
        return this;  // Who cares
    }

    @Override
    public boolean isSimilar(EsItemStack stack) {
        return type.equals(stack.getType());
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public Object getInternalObject() {
        return null;
    }

    @Override
    public Map<EsEnchantment, Integer> getEnchantments() {
        return enchantments;
    }
}

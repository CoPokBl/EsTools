package net.estools.ServerApi.Implementations.Minestom;

import net.estools.NotImplementedException;
import net.estools.ServerApi.EsEnchantment;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Implementations.Minestom.Helpers.MinestomHelper;
import net.estools.ServerApi.Interfaces.EsItemMeta;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MinestomItemStack implements EsItemStack {
    private ItemStack stack;

    public MinestomItemStack(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack internal() {
        return stack;
    }

    @Override
    public EsMaterial getType() {
        return EsMaterial.createUnchecked(stack.material().registry().namespace().asString());
    }

    @Override
    public void setType(EsMaterial val) {
        stack = stack.withMaterial(Objects.requireNonNull(Material.fromNamespaceId(val.getKey())));
    }

    @Override
    public int getAmount() {
        return stack.amount();
    }

    @Override
    public void setAmount(int amount) {
        stack = stack.withAmount(amount);
    }

    @Override
    public void addEnchantment(EsEnchantment enchantment, int level) {
        throw new NotImplementedException();
    }

    @Override
    public void removeEnchantment(EsEnchantment enchantment) {
        throw new NotImplementedException();
    }

    @Override
    public EsItemMeta getItemMeta() {
        throw new NotImplementedException();
    }

    @Override
    public String exportItemMeta() {
        throw new NotImplementedException();
    }

    @Override
    public void importItemMeta(String meta) {
        throw new NotImplementedException();
    }

    @Override
    public void setItemMeta(EsItemMeta meta) {
        throw new NotImplementedException();
    }

    @Override
    public void setDamage(int val) {
        stack = stack.with(ItemComponent.DAMAGE, val);
    }

    @Override
    public int getDamage() {
        Integer d = stack.get(ItemComponent.DAMAGE);
        return d == null ? 0 : d;
    }

    @Override
    public EsItemStack clone() {
        return MinestomHelper.toItem(stack.withAmount(stack.amount()));
    }

    @Override
    public boolean isSimilar(EsItemStack stack) {
        return this.stack.isSimilar(((MinestomItemStack) stack).stack);
    }

    @Override
    public int getMaxStackSize() {
        return stack.maxStackSize();
    }

    @Override
    public Object getInternalObject() {
        return stack;
    }

    @Override
    public Map<EsEnchantment, Integer> getEnchantments() {
        return new HashMap<>();
    }
}

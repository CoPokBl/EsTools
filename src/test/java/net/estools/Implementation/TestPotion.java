package net.estools.Implementation;

import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.EsPotType;
import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.Interfaces.EsPotion;

import java.util.ArrayList;
import java.util.List;

public class TestPotion extends TestItemStack implements EsPotion {
    private final EsPotType potType;
    private final List<EsPotionEffect> effects;

    public TestPotion(int amount, EsPotType potType, EsPotionEffect effect) {
        super(EsMaterial.fromKey("POTION"), amount);
        this.potType = potType;
        effects = new ArrayList<>();
        effects.add(effect);
    }

    @Override
    public EsPotionEffect[] getEffects() {
        return effects.toArray(new EsPotionEffect[0]);
    }

    @Override
    public EsPotType getPotionType() {
        return potType;
    }

    @Override
    public void addEffect(EsPotionEffect effect) {
        effects.add(effect);
    }
}

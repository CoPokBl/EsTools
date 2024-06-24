package net.estools.Implementation;

import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.EsPotionEffectType;
import net.estools.ServerApi.Interfaces.EsLivingEntity;
import net.estools.ServerApi.Interfaces.EsWorld;

import java.util.ArrayList;
import java.util.List;

public class TestLivingEntity extends TestEntity implements EsLivingEntity {
    private double maxHealth = 20;
    private double health = 20;
    private final List<EsPotionEffect> effects = new ArrayList<>();

    public TestLivingEntity(EsWorld world) {
        super(world);
    }

    // IMPLEMENTATION METHODS

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(double val) {
        maxHealth = val;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double val) {
        health = val;
    }

    @Override
    public void addPotionEffect(EsPotionEffect effect) {
        effects.add(effect);
    }

    @Override
    public void removePotionEffect(EsPotionEffectType effect) {
        effects.removeIf(e -> e.getType() == effect);
    }

    @Override
    public List<EsPotionEffect> getActivePotionEffects() {
        return effects;
    }

    @Override
    public EsWorld getWorld() {
        return null;
    }
}

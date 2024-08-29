package net.estools.ServerApi.Implementations.Minestom;

import net.estools.NotImplementedException;
import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.EsPotionEffectType;
import net.estools.ServerApi.Implementations.Minestom.Helpers.MinestomHelper;
import net.estools.ServerApi.Interfaces.EsLivingEntity;
import net.estools.ServerApi.Interfaces.EsWorld;
import net.minestom.server.entity.LivingEntity;

import java.util.List;

public class MinestomLivingEntity extends MinestomEntity implements EsLivingEntity {
    private final LivingEntity entity;

    public MinestomLivingEntity(LivingEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public double getMaxHealth() {
        return 0;  // There is no max health in Minestom
    }

    @Override
    public void setMaxHealth(double val) {
        // There is no max health in Minestom
    }

    @Override
    public double getHealth() {
        return entity.getHealth();
    }

    @Override
    public void setHealth(double val) {
        entity.setHealth((float) val);
    }

    @Override
    public void addPotionEffect(EsPotionEffect effect) {
        throw new NotImplementedException();
    }

    @Override
    public void removePotionEffect(EsPotionEffectType effect) {
        throw new NotImplementedException();
    }

    @Override
    public List<EsPotionEffect> getActivePotionEffects() {
        throw new NotImplementedException();
    }

    @Override
    public EsWorld getWorld() {
        return MinestomHelper.toWorld(entity.getInstance());
    }
}

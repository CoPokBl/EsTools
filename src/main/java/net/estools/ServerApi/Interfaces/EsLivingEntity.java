package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.EsPotionEffectType;

import java.util.List;

public interface EsLivingEntity extends EsEntity {
    double getMaxHealth();
    void setMaxHealth(double val);
    double getHealth();
    void setHealth(double val);
    void sendMessage(String... msg);
    void addPotionEffect(EsPotionEffect effect);
    void removePotionEffect(EsPotionEffectType effect);
    List<EsPotionEffect> getActivePotionEffects();
    EsWorld getWorld();
}

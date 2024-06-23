package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsPotionEffect;
import net.serble.estools.ServerApi.EsPotionEffectType;

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

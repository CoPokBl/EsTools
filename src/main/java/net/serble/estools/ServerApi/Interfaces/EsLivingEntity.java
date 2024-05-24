package net.serble.estools.ServerApi.Interfaces;

import java.util.List;

public interface EsLivingEntity extends EsEntity {
    double getMaxHealth();
    void setMaxHealth(double val);
    double getHealth();
    void setHealth(double val);
    void sendMessage(String... msg);
    void addPotionEffect(String effect, int duration, int amplifier);
    void removePotionEffect(String effect);
    List<String> getActivePotionEffects();
    EsWorld getWorld();
}

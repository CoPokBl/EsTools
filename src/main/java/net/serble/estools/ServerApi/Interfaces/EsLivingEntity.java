package net.serble.estools.ServerApi.Interfaces;

public interface EsLivingEntity extends EsEntity {
    double getMaxHealth();
    void setMaxHealth(double val);
    double getHealth();
    void setHealth(double val);
    void sendMessage(String... msg);
}

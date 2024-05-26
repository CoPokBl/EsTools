package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsGameMode;
import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.EsSoundCategory;

@SuppressWarnings("unused")
public interface EsPlayer extends EsLivingEntity {
    int getFoodLevel();
    void setFoodLevel(int val);
    double getSaturation();
    void setSaturation(double val);
    EsItemStack getMainHand();
    void setMainHand(EsItemStack item);
    void openInventory(EsInventory inv);
    void closeInventory();
    EsPlayerInventory getInventory();
    void setFlySpeed(float val);
    void setWalkSpeed(float val);
    void setGameMode(EsGameMode mode);
    EsGameMode getGameMode();
    EsBlock getTargetBlock();
    boolean getAllowFlight();
    void setAllowFlight(boolean val);
    boolean isFlying();
    void playSound(String sound, EsSoundCategory category, EsLocation loc, int volume, int pitch);
    void updateInventory();
}

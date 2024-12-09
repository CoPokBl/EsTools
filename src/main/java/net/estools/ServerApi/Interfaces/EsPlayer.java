package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.*;

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
    void playSound(EsSound sound, EsSoundCategory category, EsLocation loc, int volume, int pitch);
    void updateInventory();
    boolean hasPermission(String node);
    boolean isPermissionSet(String node);
    void sendMessage(String... args);
    EsInventory getEnderChest();
}

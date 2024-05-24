package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsGameMode;

public interface EsPlayer extends EsLivingEntity {
    int getFoodLevel();
    void setFoodLevel(int val);
    EsItemStack getMainHand();
    void setMainHand(EsItemStack item);
    void openInventory(EsInventory inv);
    void closeInventory();
    EsInventory getInventory();
    void setFlySpeed(float val);
    void setWalkSpeed(float val);
    EsWorld getWorld();
    void setGameMode(EsGameMode mode);
    EsGameMode getGameMode();
    EsBlock getTargetBlock();  // TODO: Implement own Block class
}

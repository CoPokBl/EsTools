package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.SemanticVersion;
import net.serble.estools.ServerApi.EsPotType;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public interface EsServerSoftware {
    EsPlayer getPlayer(String name);
    EsEntity getEntity(UUID uuid);
    SemanticVersion getVersion();
    Collection<? extends EsPlayer> getOnlinePlayers();
    EsItemStack createItemStack(String material, int amount);
    EsItemStack createPotion(EsPotType potType, String effect, int duration, int amp, int amount);
    EsInventory createInventory(EsPlayer owner, int size, String title);
    String[] getPotionEffectTypes();
    String[] getEnchantments();
    String[] getSounds();
    boolean doesEnchantmentExist(String name);
    File getDataFolder();
    void dispatchCommand(EsCommandSender sender, String cmd);
    EsCommandSender getConsoleSender();
    SemanticVersion getPluginVersion();
    String getPluginName();
    void runTaskLater(Runnable task, long ticks);
    void runTask(Runnable task);
    EsLogger getLogger();
    void startEvents();
}

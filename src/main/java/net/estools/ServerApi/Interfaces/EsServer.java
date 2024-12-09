package net.estools.ServerApi.Interfaces;

import net.estools.EsToolsTabCompleter;
import net.estools.SemanticVersion;
import net.estools.ServerApi.*;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unused")
public interface EsServer {
    EsPlayer getPlayer(String name);
    EsPlayer getPlayer(UUID uuid);
    EsOfflinePlayer getOfflinePlayer(String name);
    EsOfflinePlayer getOfflinePlayer(UUID uuid);
    EsEntity getEntity(UUID uuid);
    SemanticVersion getVersion();
    Collection<? extends EsPlayer> getOnlinePlayers();
    EsItemStack createItemStack(EsMaterial material, int amount);
    EsPotion createPotion(EsPotType potType, EsPotionEffect effect, int amount);
    EsPotion createPotion(EsPotType potType);
    EsInventory createInventory(EsPlayer owner, int size, String title);
    EsInventory createInventory(EsPlayer owner, EsInventoryType type);
    boolean inventoryTypeExists(EsInventoryType type);
    Set<EsPotionEffectType> getPotionEffectTypes();
    Set<EsPotionEffectType> getOldPotionTypes();
    Set<EsEnchantment> getEnchantments();
    Set<EsSound> getSounds();
    void initialise();

    /**
     * @param onlyItems
     * Whether to only include materials that are classified as items
     * */
    Set<EsMaterial> getMaterials(boolean onlyItems);

    File getDataFolder();
    void dispatchCommand(EsCommandSender sender, String cmd);
    EsCommandSender getConsoleSender();
    SemanticVersion getPluginVersion();
    String getPluginName();
    int runTaskLater(Runnable task, long ticks);
    void runTask(Runnable task);
    void cancelTask(int id);
    EsLogger getLogger();
    void startEvents();
    void registerCommand(String cmd, EsToolsTabCompleter tab);
    void setTabCompleter(String cmd, EsToolsTabCompleter tab);
    void setCommandPermission(String cmd, String perm);
    void broadcast(String msg, String perm);
    void broadcast(String msg);
    EsWorld getWorld(String name);

    /** Get a list of classes that SnakeYAML needs to accept for proper config serialisation */
    String[] getRelevantInternalTypes();
}

package net.estools.ServerApi.Implementations.Minestom;

import net.estools.EsToolsTabCompleter;
import net.estools.SemanticVersion;
import net.estools.ServerApi.*;
import net.estools.ServerApi.Interfaces.*;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MinestomServer implements EsServer {

    @Override
    public EsPlayer getPlayer(String name) {
        return MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(name);
    }

    @Override
    public EsPlayer getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public EsOfflinePlayer getOfflinePlayer(String name) {
        return null;
    }

    @Override
    public EsOfflinePlayer getOfflinePlayer(UUID uuid) {
        return null;
    }

    @Override
    public EsEntity getEntity(UUID uuid) {
        return null;
    }

    @Override
    public SemanticVersion getVersion() {
        return null;
    }

    @Override
    public Collection<? extends EsPlayer> getOnlinePlayers() {
        return List.of();
    }

    @Override
    public EsItemStack createItemStack(EsMaterial material, int amount) {
        return null;
    }

    @Override
    public EsPotion createPotion(EsPotType potType, EsPotionEffect effect, int amount) {
        return null;
    }

    @Override
    public EsPotion createPotion(EsPotType potType) {
        return null;
    }

    @Override
    public EsInventory createInventory(EsPlayer owner, int size, String title) {
        return null;
    }

    @Override
    public Set<EsPotionEffectType> getPotionEffectTypes() {
        return Set.of();
    }

    @Override
    public Set<EsPotionEffectType> getOldPotionTypes() {
        return Set.of();
    }

    @Override
    public Set<EsEnchantment> getEnchantments() {
        return Set.of();
    }

    @Override
    public Set<EsSound> getSounds() {
        return Set.of();
    }

    @Override
    public void initialise() {

    }

    @Override
    public Set<EsMaterial> getMaterials(boolean onlyItems) {
        return Set.of();
    }

    @Override
    public File getDataFolder() {
        return null;
    }

    @Override
    public void dispatchCommand(EsCommandSender sender, String cmd) {

    }

    @Override
    public EsCommandSender getConsoleSender() {
        return null;
    }

    @Override
    public SemanticVersion getPluginVersion() {
        return null;
    }

    @Override
    public String getPluginName() {
        return "";
    }

    @Override
    public int runTaskLater(Runnable task, long ticks) {
        return 0;
    }

    @Override
    public void runTask(Runnable task) {

    }

    @Override
    public void cancelTask(int id) {

    }

    @Override
    public EsLogger getLogger() {
        return null;
    }

    @Override
    public void startEvents() {

    }

    @Override
    public void registerCommand(String cmd, EsToolsTabCompleter tab) {

    }

    @Override
    public void setTabCompleter(String cmd, EsToolsTabCompleter tab) {

    }

    @Override
    public void setCommandPermission(String cmd, String perm) {

    }

    @Override
    public void broadcast(String msg, String perm) {

    }

    @Override
    public void broadcast(String msg) {

    }

    @Override
    public EsWorld getWorld(String name) {
        return null;
    }

    @Override
    public String[] getRelevantInternalTypes() {
        return new String[0];
    }
}

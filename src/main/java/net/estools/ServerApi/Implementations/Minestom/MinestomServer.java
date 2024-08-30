package net.estools.ServerApi.Implementations.Minestom;

import net.estools.EsToolsTabCompleter;
import net.estools.NotImplementedException;
import net.estools.SemanticVersion;
import net.estools.ServerApi.*;
import net.estools.ServerApi.Implementations.Minestom.Helpers.MinestomHelper;
import net.estools.ServerApi.Interfaces.*;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.enchant.Enchantment;

import java.io.File;
import java.util.*;

public class MinestomServer implements EsServer {

    @Override
    public EsPlayer getPlayer(String name) {
        return MinestomHelper.getPlayer(MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(name));
    }

    @Override
    public EsPlayer getPlayer(UUID uuid) {
        return MinestomHelper.getPlayer(MinecraftServer.getConnectionManager().getOnlinePlayerByUuid(uuid));
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
        for (Instance instance : MinecraftServer.getInstanceManager().getInstances()) {
            for (Entity entity : instance.getEntities()) {
                if (entity.getUuid().equals(uuid)) {
                    return MinestomHelper.toEntity(entity);
                }
            }
        }
        return null;
    }

    @Override
    public SemanticVersion getVersion() {
        return new SemanticVersion(MinecraftServer.VERSION_NAME);
    }

    @Override
    public Collection<? extends EsPlayer> getOnlinePlayers() {
        List<EsPlayer> players = new ArrayList<>();
        for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            players.add(MinestomHelper.getPlayer(player));
        }
        return players;
    }

    @Override
    public EsItemStack createItemStack(EsMaterial material, int amount) {
        return MinestomHelper.toItem(null);
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
        return new HashSet<>();
    }

    @Override
    public Set<EsPotionEffectType> getOldPotionTypes() {
        throw new UnsupportedOperationException("This platform does not support old Minecraft versions");
    }

    @Override
    public Set<EsEnchantment> getEnchantments() {
        Set<EsEnchantment> enchantments = new HashSet<>();
        for (Enchantment ench : MinecraftServer.getEnchantmentRegistry().values()) {
            enchantments.add(EsEnchantment.createUnchecked(ench.registry().namespace().asString()));
        }
        return enchantments;
    }

    @Override
    public Set<EsSound> getSounds() {
        throw new NotImplementedException();
    }

    @Override
    public void initialise() {
        // Do nothing
    }

    @Override
    public Set<EsMaterial> getMaterials(boolean onlyItems) {
        return new HashSet<>();
    }

    @Override
    public File getDataFolder() {
        return new File("data");
    }

    @Override
    public void dispatchCommand(EsCommandSender sender, String cmd) {
        MinecraftServer.getCommandManager().execute(MinestomHelper.getUnderlyingSender(sender), cmd);
    }

    @Override
    public EsCommandSender getConsoleSender() {
        return new MinestomConsoleSender();
    }

    @Override
    public SemanticVersion getPluginVersion() {
        throw new NotImplementedException();
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

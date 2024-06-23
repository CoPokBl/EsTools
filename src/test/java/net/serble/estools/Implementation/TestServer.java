package net.serble.estools.Implementation;

import net.serble.estools.EsToolsTabCompleter;
import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.SemanticVersion;
import net.serble.estools.ServerApi.*;
import net.serble.estools.ServerApi.Interfaces.*;

import java.io.File;
import java.util.*;

public class TestServer implements EsServer {
    private final TestLogger logger;

    public TestServer() {
        logger = new TestLogger();
    }

    @Override
    public EsPlayer getPlayer(String name) {
        if (Objects.equals(EsToolsUnitTest.player.getName(), name)) {
            return EsToolsUnitTest.player;
        }

        return null;
    }

    @Override
    public EsEntity getEntity(UUID uuid) {
        if (Objects.equals(EsToolsUnitTest.player.getUniqueId(), uuid)) {
            return EsToolsUnitTest.player;
        }

        return null;
    }

    @Override
    public SemanticVersion getVersion() {
        return EsToolsUnitTest.minecraftVersion;
    }

    @Override
    public Collection<? extends EsPlayer> getOnlinePlayers() {
        return Collections.singletonList(EsToolsUnitTest.player);
    }

    @Override
    public EsItemStack createItemStack(EsMaterial material, int amount) {
        return new TestItemStack(material, amount);
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
        return Collections.emptySet();
    }

    @Override
    public Set<EsPotionEffectType> getOldPotionTypes() {
        return Collections.emptySet();
    }

    @Override
    public Set<EsEnchantment> getEnchantments() {
        return Collections.emptySet();
    }

    @Override
    public Set<EsSound> getSounds() {
        return Collections.emptySet();
    }

    @Override
    public void initialise() {

    }

    @Override
    public Set<EsMaterial> getMaterials(boolean onlyItems) {
        return Collections.emptySet();
    }

    @Override
    public File getDataFolder() {  // Return the system temp folder + EsTools
        return new File(System.getProperty("java.io.tmpdir"), "EsTools");
    }

    @Override
    public void dispatchCommand(EsCommandSender sender, String cmd) {

    }

    @Override
    public EsCommandSender getConsoleSender() {
        return EsToolsUnitTest.player;
    }

    @Override
    public SemanticVersion getPluginVersion() {
        return EsToolsUnitTest.pluginVersion;
    }

    @Override
    public String getPluginName() {
        return "EsTools";
    }

    @Override
    public int runTaskLater(Runnable task, long ticks) {
        task.run();
        return 0;
    }

    @Override
    public void runTask(Runnable task) {
        task.run();
    }

    @Override
    public void cancelTask(int id) {
        // No lol
    }

    @Override
    public EsLogger getLogger() {
        return logger;
    }

    @Override
    public void startEvents() {
        // We don't need to do this
    }

    @Override
    public void registerCommand(String cmd, EsToolsTabCompleter tab) {
        // We don't need to do this
    }

    @Override
    public void setTabCompleter(String cmd, EsToolsTabCompleter tab) {
        // We don't need to do this
    }

    @Override
    public void setCommandPermission(String cmd, String perm) {
        // We don't need to do this
    }

    @Override
    public void broadcast(String msg, String perm) {
        if (EsToolsUnitTest.player.hasPermission(perm)) {
            EsToolsUnitTest.player.sendMessage(msg);
        }
        logger.info(msg);
    }

    @Override
    public void broadcast(String msg) {
        logger.info(msg);
        EsToolsUnitTest.player.sendMessage(msg);
    }

    @Override
    public EsWorld getWorld(String name) {
        if (EsToolsUnitTest.world.getName().equals(name)) {
            return EsToolsUnitTest.world;
        }

        return null;
    }

    @Override
    public String[] getRelevantInternalTypes() {
        return new String[0];
    }
}

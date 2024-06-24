package net.estools.Implementation;

import net.estools.EsToolsTabCompleter;
import net.estools.EsToolsUnitTest;
import net.estools.SemanticVersion;
import net.estools.ServerApi.*;
import net.estools.ServerApi.Interfaces.*;

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

        return (EsPlayer) EsToolsUnitTest.world.getEntities()
                .stream()
                .filter(e -> e instanceof TestPlayer && Objects.equals(e.getName(), name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public EsEntity getEntity(UUID uuid) {
        if (Objects.equals(EsToolsUnitTest.player.getUniqueId(), uuid)) {
            return EsToolsUnitTest.player;
        }

        return EsToolsUnitTest.world.getEntities()
                .stream()
                .filter(e -> Objects.equals(e.getUniqueId(), uuid))
                .findFirst()
                .orElse(null);
    }

    @Override
    public SemanticVersion getVersion() {
        return EsToolsUnitTest.minecraftVersion;
    }

    @Override
    public Collection<? extends EsPlayer> getOnlinePlayers() {
        return EsToolsUnitTest.players;
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
        Set<EsEnchantment> enchs = new HashSet<>();
        enchs.add(EsEnchantment.createUnchecked("sharpness"));
        return enchs;
    }

    @Override
    public Set<EsSound> getSounds() {
        HashSet<EsSound> sounds = new HashSet<>();
        sounds.add(EsSound.createUnchecked("music_disc.cat"));
        return sounds;
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
        EsToolsUnitTest.waitingTasks.add(task);  // Run it later
        return EsToolsUnitTest.waitingTasks.size() - 1;  // Maybe I'll use this later
    }

    @Override
    public void runTask(Runnable task) {
        task.run();
    }

    @Override
    public void cancelTask(int id) {
        // Not needed
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

package net.estools.Implementation;

import net.estools.EsToolsTabCompleter;
import net.estools.EsToolsUnitTest;
import net.estools.SemanticVersion;
import net.estools.ServerApi.*;
import net.estools.ServerApi.EsCommand.EsCommandManager;
import net.estools.ServerApi.Interfaces.*;

import java.io.File;
import java.time.Instant;
import java.util.*;

public class TestServer implements EsServer {
    private final TestLogger logger;
    private static final Set<EsMaterial> materials;
    private static final Set<EsEnchantment> enchantments;
    private static final Set<EsSound> sounds;
    private static final Set<EsPotionEffectType> effects;
    private static final EsCommandManager commandManager;

    static {
        materials = new HashSet<>();
        materials.add(EsMaterial.createUnchecked("dirt"));
        materials.add(EsMaterial.createUnchecked("stone"));
        materials.add(EsMaterial.createUnchecked("NETHERITE_AXE"));
        materials.add(EsMaterial.createUnchecked("DIAMOND_AXE"));
        materials.add(EsMaterial.createUnchecked("NETHERITE_PICKAXE"));
        materials.add(EsMaterial.createUnchecked("DIAMOND_PICKAXE"));
        materials.add(EsMaterial.createUnchecked("NETHERITE_HOE"));
        materials.add(EsMaterial.createUnchecked("DIAMOND_HOE"));
        materials.add(EsMaterial.createUnchecked("NETHERITE_SHOVEL"));
        materials.add(EsMaterial.createUnchecked("DIAMOND_SHOVEL"));
        materials.add(EsMaterial.createUnchecked("NETHERITE_SWORD"));
        materials.add(EsMaterial.createUnchecked("DIAMOND_SWORD"));
        materials.add(EsMaterial.createUnchecked("SALMON"));
        materials.add(EsMaterial.createUnchecked("POTION"));

        enchantments = new HashSet<>();
        enchantments.add(EsEnchantment.createUnchecked("sharpness"));
        enchantments.add(EsEnchantment.createUnchecked("knockback"));

        sounds = new HashSet<>();
        sounds.add(EsSound.createUnchecked("music_disc.cat"));

        effects = new HashSet<>();
        effects.add(EsPotionEffectType.createUnchecked("speed"));

        commandManager = new TestCommandManager();
    }

    public TestServer() {
        logger = new TestLogger();
    }

    @Override
    public EsCommandManager getCommandManager() {
        return commandManager;
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

    public EsPlayer getPlayer(UUID uuid) {
        if (Objects.equals(EsToolsUnitTest.player.getUniqueId(), uuid)) {
            return EsToolsUnitTest.player;
        }

        return (EsPlayer) EsToolsUnitTest.world.getEntities()
                .stream()
                .filter(e -> e instanceof TestPlayer && Objects.equals(e.getUniqueId(), uuid))
                .findFirst()
                .orElse(null);
    }

    private EsOfflinePlayer getOfflinePlayer(EsPlayer p) {
        EsOfflinePlayer op = new EsOfflinePlayer();
        op.setName(p.getName());
        op.setUuid(p.getUniqueId());
        op.setPlayedBefore(true);
        op.setLocation(p.getLocation());
        op.setRespawnLocation(null);
        op.setBanned(false);
        op.setFirstPlayed(Instant.now().toEpochMilli());
        op.setLastPlayed(Instant.now().toEpochMilli());
        op.setLastDeathLocation(null);
        return op;
    }

    @Override
    public EsOfflinePlayer getOfflinePlayer(String name) {
        EsPlayer p = getPlayer(name);
        return getOfflinePlayer(p);
    }

    @Override
    public EsOfflinePlayer getOfflinePlayer(UUID uuid) {
        EsPlayer p = getPlayer(uuid);
        return getOfflinePlayer(p);
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
        return new TestPotion(amount, potType, effect);
    }

    @Override
    public EsPotion createPotion(EsPotType potType) {
        return null;
    }

    @Override
    public EsInventory createInventory(EsPlayer owner, int size, String title) {
        return new TestInventory(size, title);
    }

    @Override
    public Set<EsPotionEffectType> getPotionEffectTypes() {
        return effects;
    }

    @Override
    public Set<EsPotionEffectType> getOldPotionTypes() {
        return Collections.emptySet();
    }

    @Override
    public Set<EsEnchantment> getEnchantments() {
        return enchantments;
    }

    @Override
    public Set<EsSound> getSounds() {
        return sounds;
    }

    @Override
    public void initialise() {

    }

    @Override
    public Set<EsMaterial> getMaterials(boolean onlyItems) {
        return materials;
    }

    @Override
    public File getDataFolder() {  // Return the system temp folder + EsTools
        return new File(System.getProperty("java.io.tmpdir"), "EsTools");
    }

    @Override
    public void dispatchCommand(EsCommandSender sender, String cmd) {
        EsToolsUnitTest.pendingCommands.put(sender, cmd);
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
        EsToolsUnitTest.waitingTasks.add(task);  // Run it later
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

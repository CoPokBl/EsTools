package net.estools.ServerApi.Implementations.Folia;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.estools.Entrypoints.EsToolsBukkit;
import net.estools.EsToolsCommand;
import net.estools.EsToolsTabCompleter;
import net.estools.Main;
import net.estools.SemanticVersion;
import net.estools.ServerApi.*;
import net.estools.ServerApi.Implementations.Bukkit.BukkitTabCompleteGenerator;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitEffectHelper;
import net.estools.ServerApi.Interfaces.*;
import org.bukkit.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class FoliaServer implements EsServer {
    private final JavaPlugin plugin;
    private final FoliaEventsListener listener;
    private final Map<Integer, ScheduledTask> tasks = new HashMap<>();
    private static TaskChainFactory taskChainFactory;
    private static final Set<EsMaterial> materials = new HashSet<>();
    private static final Set<EsMaterial> itemMaterials = new HashSet<>();
    private static final Set<EsSound> sounds = new HashSet<>();

    public FoliaServer(Object pluginObj) {
        plugin = (JavaPlugin) pluginObj;
        taskChainFactory = BukkitTaskChainFactory.create(plugin);
        listener = new FoliaEventsListener();
    }

    @Override
    public void initialise() {
        for (Material mat : Material.values()) {
            EsMaterial esMat = EsMaterial.createUnchecked(mat.getKey().getKey().toLowerCase());
            if (mat.isItem()) {
                itemMaterials.add(esMat);
            }

            materials.add(esMat);
        }

        for (Sound sound : Registry.SOUNDS) {
            EsSound esSound = EsSound.createUnchecked(sound.getKey().getKey());
            sounds.add(esSound);
        }
    }

    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }

    @Override
    public EsPlayer getPlayer(String name) {
        Player p = Bukkit.getPlayer(name);
        if (p == null) {
            return null;
        }
        return new FoliaPlayer(p);
    }

    @Override
    public EsPlayer getPlayer(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        if (p == null) {
            return null;
        }
        return new FoliaPlayer(p);
    }

    @SuppressWarnings("deprecation")  // Did I stutter?
    @Override
    public EsOfflinePlayer getOfflinePlayer(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        return FoliaHelper.fromBukkitOfflinePlayer(player);
    }

    @Override
    public EsOfflinePlayer getOfflinePlayer(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return FoliaHelper.fromBukkitOfflinePlayer(player);
    }

    @Override
    public EsEntity getEntity(UUID uuid) {
        if (Main.minecraftVersion.getMinor() > 11) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) {
                return null;
            }
            return FoliaHelper.fromBukkitEntity(entity);
        }

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId() == uuid) {
                    return new FoliaEntity(entity);
                }
            }
        }

        return null;
    }

    @Override
    public SemanticVersion getVersion() {  // Parse the minecraft version from the Bukkit version string
        String versionS = Bukkit.getVersion();
        int minor = 0;
        int patch = 0;

        if (versionS.contains("(MC: ")) {
            int posOfMC = versionS.indexOf("(MC: ") + 5;
            versionS = versionS.substring(posOfMC, versionS.indexOf(')', posOfMC));
        } else {
            Main.logger.warning("Could not detect version from: " + versionS);
            throw new RuntimeException("Could not detect version");
        }

        for (int i = 0; i < 99; i++) {
            if (versionS.contains("1." + i)) {
                minor = i;
            }
        }

        for (int i = 0; i < 99; i++) {
            if (versionS.contains("1." + minor + '.' + i)) {
                patch = i;
            }
        }

        return new SemanticVersion(1, minor, patch);
    }

    @Override
    public Collection<? extends EsPlayer> getOnlinePlayers() {
        try {
            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                List<EsPlayer> players = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    players.add(new FoliaPlayer(p));
                }
                return players;
            }
            else {
                Player[] players = (Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null, new Object[0]);
                List<EsPlayer> users = new ArrayList<>();
                for (Player p : players) {
                    users.add(new FoliaPlayer(p));
                }
                return users;
            }

        } catch (Exception e) {
            Bukkit.getLogger().severe(e.toString());
            return new ArrayList<>();
        }
    }

    @Override
    public EsItemStack createItemStack(EsMaterial material, int amount) {
        return new FoliaItemStack(material, amount);
    }

    @Override
    public EsPotion createPotion(EsPotType potType, EsPotionEffect effect, int amount) {
        String type = potType == EsPotType.drink ?
                "POTION" :
                potType.toString().toUpperCase() + "_POTION";
        ItemStack pot = new ItemStack(Material.valueOf(type), amount);

        PotionMeta meta = (PotionMeta) pot.getItemMeta();
        assert meta != null;
        meta.addCustomEffect(FoliaHelper.toBukkitPotionEffect(effect), true);
        pot.setItemMeta(meta);

        return new FoliaPotion(pot);
    }

    @Override
    public EsPotion createPotion(EsPotType potType) {
        String type = potType == EsPotType.drink ?
                "POTION" :
                potType.toString().toUpperCase() + "_POTION";

        ItemStack pot = new ItemStack(Material.valueOf(type), 1);

        return new FoliaPotion(pot);
    }

    @Override
    public EsInventory createInventory(EsPlayer owner, int size, String title) {
        InventoryHolder holder = owner == null ? null : ((FoliaPlayer) owner).getBukkitPlayer();
        return new FoliaInventory(Bukkit.createInventory(holder, size, title));
    }

    @Override
    public Set<EsPotionEffectType> getPotionEffectTypes() {
        return BukkitEffectHelper.getEffectList();
    }

    @Override
    public Set<EsPotionEffectType> getOldPotionTypes() {
        return BukkitEffectHelper.getPotionList();
    }

    @Override
    public Set<EsEnchantment> getEnchantments() {
        return FoliaEnchantmentHelper.getEnchantmentList();
    }

    @Override
    public Set<EsSound> getSounds() {
        return sounds;
    }

    @Override
    public Set<EsMaterial> getMaterials(boolean onlyItems) {
        return onlyItems ? itemMaterials : materials;
    }

    @Override
    public File getDataFolder() {
        return plugin.getDataFolder();
    }

    @Override
    public void dispatchCommand(EsCommandSender sender, String cmd) {
//        CompletableFuture<Map.Entry<Player, String>> completeTask = new CompletableFuture<Map.Entry<Player, String>>()
//                .whenComplete((c, exception) -> {
//                    Main.logger.info("WE DID THE WHEN COMPLETE");
//                    Bukkit.dispatchCommand(c.getKey(), c.getValue());
//                });
//
//        FoliaHelper.getGlobalScheduler().runDelayed(plugin, task -> {
//            completeTask.complete(new AbstractMap.SimpleImmutableEntry<>(((FoliaPlayer) sender).getBukkitPlayer(), cmd));
//        }, 1L);
        FoliaHelper.runSync(() -> Bukkit.dispatchCommand(FoliaHelper.toBukkitCommandSender(sender), cmd));
    }

    @Override
    public EsCommandSender getConsoleSender() {
        return null;
    }

    @Override
    public SemanticVersion getPluginVersion() {
        return new SemanticVersion(plugin.getDescription().getVersion());
    }

    @Override
    public String getPluginName() {
        return plugin.getDescription().getName();
    }

    @Override
    public int runTaskLater(Runnable task, long ticks) {
        ScheduledTask foliaTask = FoliaHelper.runTaskLater(task, ticks);
        int id = foliaTask.hashCode();
        if (tasks.containsKey(id)) {
            throw new RuntimeException("Hash collision?");
        }

        tasks.put(id, foliaTask);
        return id;
    }

    @Override
    public void runTask(Runnable task) {
        FoliaHelper.runTaskOnNextTick(task);
    }

    @Override
    public void cancelTask(int id) {
        if (!tasks.containsKey(id)) {
            throw new RuntimeException("That task doesn't exist");
        }

        ScheduledTask task = tasks.get(id);
        if (task.isCancelled() || task.getExecutionState() == ScheduledTask.ExecutionState.FINISHED) {
            return;
        }

        task.cancel();
    }

    @Override
    public EsLogger getLogger() {
        return new FoliaLogger();
    }

    @Override
    public void startEvents() {
        Bukkit.getPluginManager().registerEvents(listener, EsToolsBukkit.plugin);
    }

    @Override
    public void registerCommand(String cmd, EsToolsTabCompleter tab) {
        PluginCommand command = Objects.requireNonNull(Bukkit.getPluginCommand(cmd));
        command.setExecutor(listener);
        if (Main.tabCompleteEnabled) {
            command.setTabCompleter(BukkitTabCompleteGenerator.generate(tab));
        }
    }

    @Override
    public void setTabCompleter(String cmd, EsToolsTabCompleter tab) {
        PluginCommand command = Objects.requireNonNull(Bukkit.getPluginCommand(cmd));
        if (Main.tabCompleteEnabled) {
            command.setTabCompleter(BukkitTabCompleteGenerator.generate(tab));
        }
    }

    @Override
    public void setCommandPermission(String cmd, String perm) {
        PluginCommand command = Objects.requireNonNull(Bukkit.getPluginCommand(cmd));
        command.setPermission(perm);

        if (Main.minecraftVersion.getMinor() > 0) {
            //noinspection deprecation, is still useful in pre 1.13 and technically is useful in rare situations post 1.13
            command.setPermissionMessage(EsToolsCommand.translate("&cYou do not have permission to run this command."));
        }
    }

    @Override
    public void broadcast(String msg, String perm) {
        Bukkit.broadcast(msg, perm);
    }

    @Override
    public void broadcast(String msg) {
        Bukkit.broadcastMessage(msg);
    }

    @Override
    public EsWorld getWorld(String name) {
        return new FoliaWorld(Bukkit.getWorld(name));
    }

    @Override
    public String[] getRelevantInternalTypes() {
        return new String[] {};
    }
}

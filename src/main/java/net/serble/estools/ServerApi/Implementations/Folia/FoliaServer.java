package net.serble.estools.ServerApi.Implementations.Folia;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.serble.estools.*;
import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.ServerApi.EsPotType;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitTabCompleteGenerator;
import net.serble.estools.ServerApi.Interfaces.*;
import org.bukkit.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.*;

public class FoliaServer implements EsServerSoftware {
    private final JavaPlugin plugin;
    private final FoliaEventsListener listener;
    private final Map<Integer, ScheduledTask> tasks = new HashMap<>();
    private static TaskChainFactory taskChainFactory;

    public FoliaServer(Object pluginObj) {
        plugin = (JavaPlugin) pluginObj;
        taskChainFactory = BukkitTaskChainFactory.create(plugin);
        listener = new FoliaEventsListener();
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
    public EsEntity getEntity(UUID uuid) {
        if (Main.minecraftVersion.getMinor() > 11) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) {
                return null;
            }
            return new FoliaEntity(entity);
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
                for (org.bukkit.entity.Player p : Bukkit.getOnlinePlayers()) {
                    players.add(new FoliaPlayer(p));
                }
                return players;
            }
            else {
                org.bukkit.entity.Player[] players = (org.bukkit.entity.Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null, new Object[0]);
                List<EsPlayer> users = new ArrayList<>();
                for (org.bukkit.entity.Player p : players) {
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
    public EsItemStack createItemStack(String material, int amount) {
        return new FoliaItemStack(material, amount);
    }

    @SuppressWarnings("deprecation")
    @Override
    public EsItemStack createPotion(EsPotType potType, String effect, int duration, int amp, int amount) {
        if (Main.minecraftVersion.getMinor() >= 9) {
            String type = potType == EsPotType.drink ?
                    "POTION" :
                    potType.toString().toUpperCase() + "_POTION";
            ItemStack pot = new ItemStack(Material.valueOf(type), amount);

            String effType;
            try {
                effType = Effects.getByName(effect);
            } catch (IllegalArgumentException e) {
                return null;
            }

            PotionMeta meta = (PotionMeta) pot.getItemMeta();
            assert meta != null;
            meta.addCustomEffect(new PotionEffect(Objects.requireNonNull(Registry.EFFECT.match(effType)), duration, amp-1), true);
            pot.setItemMeta(meta);
            return new FoliaItemStack(pot);
        } else if (Main.minecraftVersion.getMinor() >= 4) {
            String effType;
            try {
                effType = Effects.getPotionByName(effect);
            } catch (IllegalArgumentException e) {
                return null;
            }

            org.bukkit.potion.Potion potion = new org.bukkit.potion.Potion(Objects.requireNonNull(Registry.POTION.match(effType)), amp);
            potion.setSplash(potType == EsPotType.splash);
            return new FoliaItemStack(potion.toItemStack(1));
        } else {  // This isn't possible to get to because this class won't load on 1.3 and below
            return null;
        }
    }

    @Override
    public EsInventory createInventory(EsPlayer owner, int size, String title) {
        InventoryHolder holder = owner == null ? null : ((FoliaPlayer) owner).getBukkitPlayer();
        return new FoliaInventory(Bukkit.createInventory(holder, size, title));
    }

    @SuppressWarnings("deprecation")
    @Override
    public String[] getPotionEffectTypes() {
        // We need to use the deprecated .values() method because Registry doesn't exist in old versions
        PotionEffectType[] effectTypes = PotionEffectType.values();
        String[] out = new String[effectTypes.length];
        for (int i = 0; i < effectTypes.length; i++) {
            out[i] = effectTypes[i].getName();  // Same reason as above for deprecated method
        }
        return out;
    }

    @Override
    public String[] getEnchantments() {
        if (Main.minecraftVersion.getMinor() > 12) {
            String[] out = new String[(int) Registry.ENCHANTMENT.stream().count()];
            int i = 0;
            for (Enchantment e : Registry.ENCHANTMENT) {
                out[i] = e.getKey().getKey();
                i++;
            }
            return out;
        }

        // Pre 1.13, we need to use the helper to get all the keys
        Set<Map.Entry<String, String>> enchSet = FoliaEnchantmentsHelper.entrySet();
        String[] enchs = new String[enchSet.size()];
        int i = 0;
        for (Map.Entry<String, String> enchEntry : enchSet) {
            enchs[i] = enchEntry.getKey();
            i++;
        }

        return enchs;
    }

    @Override
    public String[] getSounds() {
        Sound[] sounds = Sound.values();
        String[] strSounds = new String[sounds.length];
        for (int i = 0; i < sounds.length; i++) {
            strSounds[i] = sounds[i].name();
        }
        return strSounds;
    }

    @Override
    public String[] getMaterials(boolean onlyItems) {
        Material[] materials = Material.values();
        String[] strMaterials = new String[materials.length];
        for (int i = 0; i < materials.length; i++) {
            if (onlyItems && Main.minecraftVersion.getMinor() >= 12 && !materials[i].isItem()) {
                continue;
            }
            strMaterials[i] = materials[i].name();
        }
        return strMaterials;
    }

    @Override
    public boolean doesEnchantmentExist(String name) {
        try {
            return FoliaHelper.getBukkitEnchantment(name) != null;
        } catch (Exception e) {
            return false;
        }
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
    public EsItemStack createItemStack(Object internalObject) {
        return new FoliaItemStack(internalObject);
    }

    @Override
    public String[] getRelevantInternalTypes() {
        return new String[] {
                "CraftItemStack"  // CChest config files
        };
    }
}

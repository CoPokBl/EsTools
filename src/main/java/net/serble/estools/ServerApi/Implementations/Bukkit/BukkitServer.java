package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.EsToolsCommand;
import net.serble.estools.EsToolsTabCompleter;
import net.serble.estools.Main;
import net.serble.estools.SemanticVersion;
import net.serble.estools.ServerApi.*;
import net.serble.estools.ServerApi.Implementations.Bukkit.EventHandlers.BukkitEventsListener;
import net.serble.estools.ServerApi.Implementations.Bukkit.EventHandlers.BukkitEventsListenerPost1_1;
import net.serble.estools.ServerApi.Implementations.Bukkit.EventHandlers.BukkitEventsListenerPost1_4;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.*;
import net.serble.estools.ServerApi.Interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class BukkitServer implements EsServer {
    private final JavaPlugin plugin;
    private final List<Listener> listeners;
    private final CommandExecutor cmdExecutor;
    private static final Set<EsMaterial> materials = new HashSet<>();
    private static final Set<EsMaterial> itemMaterials = new HashSet<>();
    private static Set<EsSound> sounds = new HashSet<>();

    public BukkitServer(Object pluginObj) {
        plugin = (JavaPlugin) pluginObj;

        listeners = new ArrayList<>();

        SemanticVersion mcVer = getVersion();
        if (mcVer.isAtLeast(1, 5, 0)) {
            listeners.add(new BukkitEventsListenerPost1_4());
        }
        if (mcVer.isAtLeast(1, 2, 0)) {
            listeners.add(new BukkitEventsListenerPost1_1());
        }

        BukkitEventsListener bel = new BukkitEventsListener();
        listeners.add(bel);
        cmdExecutor = bel;
    }

    @Override
    public void initialise() {
        for (Material mat : Material.values()) {
            EsMaterial esMat;
            if (Main.minecraftVersion.getMinor() > 12) {
                esMat = EsMaterial.createUnchecked(mat.getKey().getKey().toLowerCase());

                if (mat.isItem()) {
                    itemMaterials.add(esMat);
                }
            } else {
                esMat = EsMaterial.createUnchecked(mat.name().toLowerCase());

                itemMaterials.add(esMat);
            }

            materials.add(esMat);
        }

        sounds = BukkitSoundHelper.getSounds();
    }

    @Override
    public EsPlayer getPlayer(String name) {
        Player p = Bukkit.getPlayer(name);
        if (p == null) {
            return null;
        }
        return new BukkitPlayer(p);
    }

    @Override
    public EsEntity getEntity(UUID uuid) {
        if (Main.minecraftVersion.getMinor() > 11) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) {
                return null;
            }
            return BukkitHelper.fromBukkitEntity(entity);
        }

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uuid)) {
                    return BukkitHelper.fromBukkitEntity(entity);
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
            Bukkit.getLogger().warning("Could not detect version from: " + versionS);
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
                    players.add(new BukkitPlayer(p));
                }
                return players;
            }
            else {
                Player[] players = (Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null, new Object[0]);
                List<EsPlayer> users = new ArrayList<>();
                for (Player p : players) {
                    users.add(new BukkitPlayer(p));
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
        return new BukkitItemStack(material, amount);
    }

    @SuppressWarnings("deprecation")
    @Override
    public EsPotion createPotion(EsPotType potType, EsPotionEffect effect, int amount) {
        if (Main.minecraftVersion.getMinor() >= 9) {
            String type = potType == EsPotType.drink ?
                    "POTION" :
                    potType.toString().toUpperCase() + "_POTION";
            ItemStack pot = new ItemStack(Material.valueOf(type), amount);

            BukkitMetaHelper.setPotionType(pot, effect);  // No ItemMeta allowed

            return new BukkitPotion(pot);
        } else if (Main.minecraftVersion.getMinor() >= 4) {
            return BukkitPotionsHelper.createPotion1_4(potType, effect, amount);
        } else {
            short potionDat = BukkitPotionsHelper1_3.getPotionIdFromEffectType(effect.getType());
            if (potType == EsPotType.splash || potType == EsPotType.lingering) {
                potionDat += 16384;
            }
            ItemStack stack = new ItemStack(Material.POTION, 1, potionDat);
            return new BukkitPotionVeryOld(stack);
        }
    }

    @Override
    public EsPotion createPotion(EsPotType potType) {
        if (Main.minecraftVersion.getMinor() >= 9) {
            String type = potType == EsPotType.drink ?
                    "POTION" :
                    potType.toString().toUpperCase() + "_POTION";

            ItemStack pot = new ItemStack(Material.valueOf(type), 1);

            return new BukkitPotion(pot);
        } else if (Main.minecraftVersion.getMinor() >= 4) {
            return BukkitPotionsHelper.createPotion1_4(potType);
        } else {
            // This method isn't called pre 1.4 because CChest doesn't work
            // With the way this works it wouldn't work in pre 1.4.
            throw new UnsupportedOperationException("Proper support in this version has not been implemented.");
        }
    }

    @Override
    public EsInventory createInventory(EsPlayer owner, int size, String title) {
        if (!Main.minecraftVersion.isAtLeast(1, 2, 0)) {
            // Creating inventories was not a feature in this version
            // InventoryHolder also didn't exist, hence why we must use
            // a helper class
            throw new UnsupportedOperationException("Creating inventories is not supported in this version");
        }
        return BukkitInventoryHelper.createInventory(owner, size, title);
    }

    @Override
    public Set<EsPotionEffectType> getPotionEffectTypes() {
        if (Main.minecraftVersion.isLowerThan(1, 1, 0)) {
            return BukkitPotionsHelper1_3.getPotionList();
        }
        return BukkitEffectHelper.getEffectList();
    }

    @Override
    public Set<EsPotionEffectType> getOldPotionTypes() {
        if (Main.minecraftVersion.isLowerThan(1, 1, 0)) {
            return BukkitPotionsHelper1_3.getPotionList();
        }
        return BukkitEffectHelper.getPotionList();
    }

    @Override
    public Set<EsEnchantment> getEnchantments() {
        return BukkitEnchantmentHelper.getEnchantmentList();
    }

    @Override
    public Set<EsSound> getSounds() {
        return sounds;
    }

    @Override
    public File getDataFolder() {
        return plugin.getDataFolder();
    }

    @Override
    public void dispatchCommand(EsCommandSender sender, String cmd) {
        Bukkit.dispatchCommand(BukkitHelper.toBukkitCommandSender(sender), cmd);
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
        return Bukkit.getScheduler().scheduleSyncDelayedTask(EsToolsBukkit.plugin, task, ticks);
    }

    @Override
    public void runTask(Runnable task) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(EsToolsBukkit.plugin, task);
    }

    @Override
    public void cancelTask(int id) {
        Bukkit.getScheduler().cancelTask(id);
    }

    @Override
    public EsLogger getLogger() {
        return new BukkitLogger();
    }

    @Override
    public void startEvents() {
        if (Main.minecraftVersion.isMoreThan(1, 0, 0)) {  // Events work differently in 1.0
            for (Listener l : listeners) {
                Bukkit.getPluginManager().registerEvents(l, EsToolsBukkit.plugin);
            }
            return;
        }

        // TODO: 1.0 events
        Main.logger.warning("Events are not currently functional in Minecraft 1.0.0 and below");
    }

    @Override
    public void registerCommand(String cmd, EsToolsTabCompleter tab) {
        PluginCommand command = Objects.requireNonNull(Bukkit.getPluginCommand(cmd));
        if (!Main.tabCompleteEnabled || command.getTabCompleter() == null) {
            command.setExecutor(cmdExecutor);
        }
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
    public Set<EsMaterial> getMaterials(boolean onlyItems) {
        return onlyItems ? itemMaterials : materials;
    }

    @Override
    public EsWorld getWorld(String name) {
        return new BukkitWorld(Bukkit.getWorld(name));
    }

    @Override
    public String[] getRelevantInternalTypes() {
        return new String[] {};
    }
}

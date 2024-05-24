package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.SemanticVersion;
import net.serble.estools.ServerApi.Interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class BukkitServer implements EsServerSoftware {
    private final JavaPlugin plugin;

    public BukkitServer(Object pluginObj) {
        plugin = (JavaPlugin) pluginObj;
    }

    @Override
    public EsPlayer getPlayer(String name) {
        return new BukkitPlayer(Bukkit.getPlayer(name));
    }

    @Override
    public EsEntity getEntity(UUID uuid) {
        if (Main.minecraftVersion.getMinor() > 11) {
            return new BukkitEntity(Bukkit.getEntity(uuid));
        }

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId() == uuid) {
                    return new BukkitEntity(entity);
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

        Bukkit.getLogger().info("Version detected as: 1." + minor + '.' + patch + " from: " + versionS);
        return new SemanticVersion(1, minor, patch);
    }

    @Override
    public Collection<? extends EsPlayer> getOnlinePlayers() {
        try {
            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                List<EsPlayer> players = new ArrayList<>();
                for (org.bukkit.entity.Player p : Bukkit.getOnlinePlayers()) {
                    players.add(new BukkitPlayer(p));
                }
                return players;
            }
            else {
                org.bukkit.entity.Player[] players = (org.bukkit.entity.Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null, new Object[0]);
                List<EsPlayer> users = new ArrayList<>();
                for (org.bukkit.entity.Player p : players) {
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
    public EsItemStack createItemStack(String material, int amount) {
        return new BukkitItemStack(material, amount);
    }

    @Override
    public EsInventory createInventory(EsPlayer owner, int size, String title) {
        InventoryHolder holder = owner == null ? null : ((BukkitPlayer) owner).getBukkitPlayer();
        return new BukkitInventory(Bukkit.createInventory(holder, size, title));
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

    @SuppressWarnings("deprecation")
    @Override
    public String[] getEnchantments() {
        Enchantment[] enchantments = (Enchantment[]) Registry.ENCHANTMENT.stream().toArray();
        String[] out = new String[enchantments.length];
        for (int i = 0; i < enchantments.length; i++) {
            Enchantment enchantment = enchantments[i];
            out[i] = enchantment.getName();
        }
        return out;
    }

    @Override
    public boolean doesEnchantmentExist(String name) {
        try {
            return BukkitHelper.getBukkitEnchantment(name) != null;
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
}

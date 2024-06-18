package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Commands.Warps.WarpLocation;
import net.serble.estools.Config.ConfigManager;
import net.serble.estools.Config.Schemas.GeneralConfig.EsToolsConfig;
import net.serble.estools.Config.Schemas.GeneralConfig.UpdaterConfig;
import net.serble.estools.Config.Schemas.Give.GiveConfig;
import net.serble.estools.Config.Schemas.Give.GiveSettings;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsSerialisableItemStack;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Tool for migrating from legacy Bukkit configs to v5 compatible configs.
 * Once migration is complete an empty file ".configsmigrated" is created.
*/
public class BukkitConfigMigrator {
    private static final String migratedFile = ".configsmigrated";
    private static final String mainConfig = "config.yml";
    private static final String giveConfig = "give.yml";
    private static final String godsConfig = "gods.yml";
    private static final String buddhaConfig = "buddhas.yml";
    private static final String warpsConfig = "warps.yml";
    private static final String cchestsFolder = "cchests";

    public static void checkPerformMigration() {
        File migratedAlreadyFile = new File(Main.server.getDataFolder(), migratedFile);

        // If we have already migrated, or we have no data to migrate then return
        if (migratedAlreadyFile.exists()) {
            return;
        }

        // If there is no data at all then we don't need to migrate so mark as migrated
        if (!Main.server.getDataFolder().exists()) {
            try {
                boolean ignored = migratedAlreadyFile.getParentFile().mkdirs();
                boolean ignored2 = migratedAlreadyFile.createNewFile();
            } catch (IOException e) {
                Main.logger.severe("[EsTools] Failed to create file specifying that configs are migrated");
            }
        }

        Main.logger.warning("[EsTools] It seems you are using an outdated config, migrating files to v5");

        migrateMainConfig();
        migrateGiveConfig();
        migrateGodsConfig();
        migrateWarpsConfig();
        migrateCChestsConfig();

        Main.logger.info("[EsTools] Successfully migrated all existing config files");
        try {
            boolean ignored = migratedAlreadyFile.createNewFile();
        } catch (IOException e) {
            Main.logger.severe("[EsTools] Failed to create file specifying that configs are migrated");
        }
    }

    private static void migrateMainConfig() {  // config.yml
        File file = new File(Main.server.getDataFolder(), mainConfig);
        if (!file.exists()) {
            return;
        }

        if (file.length() == 0) {
            file.delete();
            return;
        }

        try {  // We will now try to get all the values, defaulting to their current defaults
            FileConfiguration config = new YamlConfiguration();
            config.load(file);

            EsToolsConfig newConfig = new EsToolsConfig();
            newConfig.setSafeTp(config.getBoolean("safetp", newConfig.isSafeTp()));
            newConfig.setMetrics(config.getBoolean("metrics", newConfig.isMetrics()));

            UpdaterConfig updaterConfig = newConfig.getUpdater();
            updaterConfig.setGithubReleasesUrl(config.getString("updater.github-release-url", updaterConfig.getGithubReleasesUrl()));
            updaterConfig.setAutoUpdate(config.getBoolean("updater.auto-update", updaterConfig.isAutoUpdate()));
            updaterConfig.setWarnOnOutdated(config.getBoolean("updater.warn-on-outdated", updaterConfig.isWarnOnOutdated()));
            updaterConfig.setLogOnOutdated(config.getBoolean("updater.log-on-outdated", updaterConfig.isLogOnOutdated()));

            // Move file to file.old
            File oldFile = new File(Main.server.getDataFolder(), mainConfig + ".old");
            if (!file.renameTo(oldFile)) {
                throw new FileNotFoundException("Failed to rename old config file");
            }

            // Save new config
            ConfigManager.save(file, newConfig);

            Main.logger.info("[EsTools] Successfully migrated " + mainConfig);
        } catch (IOException | InvalidConfigurationException e) {
            Main.logger.severe("[EsTools] Failed to migrate main config, it might be invalid");
            Main.logger.severe("[EsTools] You may need to delete config.yml for it to function again");
            Main.logger.severe("[EsTools] Your current setting ARE NOT BEING RESPECTED");
        }
    }

    private static void migrateGiveConfig() {  // config.yml
        File file = new File(Main.server.getDataFolder(), giveConfig);
        if (!file.exists()) {
            return;
        }

        if (file.length() == 0) {
            file.delete();
            return;
        }

        try {  // We will now try to get all the values, defaulting to their current defaults
            FileConfiguration config = new YamlConfiguration();
            config.load(file);

            GiveConfig newConfig = new GiveConfig();
            GiveSettings settings = newConfig.getSettings();
            settings.setAddWithoutUnderscores(config.getBoolean("settings.addWithoutUnderscores", settings.isAddWithoutUnderscores()));
            settings.setRemoveWithUnderscores(config.getBoolean("settings.removeWithUnderscores", settings.isRemoveWithUnderscores()));

            ConfigurationSection itemsSec = config.getConfigurationSection("items");
            if (itemsSec != null) {
                Map<String, String> items = new HashMap<>();
                for (String key : itemsSec.getKeys(false)) {
                    items.put(key, itemsSec.getString(key));
                }

                newConfig.setItems(items);
            }

            // Move file to file.old
            File oldFile = new File(Main.server.getDataFolder(), giveConfig + ".old");
            if (!file.renameTo(oldFile)) {
                throw new FileNotFoundException("Failed to rename old config file");
            }

            // Save new config
            ConfigManager.save(file, newConfig);

            Main.logger.info("[EsTools] Successfully migrated " + giveConfig);
        } catch (IOException | InvalidConfigurationException e) {
            Main.logger.severe("[EsTools] Failed to migrate config, it might be invalid");
            Main.logger.severe("[EsTools] You may need to delete " + giveConfig + " for it to function again");
            Main.logger.severe("[EsTools] Your current setting ARE NOT BEING RESPECTED");
        }
    }

    private static void migrateGodsConfig() {  // config.yml
        File file = new File(Main.server.getDataFolder(), godsConfig);
        if (!file.exists()) {
            return;
        }

        if (file.length() == 0) {
            file.delete();
            return;
        }

        try {  // We will now try to get all the values, defaulting to their current defaults
            FileConfiguration config = new YamlConfiguration();
            config.load(file);

            List<String> gods = config.getStringList("gods");
            List<String> buddhas = config.getStringList("buddhas");

            // Move file to file.old
            File oldFile = new File(Main.server.getDataFolder(), godsConfig + ".old");
            if (!file.renameTo(oldFile)) {
                throw new FileNotFoundException("Failed to rename old config file");
            }

            // Save gods.yml and buddhas.yml
            ConfigManager.save(file, gods);
            ConfigManager.save(buddhaConfig, buddhas);

            Main.logger.info("[EsTools] Successfully migrated " + godsConfig);
        } catch (IOException | InvalidConfigurationException e) {
            Main.logger.severe("[EsTools] Failed to migrate config, it might be invalid");
            Main.logger.severe("[EsTools] You may need to delete " + godsConfig + " for it to function again");
            Main.logger.severe("[EsTools] Your current setting ARE NOT BEING RESPECTED");
        }
    }

    private static void migrateWarpsConfig() {  // config.yml
        File file = new File(Main.server.getDataFolder(), warpsConfig);
        if (!file.exists()) {
            return;
        }

        if (file.length() == 0) {
            file.delete();
            return;
        }

        try {  // We will now try to get all the values, defaulting to their current defaults
            // Register the old WarpLocation class, so it can be parsed
            ConfigurationSerialization.registerClass(OldWarpLocation.class, "WarpLocation");
            FileConfiguration config = new YamlConfiguration();
            config.load(file);

            Map<String, WarpLocation> newConfig = new HashMap<>();

            List<?> warpList = config.getList("warps");
            if (warpList == null) {
                return;
            }

            warpList.sort((w1, w2) -> {
                if (w1 instanceof OldWarpLocation && w2 instanceof OldWarpLocation) {
                    OldWarpLocation warp1 = (OldWarpLocation)w1;
                    OldWarpLocation warp2 = (OldWarpLocation)w1;

                    return warp1.name.compareTo(warp2.name);
                }

                return 0;
            });

            warpList.forEach(w -> {
                if (w instanceof OldWarpLocation) {
                    OldWarpLocation warp = (OldWarpLocation)w;
                    WarpLocation loc = new WarpLocation();
                    loc.setGlobal(warp.global);
                    loc.setName(warp.name);
                    loc.setLocation(BukkitHelper.fromBukkitLocation(warp.location));
                    newConfig.put(warp.name, loc);
                }
            });

            // Move file to file.old
            File oldFile = new File(Main.server.getDataFolder(), warpsConfig + ".old");
            if (!file.renameTo(oldFile)) {
                throw new FileNotFoundException("Failed to rename old config file");
            }

            // Save new config
            ConfigManager.save(file, newConfig);

            Main.logger.info("[EsTools] Successfully migrated " + warpsConfig);
        } catch (IOException | InvalidConfigurationException e) {
            Main.logger.severe("[EsTools] Failed to migrate config, it might be invalid");
            Main.logger.severe("[EsTools] You may need to delete " + warpsConfig + " for it to function again");
            Main.logger.severe("[EsTools] Your current setting ARE NOT BEING RESPECTED");
        }
    }

    private static void migrateCChestsConfig() {  // config.yml
        File folder = new File(Main.server.getDataFolder(), cchestsFolder);

        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }

        List<File> savedPlayers = new ArrayList<>(Arrays.asList(files));
        savedPlayers.removeIf(file -> {
            if (file.length() == 0) {
                file.delete();
                return true;
            }

            return false;
        });

        Main.logger.warning("[EsTools] Now migrating CChest config files, this may take a while");

        for (File file : savedPlayers) {
            try {  // We will now try to get all the values, defaulting to their current defaults
                FileConfiguration config = new YamlConfiguration();
                config.load(file);

                if (!config.contains("items")) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                ArrayList<ItemStack> content = (ArrayList<ItemStack>) Objects.requireNonNull(config.get("items"));

                EsSerialisableItemStack[] newConfig = new EsSerialisableItemStack[content.size()];
                for (int i = 0; i < content.size(); i++) {
                    if (content.get(i) == null) {
                        newConfig[i] = null;
                        continue;
                    }

                    newConfig[i] = EsSerialisableItemStack.generate(new BukkitItemStack(content.get(i)));
                }

                // Move file to file.old
                File oldFile = new File(file.getName() + ".old");
                if (!file.renameTo(oldFile)) {
                    throw new FileNotFoundException("Failed to rename old config file");
                }

                // Save new config
                ConfigManager.save(file, newConfig);

                Main.logger.info("[EsTools] Successfully migrated CChest: " + file.getName());
            } catch (IOException | InvalidConfigurationException e) {
                Main.logger.severe("[EsTools] Failed to migrate CChest config, data may be lost");
            }
        }
    }
}

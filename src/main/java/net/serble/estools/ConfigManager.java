package net.serble.estools;

import java.io.*;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	public static final Main plugin = Main.plugin;
	
	public static FileConfiguration load(String fileName) {
		return load(new File(plugin.getDataFolder(), fileName));
	}
	
	@SuppressWarnings("ResultOfMethodCallIgnored")
    public static FileConfiguration load(File configFile) {
		if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
				configFile.createNewFile();
			} catch (IOException e) {
				Bukkit.getLogger().warning("Failed to create config file: " + e);
			}
        }
		
		FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException | IllegalArgumentException e) {
			Bukkit.getLogger().severe("Failed to read config file: " + e);
        }
        
        return config;
	}
	
	@SuppressWarnings("ResultOfMethodCallIgnored")
    public static void save(String file, FileConfiguration config) {
		File configFile = new File(plugin.getDataFolder(), file);
		if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
				configFile.createNewFile();
			} catch (IOException e) {
				Bukkit.getLogger().warning("Failed to create config file: " + e);
			}
        }
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			Bukkit.getLogger().severe("Failed to save config file: " + e);
		}
	}

	// Make sure to save the file after you run this, we don't do that automatically
	public static boolean patchDefaults(FileConfiguration config, FileConfiguration defaults) {
		return patchDefaults("", config, defaults);
	}

	// Patch a section, needed because we must recursively traverse the key hierarchy
	private static boolean patchDefaults(String path, FileConfiguration config, FileConfiguration defaults) {
		Main.asrt(path != null);
		Main.asrt(config != null);
		Main.asrt(defaults != null);

		boolean changedAnything = false;

		ConfigurationSection section = defaults.getConfigurationSection(path);
		if (section == null) {
			if (!config.contains(path)) {
				config.set(path, defaults.get(path));
				changedAnything = true;
			}
		} else {
			Set<String> keys = section.getKeys(false);
			for (String key : keys) {
				String updatedPath = path.isEmpty() ? key : path + "." + key;
				changedAnything = patchDefaults(updatedPath, config, defaults) || changedAnything;
			}
		}

		return changedAnything;
	}

	public static boolean patchDefaults(FileConfiguration config, InputStream defaultsRes) {
		InputStreamReader reader = new InputStreamReader(defaultsRes);
		FileConfiguration defaults = new YamlConfiguration();
		String fileContents;
		try {
			StringBuilder contentsBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				contentsBuilder.append(line).append("\n");
			}

			bufferedReader.close();
			fileContents = contentsBuilder.toString();
			defaults.loadFromString(fileContents);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getLogger().severe("Could not load default config file, there is a good chance this is bug with EsTools");
			return false;
		}

        return patchDefaults(config, defaults);
	}
}

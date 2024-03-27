package net.serble.estools;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	public static final Main plugin = Main.current;
	
	public static FileConfiguration load(String fileName) {
		return load(new File(plugin.getDataFolder(), fileName));
	}
	
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
}

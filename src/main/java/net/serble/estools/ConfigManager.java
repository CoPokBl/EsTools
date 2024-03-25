package net.serble.estools;

import java.io.File;
import java.io.IOException;

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
				e.printStackTrace();
			}
        }
		
		FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
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
				e.printStackTrace();
			}
        }
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

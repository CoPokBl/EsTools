package net.serble.estools.Commands.Give;

import net.serble.estools.*;
import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;

// TODO: Config class
public class Give implements EsToolsTabCompleter {
	private static HashMap<String, String> materialNames;

    public static EsItemStack getItem(String name, int amount) {
		name = name.toUpperCase();
		String mat = materialNames.get(name);
		
		if (mat == null) {
			return null;
		}
		
		return Main.server.createItemStack(mat, amount);
	}

	@Override
	public List<String> onTabComplete(EsCommandSender sender, Command command, String alias, String[] args) {
		List<String> tab = new ArrayList<>();
		
		if (args.length == 1) {
			for (String mat : materialNames.keySet()) {
				tab.add(mat.toLowerCase());
			}
			
			Collections.sort(tab);
		} else if (args.length == 2) {
			tab.add("64");
		}
	
		return EsToolsCommand.fixTabComplete(tab, args[args.length - 1]);
	}
	
	@SuppressWarnings("DataFlowIssue")  // Not possible
    public static void enable() {
		// initialise hashmaps
        //noinspection Convert2Diamond
        materialNames = new HashMap<String, String>();

		// Load config
		FileConfiguration f = ConfigManager.load("give.yml");

		// check if it has settings
		HashMap<String, String> materials = new HashMap<>();
		
		if (	f.contains("settings") &&
				f.contains("settings.addWithoutUnderscores") &&
				f.contains("settings.removeWithUnderscores")) {  // if it has these values then load files

			materials = loadItems(f);
		}
		
		if (materials.isEmpty()) {  // if it hasn't loaded or if it cant load anything
			Bukkit.getLogger().info("Creating new give.yml");

			try {
				copyDefaultGiveYML();
				f = ConfigManager.load(new File(EsToolsBukkit.plugin.getDataFolder(), "give.yml"));
				materials = loadItems(f);
			} catch (Exception ignored) { }
		}

		// Load normal items
        boolean addWithoutUnderscores = (boolean) f.get("settings.addWithoutUnderscores");
        boolean removeWithUnderscores = (boolean) f.get("settings.removeWithUnderscores");

		for (Material mat : Material.values()) {
			if (Main.minecraftVersion.getMinor() >= 12 && !mat.isItem()) continue;

			String name = mat.toString().toUpperCase();

			if (name.contains("_") && addWithoutUnderscores) {
				materialNames.put(name.replace("_",""), mat.name());

				if (removeWithUnderscores) {
					continue;
				}
			}

			materialNames.put(name, mat.name());
		}

		// Load custom items
		for (Entry<String, String> s : materials.entrySet()) {
			Material mat = Material.getMaterial(s.getValue().toUpperCase());

			if (mat != null) {
				materialNames.put(s.getKey().toUpperCase(), mat.name());
			}
		}
	}
	
	private static HashMap<String, String> loadItems(FileConfiguration f) {
		HashMap<String, String> ms = new HashMap<>();
		
		if (f.contains("items")) {
			ConfigurationSection itemsSection = f.getConfigurationSection("items");
			assert itemsSection != null;

			itemsSection.getKeys(false).forEach(key -> {
				String content = (String) f.get("items." + key);
				ms.put(key, content);
			});
		}
		
		return ms;
	}
	
	private static void copyDefaultGiveYML() throws IOException {
        // The class loader that loaded the class
        ClassLoader classLoader = Give.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("give.yml");

        // the stream holding the file content
        if (inputStream == null) {
            return;
        }
        
    	byte[] buffer = new byte[inputStream.available()];
        //noinspection ResultOfMethodCallIgnored
        inputStream.read(buffer);
		
		File targetFile = new File(EsToolsBukkit.plugin.getDataFolder(), "give.yml");
	    OutputStream outStream = Files.newOutputStream(targetFile.toPath());
	    outStream.write(buffer);
	    
	    inputStream.close();
	    outStream.close();  
    }

	public static EsItemStack parseArgs(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException();
		}

		int amount = 1;
		if (args.length > 1) {
			try {
				amount = Integer.parseInt(args[1]);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}

		return getItem(args[0], amount);
	}
}

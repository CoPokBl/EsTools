package me.CoPokBl.EsTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class Give implements TabCompleter {
	
	private static HashMap<String, Material> cmats;
	private static HashMap<String, Material> nmats;

	private static boolean awu;
	private static boolean rwu;
	
	public static ItemStack getItem(String name, int amount) {	
		name = name.toUpperCase();
		
		Material mat = cmats.get(name);
		
		if (mat == null) {
			mat = nmats.get(name);
			if (mat == null)
				return null;
		}
		
		return new ItemStack(mat, amount);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> tab = new ArrayList<String>();
		
		if (args.length == 1) {
			for (String mat : nmats.keySet()) {
				tab.add(mat.toLowerCase());
			}

			for (String mat : cmats.keySet()) {
				tab.add(mat.toLowerCase());
			}
			
			Collections.sort(tab);
		} else if (args.length == 2) {
			tab.add("64");
		}
	
		return CMD.fixTabComplete(tab, args[args.length - 1]);
	}
	
	public static void enable() {

		// initialise hashmaps
		cmats = new HashMap<String, Material>();
		nmats = new HashMap<String, Material>();

		// Load config
		
		FileConfiguration f = ConfigManager.load(new File(Main.current.getDataFolder(), "give.yml"));

		// check if it has settings

		HashMap<String, String> ms = new HashMap<>();
		
		if (	f.contains("settings") &&
				f.contains("settings.addWithoutUnderscores") &&
				f.contains("settings.removeWithUnderscores")) {  // if it has these values then load files

			ms = loadItems(f);
		}
		
		if (ms == null || ms.isEmpty()) { // if it hasnt loaded or if it cant load anything
			Main.current.getLogger().info("Creating new give.yml");

			try {
				copyDefaultGiveYML();
				f = ConfigManager.load(new File(Main.current.getDataFolder(), "give.yml"));
				ms = loadItems(f);
			} catch (Exception e) {
			}
		}

		// Load normal items

		awu = (boolean) f.get("settings.addWithoutUnderscores");
		rwu = (boolean) f.get("settings.removeWithUnderscores");

		for (Material mat : Material.values()) {
			String name = mat.toString().toUpperCase();

			if (name.contains("_")) {
				if (awu) {
					nmats.put(name.replace("_",""), mat);

					if (rwu) {
						continue;
					}
				}
			}

			nmats.put(name, mat);
		}

		// Load custom items:

		for (Entry<String, String> s : ms.entrySet()) {
			Material mat = Material.getMaterial(s.getValue().toUpperCase());

			if (mat != null) {
				cmats.put(s.getKey().toUpperCase(), mat);
			}
		}
	}
	
	private static HashMap<String, String> loadItems(FileConfiguration f) {
		HashMap<String, String> ms = new HashMap<String, String>();
		
		if (f.contains("items")) {
			f.getConfigurationSection("items").getKeys(false).forEach(key -> {
				String content = (String) f.get("items." + key);
				
				ms.put(key, content);
			});
		}
		
		return ms;
	}
	
	public static void disable() {
		if (cmats == null) {
			return;
		}

		HashMap<String, String> ms = new HashMap<String, String>();
		
		for (Entry<String, Material> s : cmats.entrySet()) {
			ms.put(s.getKey(), s.getValue().toString());
		}
		
		FileConfiguration f = new YamlConfiguration();
		
		for (Entry<String, String> m : ms.entrySet()) {
			f.set("items." + m.getKey(), m.getValue());
		}

		f.set("settings.addWithoutUnderscores", awu);
		f.set("settings.removeWithUnderscores", rwu);
		
		ConfigManager.save("give.yml", f);

//		FileConfiguration f2 = new YamlConfiguration();
//
//		for (Material mat : Material.values()) {
//			String name = mat.toString();
//
//			if (name.contains("_")) {
//				name = name.toLowerCase().replace("_","");
//				f2.set(name, mat.name());
//			}
//		}
//
//		ConfigManager.save("test.yml", f2);
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
		inputStream.read(buffer);
		
		File targetFile = new File(Main.current.getDataFolder(), "give.yml");
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	    
	    inputStream.close();
	    outStream.close();  
    }
}

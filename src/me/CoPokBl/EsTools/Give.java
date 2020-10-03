package me.CoPokBl.EsTools;

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
	
	private static HashMap<String, Material> mats = new HashMap<String, Material>();
	
	public static ItemStack getItem(String name, int amount) {	
		name = name.toUpperCase();
		
		Material mat = Material.getMaterial(name);
		
		if (mat == null) {
			if (mats.containsKey(name)) {
				mat = mats.get(name);
			} else {
				return null;
			}
		}
		
		return new ItemStack(mat, amount);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> tab = new ArrayList<String>();
		
		if (args.length == 1) {
			for (Material mat : Material.values()) {
				tab.add(mat.toString().toLowerCase());
			}
			
			for (String mat : mats.keySet()) {
				tab.add(mat.toLowerCase());
			}
			
			Collections.sort(tab);
		}
	
		return tab;
	}
	
	public static void init() {		
		HashMap<String, String> ms = new HashMap<String, String>();
		
		FileConfiguration f = ConfigManager.load("give.yml");
		
		if (f.contains("items")) {
			f.getConfigurationSection("items").getKeys(false).forEach(key -> {
				String content = (String) f.get("items." + key);
				
				ms.put(key, content);
			});
		}
		
		//ms.remove("example_alias");
		
		for (Entry<String, String> s : ms.entrySet()) {
			mats.put(s.getKey().toUpperCase(), Material.getMaterial(s.getValue().toUpperCase()));
		}
	}
	
	public static void disable() {
		HashMap<String, String> ms = new HashMap<String, String>();
		
		//ms.put("example_alias", "original_item");
		
		for (Entry<String, Material> s : mats.entrySet()) {
			ms.put(s.getKey(), s.getValue().toString());
		}
		
		FileConfiguration f = new YamlConfiguration();
		
		for (Entry<String, String> m : ms.entrySet()) {
			f.set("items." + m.getKey(), m.getValue());
		}
		
		ConfigManager.save("give.yml", f);
	}
}

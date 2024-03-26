package net.serble.estools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Give {

    private static HashMap<String, Material> materials;

    public static ItemStack getItem(String name, int amount) {
		name = name.toUpperCase();

		Material mat = materials.get(name);
        if (mat == null)
            return null;

        return new ItemStack(mat, amount);
	}
	
	public static void enable() {
		// initialise hashmaps
		materials = new HashMap<>();

		// Load normal items
		for (Material mat : Material.values()) {
			String name = mat.toString().toUpperCase();

			if (name.contains("_")) {
                materials.put(name.replace("_", ""), mat);
                continue;
            }

			materials.put(name, mat);
		}

		materials.remove("water");
		materials.remove("lava");
	}

}

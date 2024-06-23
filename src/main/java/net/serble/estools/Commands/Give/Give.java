package net.serble.estools.Commands.Give;

import net.serble.estools.*;
import net.serble.estools.Config.ConfigManager;
import net.serble.estools.Config.Schemas.Give.GiveConfig;
import net.serble.estools.ServerApi.EsMaterial;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;

import java.util.*;
import java.util.Map.Entry;

public class Give implements EsToolsTabCompleter {
	private static HashMap<String, EsMaterial> materialNames;

    public static EsItemStack getItem(String name, int amount) {
		name = name.toLowerCase();
		EsMaterial mat = materialNames.get(name);
		
		if (mat == null) {
			return null;
		}
		
		return Main.server.createItemStack(mat, amount);
	}

	@Override
	public List<String> onTabComplete(EsCommandSender sender, String[] args) {
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

    public static void enable() {
		// initialise hashmaps
        //noinspection Convert2Diamond
        materialNames = new HashMap<String, EsMaterial>();

		// Load config
		GiveConfig config = ConfigManager.load("give.yml", GiveConfig.class);

		// check if it has settings
		Map<String, String> materials = config.getItems();

		// Load normal items
		Set<EsMaterial> builtinMats = Main.server.getMaterials(true);
		for (EsMaterial mat : builtinMats) {
			if (mat == null) {
				continue;
			}

			String name = mat.getKey().toLowerCase();
			if (name.contains("_") && config.getSettings().isAddWithoutUnderscores()) {
				materialNames.put(name.replace("_",""), mat);

				if (config.getSettings().isRemoveWithUnderscores()) {
					continue;
				}
			}

			materialNames.put(name, mat);
		}

		// Load custom items
		for (Entry<String, String> s : materials.entrySet()) {
			if (s == null) {
				continue;
			}

			EsMaterial material = EsMaterial.fromKey(s.getValue().toLowerCase());
			if (material == null) {
				continue;  // Don't trust users, if it doesn't exist then skip
			}

			materialNames.put(s.getKey().toLowerCase(), material);
		}
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

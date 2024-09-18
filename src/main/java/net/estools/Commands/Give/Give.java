package net.estools.Commands.Give;

import net.estools.Config.ConfigManager;
import net.estools.Config.Schemas.Give.GiveConfig;
import net.estools.EsToolsCommand;
import net.estools.EsToolsTabCompleter;
import net.estools.Main;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;

public class Give implements EsToolsTabCompleter {
	private static Map<String, EsMaterial> materialNames;
	private static SortedSet<String> tabComplete;

	public static @Nullable EsMaterial getMaterial(String name) {
        return materialNames.get(name.toLowerCase());
	}

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
			tab = new ArrayList<>(tabComplete);
		} else if (args.length == 2) {
			tab.add("64");
		}
	
		return EsToolsCommand.fixTabComplete(tab, args[args.length - 1]);
	}

	public static SortedSet<String> getTabComplete() {
		return tabComplete;
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

		materialNames = Collections.unmodifiableMap(materialNames);
		tabComplete = Collections.unmodifiableSortedSet(new TreeSet<>(materialNames.keySet()));
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

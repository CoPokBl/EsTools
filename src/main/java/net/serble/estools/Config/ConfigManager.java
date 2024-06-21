package net.serble.estools.Config;

import java.io.*;
import java.util.Arrays;

import net.serble.estools.Main;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

public class ConfigManager {

	@SuppressWarnings("rawtypes")
    public static <T> T load(String fileName, Class<? extends T> base, Class... allowedClasses) {
		String[] cs = new String[allowedClasses.length];
		for (int i = 0; i < cs.length; i++) {
			cs[i] = allowedClasses[i].getName();
		}
		File file = new File(Main.server.getDataFolder(), fileName);
		return load(file, base, cs);
	}

	public static <T> T load(String fileName, Class<? extends T> base, String... allowedClasses) {
		File file = new File(Main.server.getDataFolder(), fileName);
		return load(file, base, allowedClasses);
	}

	// Exists so the above 2 don't make ambig reference when array is not supplied
	public static <T> T load(String fileName, Class<? extends T> base) {
		File file = new File(Main.server.getDataFolder(), fileName);
		return load(file, base);
	}

    public static <T> T load(File configFile, Class<? extends T> base, String... allowedClasses) {
		if (!configFile.exists()) {
            // Save a default version
			try {
				Object instance = base.newInstance();
				save(configFile, instance);
			} catch (IllegalAccessException | InstantiationException e) {
				Main.logger.severe("Failed to save default config, this is a bug");
				throw new RuntimeException("Bug with saving default config: " + e);
			}
        }

		Yaml yaml = createYamlLoader(base, allowedClasses);
		try {
			return yaml.load(new FileInputStream(configFile));
		} catch (FileNotFoundException e) {
			Main.logger.severe("Impossible situation reached, files that we created don't exist");
			throw new RuntimeException("Impossible");
		}
	}

	private static <T> @NotNull Yaml createYamlLoader(Class<? extends T> base, String[] allowedClasses) {
		LoaderOptions options = new LoaderOptions();
		TagInspector taginspector = tag ->
				tag.getClassName().equals(base.getName()) ||
				Arrays.stream(allowedClasses).anyMatch(c -> c.equals(tag.getClassName())) ||
				Arrays.stream(Main.server.getRelevantInternalTypes()).anyMatch(c -> tag.getClassName().endsWith(c) ||
				tag.getClassName().startsWith("net.serble.estools.ServerApi"));  // Allow all server api classes (Stuff like EsMaterial)
		options.setTagInspector(taginspector);
        return new Yaml(new Constructor(base, options));
	}

	public static void save(String file, Object obj) {
		File configFile = new File(Main.server.getDataFolder(), file);
		save(configFile, obj);
	}
	
	@SuppressWarnings("ResultOfMethodCallIgnored")
    public static void save(File configFile, Object obj) {
		if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
				configFile.createNewFile();
			} catch (IOException e) {
				Main.logger.warning("Failed to create config file: " + e);
			}
        }

		Yaml yaml = new Yaml();
		String contents = yaml.dump(obj);
		try {
			FileWriter writer = new FileWriter(configFile);
			writer.write(contents);
			writer.close();
		} catch (IOException e) {
			Main.logger.severe("Failed to save config file: " + e);
		}
	}
}

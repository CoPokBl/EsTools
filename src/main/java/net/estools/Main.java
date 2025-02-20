package net.estools;

import net.estools.Commands.*;
import net.estools.Commands.Give.Give;
import net.estools.Commands.Give.GiveItem;
import net.estools.Commands.MoveSpeed.FlySpeed;
import net.estools.Commands.MoveSpeed.WalkSpeed;
import net.estools.Commands.PowerPick.*;
import net.estools.Commands.Teleport.TpAll;
import net.estools.Commands.Teleport.TpHere;
import net.estools.Commands.Warps.Warp;
import net.estools.Commands.Warps.WarpManager;
import net.estools.Config.ConfigManager;
import net.estools.Config.Schemas.GeneralConfig.EsToolsConfig;
import net.estools.Entrypoints.EsToolsBukkit;
import net.estools.ServerApi.EsEventListener;
import net.estools.ServerApi.EsGameMode;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitConfigMigrator;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.Interfaces.EsLogger;
import net.estools.ServerApi.Interfaces.EsServer;
import net.estools.ServerApi.ServerPlatform;
import net.estools.Signs.SignMain;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue")
public class Main {
	public static Main plugin;
	public static ServerPlatform platform;
	public static SemanticVersion minecraftVersion;
	public static boolean disableUpdater = false;  // Don't get rate limited by GitHub
	public static boolean tabCompleteEnabled = true;
	private static EsToolsConfig config;  // Get with overriden getConfig() method
	public static SemanticVersion newVersion = null;  // The version available to download
	public static boolean newVersionReady = false;
	public static EsServer server;
	public static EsLogger logger;
	private final Object context;
	private static final List<EsEventListener> eventListeners = new ArrayList<>();
	private static final Map<String, EsToolsCommand> commands = new HashMap<>();

	private static final int bStatsId = 21760;

	public Main(ServerPlatform plat, Object context) {
		platform = plat;
		this.context = context;
	}

	public void enable() {
		plugin = this;

		if (platform != ServerPlatform.Injected) {
			server = platform.getServerInstance(context);
		}
		logger = server.getLogger();

		minecraftVersion = server.getVersion();
		server.initialise();
		logger.info("Starting EsTools on platform: " + platform.name() + " (MC: " + minecraftVersion.toString() + ")");

		if (platform == ServerPlatform.Bukkit) {
			// We have to support old configs
			BukkitConfigMigrator.checkPerformMigration();
		}

		// Load the config
		config = ConfigManager.load("config.yml", EsToolsConfig.class);

		// Metrics
		if (config.isMetrics() && platform.supportsMetrics()) {
			Metrics metrics = new Metrics(EsToolsBukkit.plugin, bStatsId);
			metrics.addCustomChart(new SimplePie("vault_enabled", () -> "false"));
			logger.info("Started bStat metrics");
		} else {
			logger.info("Metrics are disabled");
		}

		if (minecraftVersion.getMinor() <= 2) {
			logger.info("Tab completion is not supported for versions 1.2 and below.");
			tabCompleteEnabled = false;
		}

		// Commands
		sc("gms", "gamemode.survival", new GameModeCommand(EsGameMode.Survival, "gms"));
		sc("gmc", "gamemode.creative", new GameModeCommand(EsGameMode.Creative, "gmc"));
		sc("gma", "gamemode.adventure", new GameModeCommand(EsGameMode.Adventure, "gma"), 3);
		sc("gmsp", "gamemode.spectator", new GameModeCommand(EsGameMode.Spectator, "gmsp"), 8);
		sc("tphere", "tp", new TpHere());
		sc("tpall", "tp", new TpAll());
		sc("feed", "feed", new Feed());
		sc("fly", "fly", new Fly(), 2);
		sc("smite", "smite", new Smite());
		sc("invsee", "invsee", new InvSee(), 2);
		sc("i", "give", new GiveItem(false), new Give());
		sc("h", "give", new GiveItem(true), new Give());
		sc("estools", new EsTools());
		sc("ench", "ench", new Ench(), 1);
		sc("fix", "fix", new Fix());
		sc("cchest", new CChest(), 7);
		sc("back", "back", new Back(), 1);
		sc("setstack", "setstack", new SetStack());
		sc("ci", "clearinv", new ClearInv());
		sc("day", "time", new Day());
		sc("night", "time", new Night());
		sc("noon", "time", new Noon());
		sc("midnight", "time", new Midnight());
		sc("sun", "weather", new Sun());
		sc("rain", "weather", new Rain());
		sc("thunder", "weather", new Thunder());
		sc("walkspeed", "walkspeed", new WalkSpeed(), 4);
		sc("flyspeed", "flyspeed", new FlySpeed(), 3);
		sc("setunbreakable", "setunbreakable", new SetUnbreakable(), 1);
		sc("hideflags", "hideflags", new HideFlags(), 8);
		sc("eff", "effect", new Eff(), 1);
		sc("safetp", "safetp", new SafeTp(), 1);
		sc("infinite", "infinite", new Infinite(), 1);
		sc("sethunger", "sethunger", new SetHunger());
		sc("setsaturation", "setsaturation", new SetSaturation());
		sc("disposal", "disposal", new Disposal(), 1, 2);

		sc("powerpick", "powerpick", new PowerPick(), 1);
		sc("poweraxe", "powerpick", new PowerAxe(), 1);
		sc("powersword", "powerpick", new PowerSword(), 1);
		sc("powershovel", "powerpick", new PowerShovel(), 1);
		sc("powerhoe", "powerpick", new PowerHoe(), 1);

		sc("lore", "lore", new Lore(), null, 4, 6);
		sc("rename", "rename", new Rename(), null, 4, 6);
		sc("sudo", "sudo", new Sudo());

		sc("heal", "heal", new Heal());
		sc("suicide", "suicide", new Suicide());
		sc("sethealth", "sethealth", new SetHealth());
		sc("setmaxhealth", "setmaxhealth", new SetMaxHealth(), 4);
		sc("getinfo", "getinfo", new GetInfo());

		sc("editsign", "editsign", new EditSign());

		sc("god", "god", new God(), 1);
		sc("buddha", "god", new Buddha(), 1);

		sc("music", "music", new Music(), 9);
		sc("potion", "potion", new Potion());

		sc("bukkitdata", "bukkitdata", new BukkitData(), 14);

		sc("warp", "warps.use", new Warp());
		sc("warps", "warps.manage", new WarpManager());

		sc("mount", "mount", new Mount());
		sc("dismount", "mount", new Dismount());

		sc("top", "top", new Top());
		sc("open", "open", new Open());
		sc("burn", "burn", new Burn());
		sc("extinguish", "extinguish", new Extinguish());

		// Load other features
		if (minecraftVersion.getMinor() > 0) {  // Enchants and events don't work on 1.0.0
			PowerTool.init();
			SignMain.init();
		}

		Give.enable();
		if (!disableUpdater) {
			Updater.checkForUpdate();
		}

		server.startEvents();  // Events will now trigger
	}

	public static void callEvent(EsEvent event) {
		for (EsEventListener listener : eventListeners) {
			listener.executeEvent(event);
		}
	}

	/**
	 * Handle the execution of a plugin command, should be called by a server implementation.
	 * @param sender The entity executing the command.
	 * @param cmd The command being run.
	 * @param args The arguments the command is being executed with.
	 * @return True if command usage should be printed, otherwise false.
	 */
	public static boolean executeCommand(EsCommandSender sender, String cmd, String[] args) {
		if (!commands.containsKey(cmd)) {
			logger.severe("&cThe command: '" + cmd + "', does not exist but was executed");
			EsToolsCommand.send(sender, "&cThis command doesn't exist, this is a bug");
			return false;
		}

		try {
			commands.get(cmd).execute(sender, args);  // Don't return result, we handle usage messages ourself.
		} catch (NotImplementedException unused) {
			EsToolsCommand.send(sender, "&cThis command has not been implemented yet ¯\\_(ツ)_/¯ sorry");
		}
		return true;
	}

	public static void registerEvents(EsEventListener listener) {
		eventListeners.add(listener);
	}

	// Setup Command Overloads

	public void sc(String name, EsToolsCommand ce) {
		server.registerCommand(name, ce);
		commands.put(name, ce);
		ce.onEnable();
	}

	public void sc(String name, EsToolsCommand ce, int minVer) {
		if (minecraftVersion.getMinor() >= minVer) {
            sc(name, ce);
			return;
        }
		sc(name, new WrongVersion(minVer));
	}
	
	public void sc(String name, EsToolsCommand ce, EsToolsTabCompleter tc) {
		sc(name, ce);
		if (tabCompleteEnabled) {
			server.setTabCompleter(name, tc);
		}
	}

	public void sc(String name, String perm, EsToolsCommand ce, EsToolsTabCompleter tc, int minMajor, int minMinor) {
		String versionName = minMajor + "." + minMinor;
		if (minecraftVersion.getMinor() > minMajor || (minecraftVersion.getMinor() == minMajor && minecraftVersion.getPatch() >= minMinor)) {
			if (tc == null) {
				sc(name, perm, ce);
				return;
			}

            sc(name, perm, ce, tc);
        } else sc(name, new WrongVersion(versionName), new WrongVersion(versionName));
	}

	public void sc(String name, String perm, EsToolsCommand ce) {
		sc(name, ce);
		server.setCommandPermission(name, "estools." + perm);
	}

	public void sc(String name, String perm, EsToolsCommand ce, int minVer) {
		if (minecraftVersion.getMinor() >= minVer) sc(name, perm, ce);
		else sc(name, perm, new WrongVersion(minVer));
	}

	public void sc(String name, String perm, EsToolsCommand ce, int minMinor, int minPatch) {
		String versionName = minMinor + "." + minPatch;
		if (minecraftVersion.isAtLeast(1, minMinor, minPatch)) sc(name, perm, ce);
		else sc(name, perm, new WrongVersion(versionName));
	}
	
	public void sc(String name, String perm, EsToolsCommand ce, EsToolsTabCompleter tc) {
		sc(name, perm, ce);
		if (tabCompleteEnabled) {
			server.setTabCompleter(name, tc);
		}
	}

	// Overriding to create more predictable behaviour between versions
	public EsToolsConfig getConfig() {
		return config;
	}

	@SuppressWarnings("unused")
    public static void asrt(boolean condition) {
		asrt(condition, "Condition is false");
	}

	// I'm sick of Java assertions not failing by default, so I made my own.
	public static void asrt(boolean condition, String msg) {
		assert condition : msg;

		// Asserts aren't enforced most of the time
        //noinspection ConstantValue
        if (condition) {
            return;
		}

		logger.severe("Assertion failed: " + msg);
		throw new AssertionError(msg);
	}

	public void saveConfig() {
		ConfigManager.save("config.yml", config);
	}
}

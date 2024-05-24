package net.serble.estools;

import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitHelper;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class EsToolsCommand implements CommandExecutor, EsToolsTabCompleter {

	public void onEnable() { }

	@Override
	public List<String> onTabComplete(EsCommandSender sender, Command command, String alias, String[] args) {
		if (Main.minecraftVersion.getMinor() < 7) {
			return new ArrayList<>();
		}

		String lArg = args[args.length - 1];
		return fixTabComplete(tabComplete(sender, args, lArg), lArg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return execute(BukkitHelper.fromBukkitCommandSender(sender), args);
	}

	public static List<String> fixTabComplete(List<String> inList, String arg) {
		final String argL = arg.toLowerCase();
		inList.removeIf(n -> !n.toLowerCase().startsWith(argL));

		return inList;
	}

	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		return new ArrayList<>();
	}

	public abstract boolean execute(EsCommandSender sender, String[] args);
	
	public static void send(EsCommandSender s, String m, Object... a) {
		if (Main.minecraftVersion.getMinor() > 1) {
			s.sendMessage(translate(m, a));
			return;
		}

		// Newlines don't work in 1.1 and 1.0
		// Send each message individually
		String[] lines = translate(m, a).split("\n");
		for (String line : lines) {
			s.sendMessage(line);
		}
	}
	
	public static String translate(String m, Object... a) {
		if (Main.minecraftVersion.getMinor() > 1) {
			return ChatColor.translateAlternateColorCodes('&', String.format(m, a));
		}

		// translateAlternateColorCodes doesn't exist in 1.1 and 1.0
		// do it manually
		return String.format(m, a).replace('&', '§');
	}
	
	public static boolean isNotPlayer(EsCommandSender sender, String usage, Object... a) {
		if (!(sender instanceof EsPlayer)) {
			send(sender, usage, a);
			return true;
		}
		return false;
	}
	
	public static boolean isNotPlayer(EsCommandSender sender) {
		if (!(sender instanceof Player)) {
			send(sender, "&cYou must be a player to run this command!");
			return true;
		}
		return false;
	}

	public static int tryParseInt(String obj, int defaultValue) {
		try {
			return Integer.parseInt(obj);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static double tryParseDouble(String obj, double defaultValue) {
		try {
			return Double.parseDouble(obj);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	// This is different from checkPerms because it doesn't display any messages
	public static boolean hasPerm(EsCommandSender sender, String perm) {
		return sender.hasPermission("estools." + perm);
	}
	
	public static boolean checkPerms(EsCommandSender sender, String perm) {
		if (!sender.hasPermission("estools." + perm)) {
			send(sender, "&cYou do not have permission to run this command.");
			return true;
		}
		return false;
	}
	
	public static String genUsage(String use) {
		return "&r&c&lUsage: &r&c" + use.replace("\n", "\n           ");
	}
	
	public static EsPlayer getPlayer(EsCommandSender sender, String name) {
		EsPlayer p = Main.server.getPlayer(name);
		
		if (p == null) {
			send(sender, "&cPlayer not found.");
		}

		return p;
	}
	
	public static boolean equalsOr(Object orig, Object... items) {
		for (Object item : items) {
			if (orig.equals(item)) {
				return true;
			}
		}
		
		return false;
	}

	public static String[] removeArgs(String[] args, int amount) {
		String[] output = new String[args.length - amount];

        if (args.length - amount >= 0) {
            System.arraycopy(args, amount, output, 0, args.length - amount);
        }

		return output;
	}

	public static String argsToString(String[] args, int skipAmount) {
		return String.join(" ", removeArgs(args, skipAmount));
	}

	public static double parseCoordinate(String coord, double playerLoc) {
		if (coord.startsWith("~")) {
			return tryParseDouble(coord.substring(1), 0) + playerLoc;
		}

		return tryParseDouble(coord, 0);
	}

	public static String locationToString(EsLocation loc) {
		return String.format("%d %d %d", Math.round(loc.getX()), Math.round(loc.getY()), Math.round(loc.getZ()));
	}
}

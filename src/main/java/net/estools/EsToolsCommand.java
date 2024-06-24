package net.estools;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class EsToolsCommand implements EsToolsTabCompleter {

	public void onEnable() { }

	@Override
	public List<String> onTabComplete(EsCommandSender sender, String[] args) {
		if (Main.minecraftVersion.getMinor() < 7) {
			return new ArrayList<>();
		}

		String lArg = args[args.length - 1];
		return fixTabComplete(tabComplete(sender, args, lArg), lArg);
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
		// This works for every Minecraft :)
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
		if (!(sender instanceof EsPlayer)) {
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

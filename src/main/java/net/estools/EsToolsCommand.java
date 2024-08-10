package net.estools;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

	/**
	 * Get tab complete values that start with the user's current input, ignoring case.
	 *
	 * @param inList The valid list of tab complete values.
	 * @param arg The user's current input (Should be args[args.length-1]).
	 * 
	 * @return The corrected tab complete values.
	 */
	public static List<String> fixTabComplete(List<String> inList, String arg) {
		final String argL = arg.toLowerCase();
		inList.removeIf(n -> !n.toLowerCase().startsWith(argL));

		return inList;
	}

	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		return new ArrayList<>();
	}

	public abstract boolean execute(EsCommandSender sender, String[] args);
	
	/** 
	 * Send a message to a {@link EsCommandSender}, accounting for colour codes and formatting.
	 * 
	 * @param s Who to send the message to.
	 * @param m The message to send (Colour codes should be written with the '&' symbol)
	 * @param a Format objects, to be used with {@link String#format(String, Object...)}
	 */
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

	/**
	 * Convert '&' colours to Minecraft formatted colour codes.
	 *
	 * @param m The message to convert (Colour codes should be written with the '&' symbol)
	 * @param a Format objects, to be used with {@link String#format(String, Object...)}
	 *
	 * @return The converted {@link String}.
	 */
	public static String translate(String m, Object... a) {
		// This works for every Minecraft :)
		return String.format(m, a).replace('&', '§');
	}

	/**
	 * Checks if a {@link EsCommandSender} is a {@link EsPlayer}, if they aren't
	 * then send the usage {@link String} and return true, otherwise do nothing and
	 * return false.
	 *
	 * @param sender The {@link EsCommandSender} to check.
	 * @param usage The usage message to send if they aren't a player, to be used with {@link EsToolsCommand#send(EsCommandSender, String, Object...)}.
	 * @param a The objects that will be used with the usage message and {@link String#format(String, Object...)}.
	 *
	 * @return True if the sender was not a player, otherwise False.
	 */
	public static boolean isNotPlayer(EsCommandSender sender, String usage, Object... a) {
		if (!(sender instanceof EsPlayer)) {
			send(sender, usage, a);
			return true;
		}
		return false;
	}

	/**
	 * Checks if a {@link EsCommandSender} is a {@link EsPlayer}, if they aren't
	 * then send an error message and return true, otherwise do nothing and
	 * return false.
	 *
	 * @param sender The {@link EsCommandSender} to check.
	 *
	 * @return True if the sender was not a player, otherwise False.
	 */
	public static boolean isNotPlayer(EsCommandSender sender) {
		if (!(sender instanceof EsPlayer)) {
			send(sender, "&cYou must be a player to run this command!");
			return true;
		}
		return false;
	}

	/**
	 * Attempts to parse an integer from a String and returns the parsed value if it's valid, otherwise
	 * returns the default value.
	 *
	 * @param obj The {@link String} to parse.
	 * @param defaultValue The value to return if the provided {@link String} is invalid.
	 *
	 * @return The parsed {@link int} or the default value if the {@link String} was invalid.
	 */
	public static int tryParseInt(String obj, int defaultValue) {
		try {
			return Integer.parseInt(obj);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Attempts to parse a double from a String and returns the parsed value if it's valid, otherwise
	 * returns the default value.
	 *
	 * @param obj The {@link String} to parse.
	 * @param defaultValue The value to return if the provided {@link String} is invalid.
	 *
	 * @return The parsed {@link double} or the default value if the {@link String} was invalid.
	 */
	public static double tryParseDouble(String obj, double defaultValue) {
		try {
			return Double.parseDouble(obj);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Gets whether the {@link EsCommandSender} has the permission node estools.PERM.
	 * <p>
	 * This is different from {@link EsToolsCommand#checkPerms(EsCommandSender, String)} because it doesn't display any messages
	 *
	 * @param sender The {@link EsCommandSender} to check the permission of.
	 * @param perm The permission node to check for.
	 *
	 * @return Whether the {@link EsCommandSender} has the permission node estools.PERM.
	 */
	public static boolean hasPerm(EsCommandSender sender, String perm) {
		return sender.hasPermission("estools." + perm);
	}

	/**
	 * Gets whether the {@link EsCommandSender} has the permission node estools.PERM
	 * and if they don't then send a permission error message.
	 * <p>
	 * This is different from {@link EsToolsCommand#hasPerm(EsCommandSender, String)} because it displays an error message
	 * if the permission node is not granted.
	 *
	 * @param sender The {@link EsCommandSender} to check the permission of.
	 * @param perm The permission node to check for.
	 *
	 * @return Whether the {@link EsCommandSender} DOES NOT have the permission node estools.PERM.
	 * In other words it returns true if a no permission message was displayed.
	 */
	public static boolean checkPerms(EsCommandSender sender, String perm) {
		if (!sender.hasPermission("estools." + perm)) {
			send(sender, "&cYou do not have permission to run this command.");
			return true;
		}
		return false;
	}

	/**
	 * Generates a usage {@link String} with the specified usage info. It adds a coloured <code>Usage: </code> prefix.
	 *
	 * @param use The usage of the command, should be <code>/command [args spec]</code>. DO NOT ADD COLOURS.
	 *
	 * @return The usage {@link String} to be sent to players.
	 *
	 * @implNote The return value will have colour codes not formatted, so it must be translated with {@link EsToolsCommand#translate(String, Object...)}
	 * or just sent with {@link EsToolsCommand#send(EsCommandSender, String, Object...)}.
	 */
	public static String genUsage(String use) {
		return "&r&c&lUsage: &r&c" + use.replace("\n", "\n           ");
	}

	/**
	 * Get a player from their name or UUID and send an error if one could not be found.
	 *
	 * @param name The name of the player
	 * @param sender The person to complain to if the player was not found.
	 *
	 * @return The found {@link EsPlayer} or null if they could not be found.
	 */
	public static EsPlayer getPlayer(EsCommandSender sender, String name) {
		EsPlayer p;
		try {
			UUID uuid = UUID.fromString(name);
			p = Main.server.getPlayer(uuid);
		} catch (IllegalArgumentException unused) {
			p = Main.server.getPlayer(name);
		}
		
		if (p == null) {
			send(sender, "&cPlayer not found.");
		}

		return p;
	}

	/**
	 * Checks if an object is equal to any of the other objects using {@link Object#equals(Object)}.
	 *
	 * @param orig The first object to compare. It cannot be null.
	 * @param items The objects to compare the first one with.
	 *
	 * @return Whether the orig {@link Object} was equal to any of the items.
	 */
	public static boolean equalsOr(Object orig, Object... items) {
		for (Object item : items) {
			if (orig.equals(item)) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Removes the first <code>amount</code> arguments from the array and return the result.
	 *
	 * @param args The array to modify.
	 * @param amount The amount of items to skip in the array.
	 *
	 * @return A new array with the first <code>amount</code> items removed.
	 */
	public static String[] removeArgs(String[] args, int amount) {
		String[] output = new String[args.length - amount];

        if (args.length - amount >= 0) {
            System.arraycopy(args, amount, output, 0, args.length - amount);
        }

		return output;
	}

	/**
	 * Join the arguments with a space character as the delimiter ignoring the first <code>skipAmount</code> arguments.
	 *
	 * @param args The list of arguments.
	 * @param skipAmount The amount of arguments to skip.
	 *
	 * @return The {@link String} containing all the arguments joined by a space character, without the
	 * first <code>skipAmount</code> arguments.
	 */
	public static String argsToString(String[] args, int skipAmount) {
		return String.join(" ", removeArgs(args, skipAmount));
	}

	/**
	 * Parse the specified coordinate {@link String}, accounting for relative coordinates based on
	 * the provided <code>playerLoc</code> argument.
	 * <p>
	 * This should be used for each coordinate, not all 3 of them together, for example
	 * <code>~10</code>, which would be <code>playerLoc</code> + 10. A value that isn't relative
	 * will just be the double parsed version of that value.
	 *
	 * @param coord The coordinate {@link String}.
	 * @param playerLoc The base position used to calculate relative coordinates.
	 *
	 * @return The final coordinate value.
	 */
	public static double parseCoordinate(String coord, double playerLoc) {
		if (coord.startsWith("~")) {
			return tryParseDouble(coord.substring(1), 0) + playerLoc;
		}

		return tryParseDouble(coord, 0);
	}

	/**
	 * Convert an {@link EsLocation} to a human-readable {@link String}. It will be in the
	 * format <code>X Y Z</code>.
	 *
	 * @param loc The location to stringify.
	 *
	 * @return The resulting {@link String}.
	 */
	public static String locationToString(EsLocation loc) {
		return String.format("%d %d %d", Math.round(loc.getX()), Math.round(loc.getY()), Math.round(loc.getZ()));
	}

	/**
	 * Convert an {@link EsLocation} to a human-readable {@link String}. It will be in the
	 * format <code>X Y Z (WORLD)</code>.
	 *
	 * @param loc The location to stringify.
	 *
	 * @return The resulting {@link String}.
	 */
	public static String worldLocationToString(EsLocation loc) {
		return String.format("%d %d %d (%s)", Math.round(loc.getX()), Math.round(loc.getY()), Math.round(loc.getZ()), loc.getWorld().getName());
	}
}

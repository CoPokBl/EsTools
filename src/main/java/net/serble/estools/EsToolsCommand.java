package net.serble.estools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class EsToolsCommand implements CommandExecutor, EsToolsTabCompleter {

	public void onEnable() {}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (Main.majorVersion < 7) {
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

	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		return new ArrayList<>();
	}
	
	public static void send(CommandSender s, String m, Object... a) {
		if (Main.majorVersion > 1) {
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
	
	public static void send(Player s, String m, Object... a) {
		s.sendMessage(translate(m, a));
	}
	
	public static String translate(String m, Object... a) {
		if (Main.majorVersion > 1) {
			return ChatColor.translateAlternateColorCodes('&', String.format(m, a));
		}

		// translateAlternateColorCodes doesn't exist in 1.1 and 1.0
		// do it manually
		return String.format(m, a).replace('&', '§');
	}
	
	public static boolean isNotPlayer(CommandSender sender, String usage, Object... a) {
		if (!(sender instanceof Player)) {
			send(sender, usage, a);
			return true;
		}
		return false;
	}
	
	public static boolean isNotPlayer(CommandSender sender) {
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
	public static boolean hasPerm(CommandSender sender, String perm) {
		return sender.hasPermission("estools." + perm);
	}
	
	public static boolean checkPerms(CommandSender sender, String perm) {
		if (!sender.hasPermission("estools." + perm)) {
			send(sender, "&cYou do not have permission to run this command.");
			return true;
		}
		return false;
	}
	
	public static String genUsage(String use) {
		return "&r&c&lUsage: &r&c" + use.replace("\n", "\n           ");
	}
	
	public static Player getPlayer(CommandSender sender, String name) {
		Player p = Bukkit.getPlayer(name);
		
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

	public static String locationToString(Location loc) {
		return String.format("%d %d %d", Math.round(loc.getX()), Math.round(loc.getY()), Math.round(loc.getZ()));
	}

	public static ItemStack getMainHand(Player p) {
		if (Main.majorVersion > 8) {
			return p.getInventory().getItemInMainHand();
		} else {
            //noinspection deprecation
            return p.getInventory().getItemInHand();
		}
	}

	public static void setMainHand(Player p, ItemStack is) {
		if (Main.majorVersion > 8) {
			p.getInventory().setItemInMainHand(is);
		} else {
            //noinspection deprecation
            p.getInventory().setItemInHand(is);
		}
	}

	public static double getMaxHealth(LivingEntity p) {
		if (Main.majorVersion > 8) {
			return Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
		} else {
			if (Main.majorVersion > 5) {
                //noinspection deprecation
                return p.getMaxHealth();
			}

			try {
				return (double)(int)LivingEntity.class.getMethod("getMaxHealth").invoke(p);
			} catch(Exception e) {
				Bukkit.getLogger().severe(e.toString());
				return 20d;
			}
		}
	}

	public static void setMaxHealth(LivingEntity p, double value) {
		if (Main.majorVersion > 8) {
			Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(value);
		} else {
			if (Main.majorVersion > 5) {
                //noinspection deprecation
                p.setMaxHealth(value);
				return;
			}

			try {
                //noinspection JavaReflectionMemberAccess
                LivingEntity.class.getMethod("setMaxHealth", int.class).invoke(p, (int)value);
			}
			catch (Exception ex) {
				Bukkit.getLogger().severe(ex.toString());
			}
		}
	}

	public static double getHealth(LivingEntity p) {
		if (Main.majorVersion > 5) {
			return p.getHealth();
		}

		try {
			return (double)(int)LivingEntity.class.getMethod("getHealth").invoke(p);
		}
		catch (Exception ex) {
			Bukkit.getLogger().severe(ex.toString());
			return 20d;
		}
	}

	public static void setHealth(LivingEntity p, double value) {
		if (Main.majorVersion > 5) {
			p.setHealth(value);
			return;
		}

		try {
            //noinspection JavaReflectionMemberAccess
            LivingEntity.class.getMethod("setHealth", int.class).invoke(p, (int)value);
		}
		catch (Exception ex) {
			Bukkit.getLogger().severe(ex.toString());
		}
	}

	public static String getEntityName(Entity p) {
		if (Main.majorVersion > 7) {
			return p.getName();
		}

		if (p instanceof Player) {
			return ((Player)p).getDisplayName();
		}

		if (p instanceof LivingEntity) {
			String name = ((LivingEntity)p).getCustomName();
			if (name != null) {
				return name;
			}
		}

		return "Entity";
	}

	public static Collection<? extends Player> getOnlinePlayers() {
		try {
			if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
				return Bukkit.getOnlinePlayers();
			}
			else {
				Player[] players = (Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null, new Object[0]);
				return Arrays.asList(players);
			}

		} catch (Exception e) {
			Bukkit.getLogger().severe(e.toString());
			return new ArrayList<>();
		}
	}
}

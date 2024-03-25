package net.serble.estools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CMD implements CommandExecutor, TabCompleter {

	public void onEnable() {}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (Main.version < 7) {
			return new ArrayList<String>();
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
		return new ArrayList<String>();
	}
	
	public static void s(CommandSender s, String m, Object... a) {
		s.sendMessage(t(m, a));
	}
	
	public static void s(Player s, String m, Object... a) {
		s.sendMessage(t(m, a));
	}
	
	public static String t(String m, Object... a) {
		return ChatColor.translateAlternateColorCodes('&', String.format(m, a));
	}
	
	public static boolean isNotPlayer(CommandSender sender, String usage, Object... a) {
		if (!(sender instanceof Player)) {
			s(sender, usage, a);
			return true;
		}
		return false;
	}
	
	public static boolean isNotPlayer(CommandSender sender) {
		if (!(sender instanceof Player)) {
			s(sender, "&cYou must be a player to run this command!");
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
	
	public static boolean checkPerms(CommandSender sender, String perm) {
		if (!sender.hasPermission("estools." + perm)) {
			s(sender, "&cYou do not have permission to run this command.");
			return true;
		}
		return false;
	}
	
	public static String genUsage(String use) {
		return "&r&c&lUsage: &r&c" + use;
	}
	
	public static Player getPlayer(CommandSender sender, String name) {

		Player p = Bukkit.getPlayer(name);
		
		if (p == null) {
			s(sender, "&cPlayer not found.");
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
		String[] outp = new String[args.length - amount];

		for (int i = amount; i < args.length; i++) {
			outp[i - amount] = args[i];
		}

		return outp;
	}

	public static String argsToString(String[] args, int amount) {
		StringBuilder outp = new StringBuilder();

		for (int i = amount; i < args.length; i++) {
			outp.append(args[i]);
		}

		return outp.toString();
	}

	public static ItemStack getMainHand(Player p) {
		if (Main.version > 8) {
			return p.getInventory().getItemInMainHand();
		} else {
			return p.getInventory().getItemInHand();
		}
	}

	public static void setMainHand(Player p, ItemStack is) {
		if (Main.version > 8) {
			p.getInventory().setItemInMainHand(is);
		} else {
			p.getInventory().setItemInHand(is);
		}
	}

	public static double getMaxHealth(LivingEntity p) {
		if (Main.version > 8) {
			return p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		} else {
			return p.getMaxHealth();
		}
	}

	public static void setMaxHealth(LivingEntity p, double value) {
		if (Main.version > 8) {
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
		} else {
			p.setMaxHealth(value);
		}
	}
}

package net.serble.estools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class CMD implements CommandExecutor, EsToolsTabCompleter {

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
		if (Main.version > 1) {
			s.sendMessage(t(m, a));
			return;
		}

		// Newlines don't work in 1.1 and 1.0
		// Send each message individually
		String[] lines = t(m, a).split("\n");
		for (String line : lines) {
			s.sendMessage(line);
		}
	}
	
	public static void s(Player s, String m, Object... a) {
		s.sendMessage(t(m, a));
	}
	
	public static String t(String m, Object... a) {
		if (Main.version > 1) {
			return ChatColor.translateAlternateColorCodes('&', String.format(m, a));
		}

		// translateAlternateColorCodes doesn't exist in 1.1 and 1.0
		// do it manually
		return String.format(m, a).replace('&', '§');
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
			if (Main.version > 5) {
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
		if (Main.version > 8) {
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
		} else {
			if (Main.version > 5) {
				p.setMaxHealth(value);
				return;
			}

			try {
				LivingEntity.class.getMethod("setMaxHealth", int.class).invoke(p, (int)value);
			}
			catch (Exception ex) {
				Bukkit.getLogger().severe(ex.toString());
			}
		}
	}

	public static double getHealth(LivingEntity p) {
		if (Main.version > 5) {
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
		if (Main.version > 5) {
			p.setHealth(value);
			return;
		}

		try {
			LivingEntity.class.getMethod("setHealth", int.class).invoke(p, (int)value);
		}
		catch (Exception ex) {
			Bukkit.getLogger().severe(ex.toString());
		}
	}

	public static String getEntityName(Entity p) {
		if (Main.version > 7) {
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
			if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
				return Bukkit.getOnlinePlayers();
			}
			else {
				Player[] players = (Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null, new Object[0]);
				return Arrays.asList(players);
			}

		} catch(Exception e) {
			Bukkit.getLogger().severe(e.toString());
			return new ArrayList<>();
		}
	}
}

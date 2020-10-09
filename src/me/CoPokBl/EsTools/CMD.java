package me.CoPokBl.EsTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public abstract class CMD implements CommandExecutor, TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
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
		String outp = "";

		for (int i = amount; i < args.length; i++) {
			outp += args[i];
		}

		return outp;
	}
}

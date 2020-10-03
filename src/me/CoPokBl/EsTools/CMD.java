package me.CoPokBl.EsTools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CMD {	
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
			s(sender, "&4You must be a player to run this command!");
			return true;
		}
		return false;
	}
	
	public static boolean checkPerms(CommandSender sender, String perm) {
		if (!sender.hasPermission("estools." + perm)) {
			s(sender, "&4You do not have permission to run this command.");
			return true;
		}
		return false;
	}
	
	public static String genUsage(String use) {
		return "&r&4&lUsage: &r&4" + use;
	}
	
	public static Player getPlayer(CommandSender sender, String name) {
		Player p = Bukkit.getPlayer(name);
		
		if (p == null) {
			s(sender, "&4Player not found.");
		}
			
		return p;
	}
}

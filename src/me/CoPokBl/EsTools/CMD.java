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
	
	public static boolean equalsOr(Object orig, Object... items) {
		for (Object item : items) {
			if (orig.equals(item)) {
				return true;
			}
		}
		
		return false;
	}
}

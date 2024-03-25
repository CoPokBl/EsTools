package net.serble.estools.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.serble.estools.Enchantments;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.serble.estools.CMD;

public class Ench extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			s(sender, genUsage("/ench <enchantment> [level] [player]"));
			return false;
		}
		
		ItemStack is;
		
		int level = 1;
		
		if (args.length > 1) {
			try {
				level = Integer.parseInt(args[1]);
			} catch (Exception e) {
				s(sender, genUsage("/ench <enchantment> [level] [player]"));
				return false;
			}
		}
		
		if (args.length <= 2) {
			if (isNotPlayer(sender, genUsage("/ench <enchantment> <level> <player>")))
				return false;
			
			is = getMainHand(((Player) sender));
		} else {
			Player p = getPlayer(sender, args[2]);
			
			if (p == null)
				return false;
			
			is = getMainHand(p);
		}
		
		Enchantment ench;
		
		try {
			if (Main.version > 12)
				ench = Enchantment.getByKey(NamespacedKey.minecraft(args[0].toLowerCase()));
			else
				ench = Enchantments.getByName(args[0].toLowerCase());
		} catch (IllegalArgumentException e) {
			s(sender, genUsage("/ench <enchantment> [level] [player]"));
			return true;
		}
		
		
		if (ench != null && is != null) {
			is.addUnsafeEnchantment(ench, level);
			s(sender, "&aEnchantment &6%s&a at level &6%s&a was added!", args[0].toLowerCase(), level);
		}
		else {
			s(sender, genUsage("/ench <enchantment> [level] [player]"));
		}
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<String>();
		
		switch (args.length) {
			case 1:
				if (Main.version > 12) {
					for (Enchantment e : Enchantment.values()) {
						tab.add(e.getKey().getKey());
					}
				} else {
					for (Map.Entry<String, Enchantment> e : Enchantments.entrySet()) {
						tab.add(e.getKey());
					}
				}

				break;
				
			case 3:
				for (Player p : getOnlinePlayers()) {
					tab.add(p.getName());
				}
				break;
		}
		
		return tab;
	}

}

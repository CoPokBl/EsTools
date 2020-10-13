package me.CoPokBl.EsTools.Commands;

import java.util.ArrayList;
import java.util.List;

import me.CoPokBl.EsTools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import me.CoPokBl.EsTools.PlayerCommand;

public class Fix extends PlayerCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p;
		
		if (args.length < 2) {
			if (isNotPlayer(sender, genUsage("/fix <hand/offhand/helmet/chestplate/leggings/boots> <player>")))
				return false;
			
			p = (Player) sender;
		} else {
			p = getPlayer(sender, args[1]);
			
			if (p == null)
				return false;
		}
		
		PlayerInventory pInv = p.getInventory();
		ItemStack is = getMainHand(p);
		
		if (args.length > 0) {
			switch (args[0].toLowerCase()) {					
				case "offhand":
				    if (Main.version > 8) {
                        is = pInv.getItemInOffHand();
                    }
					break;
					
				case "helmet":
					is = pInv.getHelmet();
					break;
					
				case "chestplate":
					is = pInv.getChestplate();
					break;
					
				case "leggings":
					is = pInv.getLeggings();
					break;
					
				case "boots":
					is = pInv.getBoots();
					break;
			}
		}
		

		if (is == null)
			 return true;

		if (Main.version > 12) {
			ItemMeta im = is.getItemMeta();

			if (im == null) return true;

			((Damageable) im).setDamage(0);

			is.setItemMeta(im);
		} else {
			is.setDurability((short) 0);
		}
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<String>();
		
		if (args.length == 1) {
			tab.add("hand"); tab.add("helmet"); tab.add("chestplate");
			tab.add("leggings"); tab.add("boots");

			if (Main.version > 8) {
                tab.add("offhand");
            }
		}
		else if (args.length == 2) {
			return super.tabComplete(sender, args, lArg);
		}
		
		return tab;
	}

}

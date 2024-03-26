package net.serble.estools.Commands;

import java.util.ArrayList;
import java.util.List;

import net.serble.estools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import net.serble.estools.PlayerCommand;

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

		boolean all = false;
		
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

				case "all":
					all = true;
					break;
			}
		}
		

		if (all) {
			ItemStack[] contents = p.getInventory().getContents();

			for (ItemStack i : contents) {
				Repair(i);
			}

			p.getInventory().setContents(contents);
		} else {
            Repair(is);
        }

		s(sender, "&aRepaired &6%s's &aitem(s)!", getEntityName(p));
		return true;
	}

	private void Repair(ItemStack is) {
		if (is == null) {
			return;
		}

		if (Main.version > 12) {
			ItemMeta im = is.getItemMeta();

			if (im == null) return;

			((Damageable) im).setDamage(0);

			is.setItemMeta(im);
		} else {
			//noinspection deprecation
			is.setDurability((short) 0);
		}

	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<String>();
		
		if (args.length == 1) {
			tab.add("hand"); tab.add("helmet"); tab.add("chestplate");
			tab.add("leggings"); tab.add("boots"); tab.add("all");

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

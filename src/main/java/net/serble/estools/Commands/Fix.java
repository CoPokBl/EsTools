package net.serble.estools.Commands;

import net.serble.estools.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Fix extends CMD {

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

		return true;
	}

	private void Repair(ItemStack is) {
		if (is == null)
			return;

		//noinspection deprecation
		is.setDurability((short) 0);

	}

}

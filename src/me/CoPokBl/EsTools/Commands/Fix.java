package me.CoPokBl.EsTools.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import me.CoPokBl.EsTools.CMD;

public class Fix extends CMD {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (checkPerms(sender, "fix"))
			return false;
		
		Player p;
		
		if (args.length == 0) {
			if (isNotPlayer(sender, genUsage("/fix <player>")))
				return false;
			
			p = (Player) sender;
		} else {
			p = getPlayer(sender, args[0]);
			
			if (p == null)
				return false;
		}
		
		ItemMeta im = p.getInventory().getItemInMainHand().getItemMeta();
		((Damageable) im).setDamage(0);
		
		p.getInventory().getItemInMainHand().setItemMeta(im);
		
		return true;
	}

}

package net.serble.estools.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.serble.estools.Enchantments;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;

import net.serble.estools.EsToolsCommand;

public class Ench extends EsToolsCommand {
	private static final String usage = genUsage("/ench <enchantment> [level] [player]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		EsItemStack is;
		
		int level = 1;
		if (args.length > 1) {
			try {
				level = Integer.parseInt(args[1]);
			} catch (Exception e) {
				send(sender, usage);
				return false;
			}
		}
		
		if (args.length <= 2) {
			if (isNotPlayer(sender, usage)) {
                return false;
            }
			
			is = ((EsPlayer) sender).getMainHand();
		} else {
			EsPlayer p = getPlayer(sender, args[2]);
			
			if (p == null) {
                return false;
            }
			
			is = p.getMainHand();
		}
		
		if (Main.server.doesEnchantmentExist(args[0].toLowerCase()) && is != null) {
			is.addEnchantment(args[0].toLowerCase(), level);
			send(sender, "&aEnchantment &6%s&a at level &6%s&a was added!", args[0].toLowerCase(), level);
		}
		else {
			send(sender, usage);
		}
		return true;
	}
	
	@Override
	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();
		
		switch (args.length) {
			case 1:
				if (Main.minecraftVersion.getMinor() > 12) {
                    for (Enchantment e : Registry.ENCHANTMENT) {
						tab.add(e.getKey().getKey());
					}
				} else {
					for (Map.Entry<String, Enchantment> e : Enchantments.entrySet()) {
						tab.add(e.getKey());
					}
				}

				break;
				
			case 3:
				for (EsPlayer p : Main.server.getOnlinePlayers()) {
					tab.add(p.getName());
				}
				break;
		}
		
		return tab;
	}

}

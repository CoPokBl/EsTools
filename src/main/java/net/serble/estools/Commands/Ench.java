package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEnchantment;
import net.serble.estools.ServerApi.EsMaterial;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ench extends EsToolsCommand {
	private static final String usage = genUsage("/ench <enchantment> [level] [player]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}

		EsEnchantment enchantment = EsEnchantment.fromKey(args[0]);
		if (enchantment == null) {
			send(sender, "&cEnchantment does not exist!");
			return false;
		}
		
		int level = 1;
		if (args.length > 1) {
			try {
				level = Integer.parseInt(args[1]);
			} catch (Exception e) {
				send(sender, usage);
				return false;
			}
		}

		EsItemStack is;
		if (args.length <= 2) {
			if (isNotPlayer(sender, usage)) {
                return false;
            }
			
			is = ((EsPlayer) sender).getMainHand();

			if (is == null || Objects.equals(is.getType(), EsMaterial.createUnchecked("AIR"))) {
				send(sender, "&cYou must be holding an item to enchant it");
				return false;
			}
		} else {
			EsPlayer p = getPlayer(sender, args[2]);
			
			if (p == null) {
                return false;
            }
			
			is = p.getMainHand();

			if (is == null || Objects.equals(is.getType(), EsMaterial.createUnchecked("AIR"))) {
				send(sender, "&c" + p.getName() + " isn't holding an item");
				return false;
			}
		}

		is.addEnchantment(enchantment, level);
		send(sender, "&aEnchantment &6%s&a at level &6%s&a was added!", args[0].toLowerCase(), level);
		return true;
	}
	
	@Override
	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();
		
		switch (args.length) {
			case 1:
				for (EsEnchantment enchantment : Main.server.getEnchantments()) {
					tab.add(enchantment.getKey());
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

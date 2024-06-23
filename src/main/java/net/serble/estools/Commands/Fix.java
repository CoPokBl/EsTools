package net.serble.estools.Commands;

import net.serble.estools.Main;
import net.serble.estools.PlayerCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import net.serble.estools.ServerApi.Interfaces.EsPlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class Fix extends PlayerCommand {
	private static final String usage = genUsage("/fix [hand/offhand/helmet/chestplate/leggings/boots/all] [player]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		EsPlayer p;
		
		if (args.length < 2) {
			if (isNotPlayer(sender, usage)) {
                return false;
            }
			
			p = (EsPlayer) sender;
		} else {
			p = getPlayer(sender, args[1]);
			
			if (p == null) {
                return false;
            }
		}
		
		EsPlayerInventory pInv = p.getInventory();
		EsItemStack is = p.getMainHand();

		boolean all = false;
		
		if (args.length > 0) {
			switch (args[0].toLowerCase()) {					
				case "offhand":
				    if (Main.minecraftVersion.getMinor() > 8) {
                        is = pInv.getOffHand();
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
			EsItemStack[] contents = p.getInventory().getContents();
			for (EsItemStack i : contents) {
				repair(i);
			}

			p.getInventory().setContents(contents);
		} else {
            repair(is);
        }

		send(sender, "&aRepaired &6%s's &aitem(s)!", p.getName());
		return true;
	}

	public static void repair(EsItemStack is) {
		if (is == null) {
			return;
		}

		is.setDamage(0);
	}

	@Override
	public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
		List<String> tab = new ArrayList<>();
		
		if (args.length == 1) {
			tab.add("hand"); tab.add("helmet"); tab.add("chestplate");
			tab.add("leggings"); tab.add("boots"); tab.add("all");

			if (Main.minecraftVersion.getMinor() > 8) {
                tab.add("offhand");
            }
		} else if (args.length == 2) {
			return super.tabComplete(sender, args, lArg);
		}
		
		return tab;
	}

}

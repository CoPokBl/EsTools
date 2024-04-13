package net.serble.estools.Commands.Give;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.serble.estools.EsToolsCommand;

public class GiveItem extends EsToolsCommand {
	private static final String usage = genUsage("/i <item> [amount]");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isNotPlayer(sender)) {
			return false;
		}

		ItemStack item;
		try {
			item = Give.parseArgs(args);
		} catch (IllegalArgumentException e) {
			send(sender, usage);
			return false;
		}

		Player p = (Player) sender;
		
		if (item != null) {
            p.getInventory().addItem(item);
			send(sender, "&aGave &6%s", item.getType().name());
        } else {
            send(sender, "&cItem &6%s&c not found", args[0]);
        }
		
		return true;
	}
}

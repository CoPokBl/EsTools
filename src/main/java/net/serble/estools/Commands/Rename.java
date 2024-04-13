package net.serble.estools.Commands;

import net.serble.estools.MetaHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.serble.estools.EsToolsCommand;

public class Rename extends EsToolsCommand {
	public static final String usage = genUsage("/rename <name>");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		Player p = (Player)sender;
		ItemStack is = getMainHand(p);

		if (args.length == 0) {
			MetaHandler.renameItem(is, "");
			send(sender, "&aRemoved item name");
			return false;
		}

		String name = translate("&r" + String.join(" ", args));
		MetaHandler.renameItem(is, name);
		send(sender, "&aItem renamed to &6%s", name);
		return true;
	}
}

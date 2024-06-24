package net.estools.Commands.Give;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class SetHandItem extends EsToolsCommand {
	private static final String usage = genUsage("/h <item> [amount]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }

		EsItemStack item;
		try {
			item = Give.parseArgs(args);
		} catch (IllegalArgumentException e) {
			send(sender, usage);
			return false;
		}
		
		EsPlayer p = (EsPlayer) sender;
		
		if (item != null) {
			p.setMainHand(item);
			send(sender, "&aGave &6%s", item.getType());
        } else {
            send(sender, "&cItem &6%s&c not found", args[0]);
        }
		
		return true;
	}

}

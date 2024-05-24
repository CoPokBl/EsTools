package net.serble.estools.Commands;

import net.serble.estools.MetaHandler;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class Rename extends EsToolsCommand {
	public static final String usage = genUsage("/rename [name]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		EsPlayer p = (EsPlayer) sender;
		EsItemStack is = p.getMainHand();

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

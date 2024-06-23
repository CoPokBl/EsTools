package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
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

		EsItemMeta meta = is.getItemMeta();

		if (meta == null) {
			send(sender, "&cYou must be holding an item");
			return false;
		}

		if (args.length == 0) {
			meta.setDisplayName("");
			is.setItemMeta(meta);
			send(sender, "&aRemoved item name");
			return false;
		}

		String name = translate("&r" + String.join(" ", args));
		meta.setDisplayName(name);
		is.setItemMeta(meta);
		send(sender, "&aItem renamed to &6%s", name);
		return true;
	}
}

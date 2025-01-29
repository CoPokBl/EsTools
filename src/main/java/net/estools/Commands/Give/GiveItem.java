package net.estools.Commands.Give;

import net.estools.EsToolsCommand;
import net.estools.Main;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class GiveItem extends EsToolsCommand {
	private final boolean isSetHandItem;
	private final String usage;
	private final String usage12;

    public GiveItem(boolean isSetHandItem) {
        this.isSetHandItem = isSetHandItem;
		usage = genUsage("/" + (isSetHandItem ? 'h' : 'i') + " <item> [amount] [damage]");
		usage12 = genUsage("/" + (isSetHandItem ? 'h' : 'i') + " <item> [amount] [damage/variant]");
    }

    @Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
			return false;
		}

		EsItemStack item;
		try {
			item = Give.parseArgs(args);
		} catch (IllegalArgumentException e) {
			send(sender, Main.minecraftVersion.getMinor() > 12 ? usage : usage12);
			return false;
		}

		EsPlayer p = (EsPlayer) sender;
		
		if (item != null) {
			if (isSetHandItem) {
				p.setMainHand(item);
			} else {
				p.getInventory().addItemOrDrop(item, p.getLocation().add(-p.getWidth(), 0.0, -p.getWidth()));
			}
			send(sender, "&aGave &6%s", item.getType());
        } else {
            send(sender, "&cItem &6%s&c not found", args[0]);
        }
		
		return true;
	}
}

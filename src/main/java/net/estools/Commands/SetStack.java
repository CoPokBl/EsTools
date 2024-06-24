package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class SetStack extends EsToolsCommand {
	private static final String usage = genUsage("/setstack <amount>");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		int amount;
		try {
			amount = Integer.parseInt(args[0]);
		} catch (Exception e) {
			send(sender, usage);
			return false;
		}

		((EsPlayer)sender).getMainHand().setAmount(amount);
		send(sender, "&aSet stack size to &6%d", amount);
		return true;
	}

}

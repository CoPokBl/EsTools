package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.PlayerCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class SetSaturation extends PlayerCommand {
	private static final String usage = genUsage("/setsaturation <amount> [player]");

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (args.length == 0) {
			send(sender, usage);
			return false;
		}
		
		EsPlayer p;
		if (args.length > 1) {
			p = EsToolsCommand.getPlayer(sender, args[1]);
			
			if (p == null) {
                return false;
            }
		} else {
			if (isNotPlayer(sender)) {
                return false;
            }
			
			p = (EsPlayer) sender;
		}

		float saturation;
		try {
			saturation = Float.parseFloat(args[0]);
		} catch (Exception e) {
			send(sender, usage);
			return false;
		}
		
		if (saturation < 0) {
			send(sender, "&cCannot set saturation to less than 0");
			return false;
		}

		float maxSaturation = 20f;
		if (maxSaturation < saturation) {
			saturation = maxSaturation;
		}

		p.setSaturation(saturation);

		if (args.length > 1) {
			send(sender, "&aSet saturation for &6%s&a for &6%s", p.getName(), String.valueOf(saturation));
		} else {
			send(sender, "&aSet saturation to &6%s", String.valueOf(saturation));
		}
		return true;
	}
}

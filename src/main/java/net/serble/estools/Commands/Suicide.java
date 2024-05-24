package net.serble.estools.Commands;

import net.serble.estools.PlayerCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;

public class Suicide extends PlayerCommand {

	@Override
	public boolean execute(EsCommandSender sender, String[] args) {
		if (isNotPlayer(sender)) {
            return false;
        }
		
		EsLivingEntity p = (EsLivingEntity) sender;

		p.setHealth(0);
		send(sender, "&aRest in peace");
		return true;
	}
}

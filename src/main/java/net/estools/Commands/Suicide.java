package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsLivingEntity;

public class Suicide extends EsToolsCommand {

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

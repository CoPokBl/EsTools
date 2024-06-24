package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class Midnight extends EsToolsCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        ((EsPlayer)sender).getWorld().setTime(18000);
        send(sender, "&aSet time to &6midnight");
        return true;
    }

}


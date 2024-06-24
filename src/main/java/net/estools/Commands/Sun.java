package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.Interfaces.EsWorld;

public class Sun extends EsToolsCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        EsWorld world = ((EsPlayer) sender).getWorld();
        world.setStorming(false);
        world.setThundering(false);

        send(sender, "&aSet weather to &6clear");
        return true;
    }
}


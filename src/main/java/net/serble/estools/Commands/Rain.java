package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import net.serble.estools.ServerApi.Interfaces.EsWorld;

public class Rain extends EsToolsCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        EsWorld world = ((EsPlayer) sender).getWorld();
        world.setStorming(true);
        world.setThundering(false);

        send(sender, "&aSet weather to &6rain");
        return true;
    }
}


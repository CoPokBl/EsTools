package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.EsLocation;

public class Top extends EsToolsCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        EsPlayer player = (EsPlayer) sender;
        EsLocation location = player.getLocation();
        EsLocation highestBlockLocation = location.getWorld().getHighestBlockAt(location).getLocation();

        player.teleport(highestBlockLocation);
        send(player, "&aTeleported to the highest block at your location!");

        return true;
    }
}

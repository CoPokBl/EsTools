package net.estools.Commands;

import net.estools.PlayerCommand;
import net.estools.ServerApi.Interfaces.EsBlock;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Position;

public class Top extends PlayerCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        EsPlayer player = null;
        if (args.length == 0) {
            if (isNotPlayer(sender)) {
                return false;
            }
            player = (EsPlayer) sender;
        } else if (args.length == 1) {
            player = getPlayer(sender, args[0]);
        } else {
            send(sender, "&cUsage: /top [player]");
        }

        if (player == null) {
            return false;
        }

        EsLocation location = player.getLocation();
        EsBlock block = location.getWorld().getHighestBlockAt(location.getBlockX(), location.getBlockZ());
        if (block == null) {
            send(sender, "&cNo block could be found");
            return false;
        }

        Position highestBlockPos = block.getLocation();

        EsLocation newLoc = new EsLocation(location.getWorld(), location.getDirection(), highestBlockPos.add(0.5, 1, 0.5));
        newLoc.setPitch(location.getPitch());
        newLoc.setYaw(location.getYaw());
        player.teleport(newLoc);
        send(sender, "&aTeleported to the highest block at your location!");
        return true;
    }
}

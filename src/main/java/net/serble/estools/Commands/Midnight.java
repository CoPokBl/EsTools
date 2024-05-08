package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Midnight extends EsToolsCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        ((Player)sender).getWorld().setTime(18000);
        send(sender, "&aSet time to &6midnight");
        return true;
    }

}


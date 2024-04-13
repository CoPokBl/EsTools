package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Night extends EsToolsCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        ((Player)sender).getWorld().setTime(13000);
        send(sender, "&aSet time to &6night");
        return true;
    }

}


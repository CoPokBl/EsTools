package net.serble.estools.Commands;

import net.serble.estools.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Night extends CMD {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotPlayer(sender))
            return true;

        ((Player)sender).getWorld().setTime(13000);
        s(sender, "&aSet time to &6night");
        return true;
    }

}


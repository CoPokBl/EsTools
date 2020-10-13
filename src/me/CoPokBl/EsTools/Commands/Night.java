package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Night extends CMD {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotPlayer(sender))
            return true;

        ((Player)sender).getWorld().setTime(13000);
        return true;
    }

}


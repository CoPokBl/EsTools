package me.CoPokBl.EsTools.Commands.GameMode;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class gmc extends GM {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        setGamemode(sender, s, strings, GameMode.CREATIVE);
        return true;
    }
}

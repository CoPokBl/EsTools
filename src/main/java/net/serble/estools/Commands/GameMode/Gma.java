package net.serble.estools.Commands.GameMode;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Gma extends GameModeCommand {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        setGamemode(sender, s, strings, GameMode.ADVENTURE);
        return true;
    }
}

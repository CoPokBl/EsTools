package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WrongVersion extends CMD {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        s(sender, "&cYou cannot do this command in version 1.%s", Main.version);
        return true;
    }
}


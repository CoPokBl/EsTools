package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WrongVersion extends CMD {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        s(sender, "&cYou cannot do this command in version 1.%s", Main.version);
        return true;
    }
}


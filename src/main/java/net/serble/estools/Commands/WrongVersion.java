package net.serble.estools.Commands;

import net.serble.estools.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WrongVersion extends CMD {
    private final int minVersion;

    public WrongVersion(int minVersion) {
        this.minVersion = minVersion;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        s(sender, "&cThis command requires Minecraft to be at least 1.%s", minVersion);
        return true;
    }
}


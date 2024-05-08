package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Thunder extends EsToolsCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        World world = ((Player) sender).getWorld();
        world.setStorm(true);
        world.setThundering(true);

        send(sender, "&aSet weather to &6thunder");
        return true;
    }
}


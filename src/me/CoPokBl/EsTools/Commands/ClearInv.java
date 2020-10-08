package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.PlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInv extends PlayerCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p;

        if (args.length == 0) {
            if (isNotPlayer(sender, genUsage("/ci <player>")))
                return true;

            p = (Player)sender;

            s(sender, "&aCleared Inventory!");
        } else {
            p = getPlayer(sender, args[0]);

            if (p == null)
                return true;

            s(sender, "&aCleared Inventory of &6%s", p.getName());
        }

        p.getInventory().clear();

        return true;
    }
}

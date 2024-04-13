package net.serble.estools.Signs;

import net.serble.estools.Commands.Fix;
import net.serble.estools.EsToolsCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Repair extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        if (!takeMoney(lines[1], p)) {
            return;
        }

        ItemStack item = EsToolsCommand.getMainHand(p);
        Fix.repair(item);
    }
}

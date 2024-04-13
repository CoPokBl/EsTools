package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Disposal extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        p.openInventory(Bukkit.createInventory(null, 54, EsToolsCommand.translate("&cDisposal")));
    }
}

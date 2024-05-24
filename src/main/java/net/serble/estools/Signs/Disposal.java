package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class Disposal extends SignType {
    
    @Override
    public void run(EsPlayer p, String[] lines) {
        p.openInventory(Main.server.createInventory(null, 54, EsToolsCommand.translate("&cDisposal")));
    }
}

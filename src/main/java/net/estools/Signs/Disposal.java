package net.estools.Signs;

import net.estools.EsToolsCommand;
import net.estools.Main;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class Disposal extends SignType {
    
    @Override
    public void run(EsPlayer p, String[] lines) {
        p.openInventory(Main.server.createInventory(null, 54, EsToolsCommand.translate("&cDisposal")));
    }
}

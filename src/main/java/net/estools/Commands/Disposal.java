package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.Main;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsInventory;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class Disposal extends EsToolsCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        EsInventory inv = Main.server.createInventory(null, 54, "Disposal");
        ((EsPlayer) sender).openInventory(inv);
        return true;
    }
}


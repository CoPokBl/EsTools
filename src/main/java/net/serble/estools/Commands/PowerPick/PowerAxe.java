package net.serble.estools.Commands.PowerPick;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;

public class PowerAxe extends EsToolsCommand {
    private static EsItemStack powerItem;

    public static void init() {
        if (Main.minecraftVersion.getMinor() > 15) {
            powerItem = Main.server.createItemStack("NETHERITE_AXE", 1);
        }
        else {
            powerItem = Main.server.createItemStack("DIAMOND_AXE", 1);
        }

        PowerTool.setupItem(powerItem, "DIG_SPEED");
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        PowerTool.cmd(sender, powerItem);
        return true;
    }
}

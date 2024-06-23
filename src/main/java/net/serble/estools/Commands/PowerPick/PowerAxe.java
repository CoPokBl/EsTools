package net.serble.estools.Commands.PowerPick;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEnchantment;
import net.serble.estools.ServerApi.EsMaterial;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;

public class PowerAxe extends EsToolsCommand {
    private static EsItemStack powerItem;

    public static void init() {
        if (Main.minecraftVersion.getMinor() > 15) {
            powerItem = Main.server.createItemStack(EsMaterial.fromKey("NETHERITE_AXE"), 1);
        }
        else {
            powerItem = Main.server.createItemStack(EsMaterial.fromKey("DIAMOND_AXE"), 1);
        }

        PowerTool.setupItem(powerItem, EsEnchantment.createUnchecked("efficiency"));
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        PowerTool.cmd(sender, powerItem);
        return true;
    }
}

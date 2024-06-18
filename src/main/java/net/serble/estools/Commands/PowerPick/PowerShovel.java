package net.serble.estools.Commands.PowerPick;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEnchantment;
import net.serble.estools.ServerApi.EsMaterial;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;

public class PowerShovel extends EsToolsCommand {
    private static EsItemStack powerItem;

    public static void init() {
        if (Main.minecraftVersion.getMinor() > 15) {
            powerItem = Main.server.createItemStack(EsMaterial.fromKey("NETHERITE_SHOVEL"), 1);
        } else if (Main.minecraftVersion.getMinor() > 12) {
            powerItem = Main.server.createItemStack(EsMaterial.fromKey("DIAMOND_SHOVEL"), 1);
        } else {
            powerItem = Main.server.createItemStack(EsMaterial.fromKey("DIAMOND_SPADE"), 1);
        }

        PowerTool.setupItem(powerItem, EsEnchantment.createUnchecked("efficiency"));
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        PowerTool.cmd(sender, powerItem);
        return true;
    }
}

package net.estools.Commands.PowerPick;

import net.estools.EsToolsCommand;
import net.estools.Main;
import net.estools.ServerApi.EsEnchantment;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsItemStack;

public class PowerSword extends EsToolsCommand {
    private static EsItemStack powerPick;

    public static void init() {
        if (Main.minecraftVersion.getMinor() > 12) {
            powerPick = Main.server.createItemStack(EsMaterial.fromKey("SALMON"), 1);
        } else {
            powerPick = Main.server.createItemStack(EsMaterial.fromKey("RAW_FISH"), 1);
        }

        PowerTool.setupItem(powerPick, EsEnchantment.createUnchecked("sharpness"));
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        PowerTool.cmd(sender, powerPick);
        return true;
    }
}

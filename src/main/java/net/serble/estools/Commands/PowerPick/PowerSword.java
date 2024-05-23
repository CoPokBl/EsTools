package net.serble.estools.Commands.PowerPick;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;

public class PowerSword extends EsToolsCommand {
    private static EsItemStack powerPick;

    public static void init() {
        if (Main.minecraftVersion.getMinor() > 12) {
            powerPick = Main.server.createItemStack("SALMON", 1);
        } else {
            powerPick = Main.server.createItemStack("RAW_FISH", 1);
        }

        PowerTool.setupItem(powerPick, "DAMAGE_ALL");
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        PowerTool.cmd(sender, powerPick);
        return true;
    }
}

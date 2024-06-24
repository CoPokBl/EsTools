package net.estools.Commands.PowerPick;

import net.estools.EsToolsCommand;
import net.estools.Main;
import net.estools.ServerApi.EsEnchantment;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsItemMeta;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class PowerTool {
    public static void init() {
        PowerPick.init();
        PowerAxe.init();
        PowerShovel.init();
        PowerHoe.init();
        PowerSword.init();
    }

    public static void setupItem(EsItemStack item, EsEnchantment enchantment) {
        item.addEnchantment(enchantment, 32767);

        if (Main.minecraftVersion.getMinor() > 10) {
            EsItemMeta im = item.getItemMeta();
            im.setUnbreakable(true);
            item.setItemMeta(im);
        } else {
            item.addEnchantment(EsEnchantment.createUnchecked("unbreaking"), 32767);
        }
    }

    public static void cmd(EsCommandSender sender, EsItemStack pp) {
        if (EsToolsCommand.isNotPlayer(sender)) {
            return;
        }

        EsPlayer p = (EsPlayer) sender;

        p.getInventory().addItem(pp);
        EsToolsCommand.send(sender, "&aThere you go!");
    }
}

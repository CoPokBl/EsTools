package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.EsItemFlag;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsItemMeta;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.Objects;

/**
 * /hideflags seems to have reduced functionality in 1.21.
 */
public class HideFlags extends EsToolsCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        EsItemStack is = ((EsPlayer) sender).getMainHand();

        if (is == null || Objects.equals(is.getType(), EsMaterial.createUnchecked("AIR")) || is.getItemMeta() == null) {
            send(sender, "&cMust be a damageable item");
            return false;
        }

        EsItemMeta im = is.getItemMeta();
        String un;

        if (!im.getItemFlags().isEmpty()) {
            im.removeItemFlags(EsItemFlag.values());
            un = "&aRemoved Item Flags";
        } else {
            im.addItemFlags(EsItemFlag.values());
            un = "&aAdded Item Flags";
        }

        is.setItemMeta(im);
        send(sender, un);
        return true;
    }
}

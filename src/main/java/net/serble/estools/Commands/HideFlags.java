package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.ServerApi.EsItemFlag;
import net.serble.estools.ServerApi.EsMaterial;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.Objects;

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

package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEnchantment;
import net.serble.estools.ServerApi.EsMaterial;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemMeta;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.Objects;

public class SetUnbreakable extends EsToolsCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        EsItemStack item = ((EsPlayer) sender).getMainHand();
        if (item == null || Objects.equals(item.getType(), EsMaterial.createUnchecked("AIR")) || (Main.minecraftVersion.getMinor() > 10 && item.getItemMeta() == null)) {
            send(sender, "&cMust be a damageable item");
            return false;
        }

        String message = "&aSet item to &6Breakable!";

        if (Main.minecraftVersion.getMinor() > 10) {
            EsItemMeta itemMeta = item.getItemMeta();
            itemMeta.setUnbreakable(!itemMeta.isUnbreakable());
            item.setItemMeta(itemMeta);

            if (itemMeta.isUnbreakable()) {
                message = "&aSet item to &6Unbreakable!";
            }
        } else {
            if (item.getEnchantments().keySet().stream()
                    .anyMatch(c -> c != null && c.getKey().equalsIgnoreCase("unbreaking"))) {
                item.removeEnchantment(EsEnchantment.createUnchecked("unbreaking"));
            } else {
                item.addEnchantment(EsEnchantment.createUnchecked("unbreaking"), 32767);
                message = "&aSet item to &6Unbreakable!";
            }
        }

        send(sender, message);
        return true;
    }
}

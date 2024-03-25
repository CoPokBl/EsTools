package net.serble.estools.Commands;

import net.serble.estools.CMD;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HideFlags extends CMD {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotPlayer(sender))
            return true;

        ItemStack is = getMainHand((Player) sender);

        if (is == null || is.getType() == Material.AIR || is.getItemMeta() == null) {
            s(sender, "&cMust be a damageable item");
            return true;
        }

        ItemMeta im = is.getItemMeta();

        String un;

        if (im.getItemFlags().size() >= ItemFlag.values().length) {
            im.removeItemFlags(ItemFlag.values());
            un = "&aRemoved Item Flags";
        } else {
            im.addItemFlags(ItemFlag.values());
            un = "&aAdded Item Flags";
        }

        is.setItemMeta(im);

        s(sender, un);
        return true;
    }

}

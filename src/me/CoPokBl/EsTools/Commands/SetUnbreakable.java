package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetUnbreakable extends CMD {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotPlayer(sender))
            return true;

        ItemStack is = ((Player)sender).getInventory().getItemInMainHand();

        if (is == null || is.getType() == Material.AIR || is.getItemMeta() == null) {
            s(sender, "&cMust be a damageable item");
            return true;
        }

        ItemMeta im = is.getItemMeta();
        im.setUnbreakable(!im.isUnbreakable());
        is.setItemMeta(im);

        String un = "&aSet item to &6Breakable!";

        if (im.isUnbreakable()) {
            un = "&aSet item to &6Unbreakable!";
        }

        s(sender, un);
        return true;
    }

}

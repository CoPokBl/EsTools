package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetUnbreakable extends CMD {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotPlayer(sender))
            return true;

        ItemStack is = getMainHand((Player) sender);

        if (is == null || is.getType() == Material.AIR || (Main.version > 10 && is.getItemMeta() == null)) {
            s(sender, "&cMust be a damageable item");
            return true;
        }

        String un = "&aSet item to &6Breakable!";

        if (Main.version > 10) {
            ItemMeta im = is.getItemMeta();
            im.setUnbreakable(!im.isUnbreakable());
            is.setItemMeta(im);

            if (im.isUnbreakable()) {
                un = "&aSet item to &6Unbreakable!";
            }
        } else {
            if (is.getEnchantments().containsKey(Enchantment.DURABILITY)) {
                is.removeEnchantment(Enchantment.DURABILITY);
            } else {
                is.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                un = "&aSet item to &6Unbreakable!";
            }
        }

        s(sender, un);
        return true;
    }
}

package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetUnbreakable extends EsToolsCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        ItemStack item = getMainHand((Player) sender);
        if (item == null || item.getType() == Material.AIR || (Main.majorVersion > 10 && item.getItemMeta() == null)) {
            send(sender, "&cMust be a damageable item");
            return false;
        }

        String message = "&aSet item to &6Breakable!";

        if (Main.majorVersion > 10) {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setUnbreakable(!itemMeta.isUnbreakable());
            item.setItemMeta(itemMeta);

            if (itemMeta.isUnbreakable()) {
                message = "&aSet item to &6Unbreakable!";
            }
        } else {
            if (item.getEnchantments().containsKey(Enchantment.DURABILITY)) {
                item.removeEnchantment(Enchantment.DURABILITY);
            } else {
                item.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
                message = "&aSet item to &6Unbreakable!";
            }
        }

        send(sender, message);
        return true;
    }
}

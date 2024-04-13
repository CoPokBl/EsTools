package net.serble.estools.Commands.PowerPick;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PowerTool {
    public static void init() {
        PowerPick.init();
        PowerAxe.init();
        PowerShovel.init();
        PowerHoe.init();
        PowerSword.init();
    }

    public static void setupItem(ItemStack item, Enchantment enchantment) {
        item.addUnsafeEnchantment(enchantment, 32767);

        if (Main.majorVersion > 10) {
            ItemMeta im = item.getItemMeta();
            assert im != null;
            im.setUnbreakable(true);
            item.setItemMeta(im);
        } else {
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
        }
    }

    public static void cmd(CommandSender sender, ItemStack pp) {
        if (EsToolsCommand.isNotPlayer(sender)) {
            return;
        }

        Player p = (Player)sender;

        p.getInventory().addItem(pp);
        EsToolsCommand.send(sender, "&aThere you go!");
    }
}

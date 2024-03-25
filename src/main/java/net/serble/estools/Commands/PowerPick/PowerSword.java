package net.serble.estools.Commands.PowerPick;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PowerSword extends CMD {
    private static ItemStack powerPick;

    public static void init() {
        if (Main.version > 12) {
            powerPick = new ItemStack(Material.SALMON);
        } else {
            powerPick = new ItemStack(Material.getMaterial("RAW_FISH"));
        }

        powerPick.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);

        if (Main.version > 10) {
            ItemMeta im = powerPick.getItemMeta();
            im.setUnbreakable(true);
            powerPick.setItemMeta(im);
        } else {
            powerPick.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PowerPick.cmd(sender, powerPick);
        return true;
    }
}

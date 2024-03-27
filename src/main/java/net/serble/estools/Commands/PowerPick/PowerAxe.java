package net.serble.estools.Commands.PowerPick;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PowerAxe extends CMD {
    private static ItemStack powerItem;

    public static void init() {
        if (Main.version > 15) {
            powerItem = new ItemStack(Material.NETHERITE_AXE);
        }
        else {
            powerItem = new ItemStack(Material.DIAMOND_AXE, 1);
        }

        PowerTool.setupItem(powerItem, Enchantment.DIG_SPEED);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PowerTool.cmd(sender, powerItem);
        return true;
    }
}

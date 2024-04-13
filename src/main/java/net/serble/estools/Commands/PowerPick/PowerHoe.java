package net.serble.estools.Commands.PowerPick;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class PowerHoe extends EsToolsCommand {
    private static ItemStack powerItem;

    public static void init() {
        if (Main.majorVersion > 15) {
            powerItem = new ItemStack(Material.NETHERITE_HOE);
        }
        else {
            powerItem = new ItemStack(Material.DIAMOND_HOE, 1);
        }

        PowerTool.setupItem(powerItem, Enchantment.DIG_SPEED);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PowerTool.cmd(sender, powerItem);
        return true;
    }
}

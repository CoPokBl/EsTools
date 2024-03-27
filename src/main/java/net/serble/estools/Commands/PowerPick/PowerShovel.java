package net.serble.estools.Commands.PowerPick;

import net.serble.estools.CMD;
import net.serble.estools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PowerShovel extends CMD {
    private static ItemStack powerItem;

    public static void init() {
        if (Main.version > 15) {
            powerItem = new ItemStack(Material.NETHERITE_SHOVEL);
        } else if (Main.version > 12) {
            powerItem = new ItemStack(Material.DIAMOND_SHOVEL);
        } else {
            powerItem = new ItemStack(Objects.requireNonNull(Material.getMaterial("DIAMOND_SPADE")), 1);
        }

        PowerTool.setupItem(powerItem, Enchantment.DIG_SPEED);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PowerTool.cmd(sender, powerItem);
        return true;
    }
}

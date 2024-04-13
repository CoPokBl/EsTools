package net.serble.estools.Commands.PowerPick;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PowerSword extends EsToolsCommand {
    private static ItemStack powerPick;

    public static void init() {
        if (Main.majorVersion > 12) {
            powerPick = new ItemStack(Material.SALMON);
        } else {
            powerPick = new ItemStack(Objects.requireNonNull(Material.getMaterial("RAW_FISH")), 1);
        }

        PowerTool.setupItem(powerPick, Enchantment.DAMAGE_ALL);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PowerTool.cmd(sender, powerPick);
        return true;
    }
}

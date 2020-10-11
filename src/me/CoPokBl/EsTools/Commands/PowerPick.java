package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PowerPick extends CMD {

    private static ItemStack powerPick = new ItemStack(Material.DIAMOND_PICKAXE);

    public static void init() {
        powerPick.addUnsafeEnchantment(Enchantment.DIG_SPEED, 32767);
        ItemMeta im = powerPick.getItemMeta();
        im.setUnbreakable(true);
        powerPick.setItemMeta(im);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotPlayer(sender))
            return true;

        Player p = (Player)sender;

        p.getInventory().addItem(powerPick);
        return true;
    }
}
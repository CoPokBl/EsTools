package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SetPersistentData extends CMD {
    public static final String usage = genUsage("/setoersistentdata <key> <value>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 2) {
            s(sender, usage);
            return true;
        }

        ItemStack item = getMainHand((Player) sender);
        NamespacedKey key = NamespacedKey.fromString(args[0].toLowerCase(), Main.current);
        StringBuilder tagValue = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            tagValue.append(args[i]).append(" ");
        }

        tagValue.deleteCharAt(tagValue.length() - 1);

        setNbt(item, key, tagValue.toString());
        s(sender, "&aSet NBT tag &e\"" + args[0].toLowerCase() + "\"&a to &e\"" + tagValue + "\"&a!");
        return true;
    }

    public static void setNbt(ItemStack itemStack, NamespacedKey key, String tagValue) {
        // Get the ItemStack's ItemMeta
        ItemMeta itemMeta = itemStack.getItemMeta();

        // Check if the item meta is not null
        if (itemMeta != null) {
            // Set the NBT data
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, tagValue);

            // Save the updated ItemMeta
            itemStack.setItemMeta(itemMeta);
        }
    }
}

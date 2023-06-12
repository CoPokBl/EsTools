package me.CoPokBl.EsTools.Commands;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GetPersistentData extends CMD {
    public static final String usage = genUsage("/getpersistentdata <key>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 1) {
            s(sender, usage);
            return true;
        }

        ItemStack item = getMainHand((Player) sender);
        NamespacedKey key = NamespacedKey.fromString(args[0].toLowerCase(), Main.current);

        String nbt = getNbt(item, key);
        s(sender, "&aNBT tag &e\"" + args[0].toLowerCase() + "\"&a is &e\"" + nbt + "\"&a!");
        return true;
    }

    public static String getNbt(ItemStack itemStack, NamespacedKey key) {
        // Get the ItemStack's ItemMeta
        ItemMeta itemMeta = itemStack.getItemMeta();
        // Check if the item meta is not null and has the given tag
        if (itemMeta == null) {
            return null;
        }

        return itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }
}

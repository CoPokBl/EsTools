package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemovePersistentData extends EsToolsCommand {
    public static final String usage = genUsage("/removepersistentdata <key>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 1) {
            send(sender, usage);
            return true;
        }

        String tagString = args[0].toLowerCase();

        ItemStack item = getMainHand((Player) sender);
        NamespacedKey key = GetPersistentData.getNamespacedKey(tagString);
        if (key == null) {
            send(sender, "&cInvalid key! examples: 'estools:count', 'backpacks:size', etc");
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            send(sender, "&cItem does not have nbt tags!");
            return false;
        }

        meta.getPersistentDataContainer().remove(key);
        item.setItemMeta(meta);

        send(sender, "&aRemove NBT tag &e\"" + tagString + "\"&a!");
        return true;
    }
}

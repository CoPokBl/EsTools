package net.estools.Commands;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsItemMeta;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class RemovePersistentData extends EsToolsCommand {
    public static final String usage = genUsage("/removepersistentdata <key>");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 1) {
            send(sender, usage);
            return true;
        }

        String key = args[0].toLowerCase();

        EsItemStack item = ((EsPlayer) sender).getMainHand();
//        NamespacedKey key = GetPersistentData.getNamespacedKey(tagString);
//        if (key == null) {
//            send(sender, "&cInvalid key! examples: 'estools:count', 'backpacks:size', etc");
//            return false;
//        }

        EsItemMeta meta = item.getItemMeta();
        if (meta == null) {
            send(sender, "&cItem does not have nbt tags!");
            return false;
        }

        meta.getPersistentDataContainer().remove(key);
        item.setItemMeta(meta);

        send(sender, "&aRemove NBT tag &e\"" + key + "\"&a!");
        return true;
    }
}

package net.estools.Commands;

import net.estools.Main;
import net.estools.PlayerCommand;
import net.estools.ServerApi.EsInventoryType;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.*;

// TODO: Make functional: anvil, blast_furnace, furnace, smoker, brewing, cartography, enchanting, grindstone, loom,
//       smithing
public class Open extends PlayerCommand {
    private static final Set<EsInventoryType> INVALID_TYPES = new HashSet<>(Arrays.asList(EsInventoryType.CRAFTING,
            EsInventoryType.CHISELED_BOOKSHELF, EsInventoryType.COMPOSTER, EsInventoryType.CREATIVE,
            EsInventoryType.JUKEBOX, EsInventoryType.MERCHANT, EsInventoryType.PLAYER, EsInventoryType.ENCHANTING));

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (args.length == 0) {
            send(sender, "&cUsage: /open <inventory> [player]");
            return false;
        }

        EsPlayer target;
        if (args.length >= 2) {
            target = getPlayer(sender, args[1]);
            if (target == null) {
                return false;
            }
        } else {
            if (isNotPlayer(sender, "&cYou must specify a player if your running this from the console")) {
                return false;
            }

            target = (EsPlayer) sender;
        }

        EsInventoryType type;
        try {
            type = EsInventoryType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            send(sender, "&cThis inventory type does not exist!");
            return false;
        }

        if (INVALID_TYPES.contains(type)) {
            send(sender, "&cThis inventory type does not exist!");
            return false;
        }

        if (type == EsInventoryType.ENDER_CHEST) {
            target.openInventory(target.getEnderChest());
        } else {
            target.openInventory(Main.server.createInventory(target, type));
        }

        send(sender, "&aOpened inventory!");
        return true;
    }

    @Override
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
        if (args.length == 1) {
            List<String> tab = new ArrayList<>();

            for (EsInventoryType type : EsInventoryType.values()) {
                if (!INVALID_TYPES.contains(type) && Main.server.inventoryTypeExists(type)) {
                    tab.add(type.name().toLowerCase());
                }
            }

            return tab;
        } else if (args.length == 2) {
            return super.tabComplete(sender, args, lArg);
        }

        return new ArrayList<>();
    }
}

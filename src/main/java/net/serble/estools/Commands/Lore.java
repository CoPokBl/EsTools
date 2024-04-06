package net.serble.estools.Commands;

import net.serble.estools.CMD;
import net.serble.estools.MetaHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lore extends CMD {
    public static final String usage = genUsage("/lore <add> <lore>\n" +
            "/lore <set/insert> <line number> <lore>\n" +
            "/lore <remove> <line number>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender))
            return false;

        if (args.length < 2) {
            s(sender, usage);
            return false;
        }

        Player p = (Player)sender;
        ItemStack is = getMainHand(p);

        if (is.getItemMeta() == null) {
            s(sender, "&cThis item cannot have lore!");
            return false;
        }

        List<String> lore = MetaHandler.getLore(is);

        switch (args[0].toLowerCase()) {
            case "add": {
                String newLore = getLore(args, 1);
                lore.add(newLore);
                break;
            }

            case "set": {
                int line = getLine(sender, args[1], lore.size());
                if (line == Integer.MIN_VALUE) {
                    return false;
                }

                String newLore = getLore(args, 2);
                lore.set(line, newLore);
                break;
            }

            case "insert": {
                int line = getLine(sender, args[1], lore.size()+1);
                if (line == Integer.MIN_VALUE) {
                    return false;
                }

                String newLore = getLore(args, 2);
                lore.add(line, newLore);
                break;
            }

            case "remove": {
                int line = getLine(sender, args[1], lore.size());
                if (line == Integer.MIN_VALUE) {
                    return false;
                }

                lore.remove(line);
                s(sender, "&aRemoved line &6%d", line + 1);
                break;
            }

            default:
                s(sender, usage);
                return false;
        }

        MetaHandler.setLore(is, lore);
        return true;
    }

    private int getLine(CommandSender sender, String num, int limit) {
        try {
            int line = Integer.parseInt(num) - 1;

            if (line < 0 || line >= limit) {
                s(sender, "&cInvalid line number!");
                return Integer.MIN_VALUE;
            }

            return line;
        }
        catch (NumberFormatException ignored) {
            s(sender, usage);
            return Integer.MIN_VALUE;
        }
    }

    private String getLore(String[] args, int start) {
        return t("&f" + String.join(" ", Arrays.copyOfRange(args, start, args.length)));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        if (args.length == 1) {
            tab.add("add");
            tab.add("set");
            tab.add("insert");
            tab.add("remove");
        } else if (args.length == 2 && equalsOr(args[0].toLowerCase(), "set", "insert", "remove")) {
            if (!(sender instanceof Player)) {
                tab.add("1");
                return tab;
            }

            ItemStack is = getMainHand((Player)sender);
            List<String> lore = MetaHandler.getLore(is);
            for (int i = 1; i <= lore.size(); i++) {
                tab.add(String.valueOf(i));
            }
        }

        return tab;
    }
}

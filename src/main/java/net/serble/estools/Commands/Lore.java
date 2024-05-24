package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.MetaHandler;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lore extends EsToolsCommand {
    public static final String usage = genUsage("/lore <add> <lore>\n" +
            "/lore <set/insert> <line number> <lore>\n" +
            "/lore <remove> <line number>");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        if (args.length < 2) {
            send(sender, usage);
            return false;
        }

        EsPlayer p = (EsPlayer)sender;
        EsItemStack is = p.getMainHand();

        if (is.getItemMeta() == null) {
            send(sender, "&cThis item cannot have lore!");
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
                send(sender, "&aRemoved line &6%d", line + 1);
                break;
            }

            default:
                send(sender, usage);
                return false;
        }

        MetaHandler.setLore(is, lore);
        return true;
    }

    private int getLine(EsCommandSender sender, String num, int limit) {
        try {
            int line = Integer.parseInt(num) - 1;

            if (line < 0 || line >= limit) {
                send(sender, "&cInvalid line number!");
                return Integer.MIN_VALUE;
            }

            return line;
        }
        catch (NumberFormatException ignored) {
            send(sender, usage);
            return Integer.MIN_VALUE;
        }
    }

    private String getLore(String[] args, int start) {
        return translate("&f" + String.join(" ", Arrays.copyOfRange(args, start, args.length)));
    }

    @Override
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        if (args.length == 1) {
            tab.add("add");
            tab.add("set");
            tab.add("insert");
            tab.add("remove");
        } else if (args.length == 2 && equalsOr(args[0].toLowerCase(), "set", "insert", "remove")) {
            if (!(sender instanceof EsPlayer)) {
                tab.add("1");
                return tab;
            }

            EsItemStack is = ((EsPlayer)sender).getMainHand();
            List<String> lore = MetaHandler.getLore(is);
            for (int i = 1; i <= lore.size(); i++) {
                tab.add(String.valueOf(i));
            }
        }

        return tab;
    }
}

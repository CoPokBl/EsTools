package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Vault;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SignType {
    public void run(Player p, String[] lines) { }

    public static double getSignMoney(String line, Player p) {
        double price = 0;
        try {
            String priceString = line.substring(1);

            if (!line.startsWith("$")) {
                send(p, "&cMoney must be formatted with \"$COST\"");
                return -1;
            }

            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) { /* Ignored */ }

        return price;
    }

    public static boolean takeMoney(String line, Player p) {
        return Vault.takeMoney(getSignMoney(line, p), p);
    }

    public static boolean payMoney(String line, Player p) {
        return Vault.payMoney(getSignMoney(line, p), p);
    }

    public static void send(CommandSender s, String msg, Object... args) {
        EsToolsCommand.send(s, msg, args);
    }
}

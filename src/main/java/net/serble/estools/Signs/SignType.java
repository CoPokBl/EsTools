package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import net.serble.estools.Vault;

public abstract class SignType {
    public void run(EsPlayer p, String[] lines) { }

    public static double getSignMoney(String line, EsPlayer p) {
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

    public static boolean takeMoney(String line, EsPlayer p) {
        return Vault.takeMoney(getSignMoney(line, p), p);
    }

    public static boolean payMoney(String line, EsPlayer p) {
        return Vault.payMoney(getSignMoney(line, p), p);
    }

    public static void send(EsCommandSender s, String msg, Object... args) {
        EsToolsCommand.send(s, msg, args);
    }
}

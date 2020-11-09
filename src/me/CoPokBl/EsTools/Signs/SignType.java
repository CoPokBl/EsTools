package me.CoPokBl.EsTools.Signs;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class SignType {
    public void run(Player p, String[] lines) {}

    public static double getSignMoney(String line, Player p) {
        double price = 0;

        try {
            String priceS = line.substring(1);

            if (!line.startsWith("$")) {
                CMD.s(p, "&cMoney must be formatted with \"$COST\"");
                return -1;
            }

            price = Double.parseDouble(priceS);
        } catch (Exception e) {}

        return price;
    }

    public static boolean hasMoney(String line, Player p) {
        return CMD.hasMoney(getSignMoney(line, p), p);
    }

    public static boolean takeMoney(String line, Player p) {
        return CMD.takeMoney(getSignMoney(line, p), p);
    }

    public static boolean payMoney(String line, Player p) {
        return CMD.payMoney(getSignMoney(line, p), p);
    }
}

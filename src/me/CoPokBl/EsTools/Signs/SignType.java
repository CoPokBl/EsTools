package me.CoPokBl.EsTools.Signs;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.entity.Player;

public abstract class SignType {
    public void run(Player p, String[] lines) {}

    public static boolean payMoney(String line, Player p) {
        double price = 0;

        try {
            String priceS = line.substring(1);

            if (!line.startsWith("$")) {
                CMD.s(p, "&cMoney must be formatted with \"$COST\"");
                return false;
            }

            price = Double.parseDouble(priceS);
        } catch (Exception e) {}

        if (price > 0) {
            if (Main.econ == null) {
                CMD.s(p, "&cVault is required for economy!");
                return false;
            }

            if (Main.econ.getBalance(p) >= price) {
                Main.econ.withdrawPlayer(p, price);
            } else {
                CMD.s(p, "&cYou do not have enough money to purchase this item.");
                return false;
            }
        }

        return true;
    }
}

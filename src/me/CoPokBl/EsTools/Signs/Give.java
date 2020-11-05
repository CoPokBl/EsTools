package me.CoPokBl.EsTools.Signs;

import me.CoPokBl.EsTools.CMD;
import me.CoPokBl.EsTools.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Give extends SignType {
    @Override
    public void run(Player p, String[] lines) {

        int amount = 1;

        try {
            amount = Integer.parseInt(lines[2]);
        } catch (Exception e) {}

        ItemStack is = me.CoPokBl.EsTools.Give.getItem(lines[1], amount);

        if (is == null) {
            CMD.s(p, "&cItem not found!");
            return;
        }

        // money

        double price = 0;

        try {
            String priceS = lines[3].substring(1);

            if (!lines[3].startsWith("$")) {
                CMD.s(p, "&cMoney must be formatted with \"$COST\"");
                return;
            }

            price = Double.parseDouble(priceS);
        } catch (Exception e) {}

        if (price > 0) {
            if (Main.econ == null) {
                CMD.s(p, "&cVault is required for economy!");
                return;
            }

            if (Main.econ.getBalance(p) >= price) {
                Main.econ.withdrawPlayer(p, price);
            } else {
                CMD.s(p, "&cYou do not have enough money to purchase this item.");
                return;
            }
        }

        p.getInventory().addItem(is.clone());
    }
}

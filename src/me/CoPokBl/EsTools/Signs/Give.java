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

        if (!payMoney(lines[3], p))
            return;

        p.getInventory().addItem(is.clone());
    }
}

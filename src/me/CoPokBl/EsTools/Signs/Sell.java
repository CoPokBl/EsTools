package me.CoPokBl.EsTools.Signs;

import me.CoPokBl.EsTools.CMD;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sell extends SignType {
    @Override
    public void run(Player p, String[] lines) {

        int amount = 1;

        try {
            amount = Integer.parseInt(lines[3]);
        } catch (Exception e) {}

        ItemStack is = me.CoPokBl.EsTools.Give.getItem(lines[1], amount);

        if (is == null) {
            CMD.s(p, "&cItem not found!");
            return;
        }

        for (ItemStack e : p.getInventory().getContents()) {
            if (e.getType().equals(is.getType())) {
                if (e.getAmount() >= is.getAmount()) {
                    if (payMoney(lines[2], p))
                        e.setAmount(e.getAmount() - is.getAmount());
                    return;
                }
            }
        }

        CMD.s(p, "&cCant find item!");
    }
}

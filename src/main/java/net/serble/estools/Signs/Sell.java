package net.serble.estools.Signs;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sell extends SignType {
    @Override
    public void run(Player p, String[] lines) {

        int amount = 1;

        try {
            amount = Integer.parseInt(lines[3]);
        } catch (Exception e) { /* ignore */ }

        ItemStack is = net.serble.estools.Give.getItem(lines[1], amount);

        if (is == null) {
            s(p, "&cItem not found!");
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

        s(p, "&cCant find item!");
    }
}

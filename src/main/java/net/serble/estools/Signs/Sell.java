package net.serble.estools.Signs;

import net.serble.estools.Commands.Give.Give;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sell extends SignType {
    @Override
    public void run(Player p, String[] lines) {
        int amount = 1;
        try {
            amount = Integer.parseInt(lines[3]);
        } catch (Exception e) { /* ignore */ }

        ItemStack item = Give.getItem(lines[1], amount);
        if (item == null) {
            send(p, "&cItem not found!");
            return;
        }

        for (ItemStack e : p.getInventory().getContents()) {
            if (!e.getType().equals(item.getType()) || e.getAmount() < item.getAmount()) {
                continue;
            }

            if (payMoney(lines[2], p)) {
                e.setAmount(e.getAmount() - item.getAmount());
            }
            return;
        }

        send(p, "&cCant find item!");
    }
}

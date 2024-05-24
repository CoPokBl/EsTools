package net.serble.estools.Signs;

import net.serble.estools.Commands.Give.Give;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class Sell extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        int amount = 1;
        try {
            amount = Integer.parseInt(lines[3]);
        } catch (Exception e) { /* ignore */ }

        EsItemStack item = Give.getItem(lines[1], amount);
        if (item == null) {
            send(p, "&cItem not found!");
            return;
        }

        for (EsItemStack e : p.getInventory().getContents()) {
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

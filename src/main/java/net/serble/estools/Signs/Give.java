package net.serble.estools.Signs;

import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class Give extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        int amount = 1;
        try {
            amount = Integer.parseInt(lines[2]);
        } catch (NumberFormatException e) { /* Ignored */ }

        EsItemStack item = net.serble.estools.Commands.Give.Give.getItem(lines[1], amount);

        if (item == null) {
            send(p, "&cItem not found!");
            return;
        }

        if (!takeMoney(lines[3], p)) {
            send(p, "&cYou cannot afford this!");
            return;
        }

        p.getInventory().addItem(item.clone());
    }
}

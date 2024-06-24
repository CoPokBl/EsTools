package net.estools.Signs;

import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class Give extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        int amount = 1;
        try {
            amount = Integer.parseInt(lines[2]);
        } catch (NumberFormatException e) { /* Ignored */ }

        EsItemStack item = net.estools.Commands.Give.Give.getItem(lines[1], amount);

        if (item == null) {
            send(p, "&cItem not found!");
            return;
        }

        p.getInventory().addItem(item.clone());
    }
}

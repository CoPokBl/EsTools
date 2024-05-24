package net.serble.estools.Signs;

import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class Feed extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        if (!takeMoney(lines[1], p)) {
            return;
        }

        p.setFoodLevel(20);
        p.setSaturation(6);
    }
}

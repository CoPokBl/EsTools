package net.serble.estools.Signs;

import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class Heal extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        if (!takeMoney(lines[1], p)) {
            return;
        }

        p.setHealth(p.getMaxHealth());
        p.setOnFireTicks(0);
    }
}

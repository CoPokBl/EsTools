package net.estools.Signs;

import net.estools.ServerApi.Interfaces.EsPlayer;

public class Heal extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        p.setHealth(p.getMaxHealth());
        p.setOnFireTicks(0);
    }
}

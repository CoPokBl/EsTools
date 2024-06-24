package net.estools.Signs;

import net.estools.ServerApi.Interfaces.EsPlayer;

public class Feed extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        p.setFoodLevel(20);
        p.setSaturation(6);
    }
}

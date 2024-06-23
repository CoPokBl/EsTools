package net.serble.estools.Signs;

import net.serble.estools.Commands.Fix;
import net.serble.estools.ServerApi.Interfaces.EsItemStack;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public class Repair extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        EsItemStack item = p.getMainHand();
        Fix.repair(item);
    }
}

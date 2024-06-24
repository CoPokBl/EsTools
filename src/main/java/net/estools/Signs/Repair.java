package net.estools.Signs;

import net.estools.Commands.Fix;
import net.estools.ServerApi.Interfaces.EsItemStack;
import net.estools.ServerApi.Interfaces.EsPlayer;

public class Repair extends SignType {

    @Override
    public void run(EsPlayer p, String[] lines) {
        EsItemStack item = p.getMainHand();
        Fix.repair(item);
    }
}

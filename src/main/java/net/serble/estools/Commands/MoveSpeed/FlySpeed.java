package net.serble.estools.Commands.MoveSpeed;

import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import net.serble.estools.Tuple;

import java.util.List;

public class FlySpeed extends MoveSpeed {
    private static final String usage = genUsage("/flyspeed <speed> [players]");

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        Tuple<List<EsPlayer>, Float> result = getTargets(sender, args);

        if (result == null) {
            return false;
        }

        for (EsPlayer p : result.a()) {
            p.setFlySpeed(result.b());
        }

        send(sender, "&aSet fly speed to &6%s", String.valueOf(result.b() * 10));
        return true;
    }
}

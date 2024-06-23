package net.serble.estools.Commands.Teleport;

import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;

public class TpAll extends EntityCommand {
    private static final String usage = genUsage("/tpall [entity]");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        EsLivingEntity p;

        if (args.length == 0) {
            p = (EsLivingEntity) sender;

            if (isNotEntity(sender, usage)) {
                return false;
            }
        } else {
            p = getEntity(sender, args[0]);
        }

        for (EsLivingEntity t : Main.server.getOnlinePlayers()) {
            assert p != null;
            t.teleport(p.getLocation());
        }

        assert p != null;
        send(sender, "&aTeleported all players to &6%s", p.getName());
        return true;
    }
}

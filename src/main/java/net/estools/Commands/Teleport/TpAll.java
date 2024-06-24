package net.estools.Commands.Teleport;

import net.estools.EntityCommand;
import net.estools.Main;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsLivingEntity;

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
            if (p == null) {
                return false;
            }
        }

        for (EsLivingEntity t : Main.server.getOnlinePlayers()) {
            t.teleport(p.getLocation());
        }

        send(sender, "&aTeleported all players to &6%s", p.getName());
        return true;
    }
}

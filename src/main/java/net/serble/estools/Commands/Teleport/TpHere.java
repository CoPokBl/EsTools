package net.serble.estools.Commands.Teleport;

import net.serble.estools.EntityCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsLivingEntity;

public class TpHere extends EntityCommand {
    private static final String usage = genUsage("/tphere <entity1> [entity2] [entity3]...");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotEntity(sender)) {
            return false;
        }

        if (args.length == 0) {
            send(sender, usage);
            return false;
        }

        EsLivingEntity p = (EsLivingEntity) sender;
        for (String arg : args) {
            EsLivingEntity t = getEntity(sender, arg);

            if (t != null) {
                t.teleport(p.getLocation());
            } else {
                return false;
            }
        }

        send(sender, "&aTeleported &6%s&a to &6%s", argsToString(args, 0), p.getName());
        return true;
    }
}

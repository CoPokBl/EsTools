package net.estools.Commands;

import net.estools.EntityCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEntity;

// /burn <entity> [time]
public class Burn extends EntityCommand {
    private static final String usage = genUsage("/burn <entity> [ticks]");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (args.length == 0) {
            send(sender, usage);
            return false;
        }

        EsEntity entity = getNonLivingEntity(sender, args[0]);
        if (entity == null) {
            return false;
        }

        int ticks = 100;
        if (args.length > 1) {
            try {
                ticks = Integer.parseInt(args[1]);
            } catch (NumberFormatException x) {
                send(sender, usage);
                return false;
            }
        }

        entity.setOnFireTicks(ticks);
        send(sender, "&aSet &6" + entity.getName() + "&a on fire for &6" + ticks + "&a ticks.");
        return true;
    }
}

package net.estools.Commands;

import net.estools.EntityCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsEntity;

// /extinguish [entity]
public class Extinguish extends EntityCommand {

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        EsEntity entity;

        if (args.length == 0) {
            if (isNotEntity(sender, "&aConsole must provide an entity to extinguish.")) {
                return false;
            }
            entity = (EsEntity) sender;
        } else {
            entity = getNonLivingEntity(sender, args[0]);
            if (entity == null) {
                return false;
            }
        }

        entity.setOnFireTicks(0);
        send(sender, "&aExtinguished &6" + entity.getName() + "&a.");
        return true;
    }
}

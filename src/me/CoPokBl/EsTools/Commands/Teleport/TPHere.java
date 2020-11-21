package me.CoPokBl.EsTools.Commands.Teleport;

import me.CoPokBl.EsTools.EntityCommand;
import me.CoPokBl.EsTools.MultiPlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class TPHere extends EntityCommand {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (isNotEntity(sender))
            return false;

        if (args.length == 0) {
            s(sender, genUsage("/tphere <entity1> [entity2] [entity3]..."));
        }

        LivingEntity p = (LivingEntity) sender;

        for (String arg : args) {
            LivingEntity t = getEntity(sender, arg);

            if (t != null)
                t.teleport(p);
        }
        return true;
    }
}

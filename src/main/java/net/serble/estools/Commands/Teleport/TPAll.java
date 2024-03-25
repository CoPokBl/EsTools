package net.serble.estools.Commands.Teleport;

import net.serble.estools.EntityCommand;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class TPAll extends EntityCommand {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        LivingEntity p;

        if (Main.version < 7) {
            s(sender, "&cYou cannot do this command in version 1.%s", Main.version);
            return true;
        }

        if (args.length == 0) {
            p = (LivingEntity) sender;

            if (isNotEntity(sender, genUsage("/tpall [entity]")))
                return false;
        } else {
            p = getEntity(sender, args[0]);
        }

        for (LivingEntity t : Bukkit.getOnlinePlayers()) {
            t.teleport(p);
        }
        return true;
    }
}

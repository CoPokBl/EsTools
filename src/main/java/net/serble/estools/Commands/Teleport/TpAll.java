package net.serble.estools.Commands.Teleport;

import net.serble.estools.EntityCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class TpAll extends EntityCommand {
    private static final String usage = genUsage("/tpall [entity]");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LivingEntity p;

        if (args.length == 0) {
            p = (LivingEntity) sender;

            if (isNotEntity(sender, usage)) {
                return false;
            }
        } else {
            p = getEntity(sender, args[0]);
        }

        for (LivingEntity t : getOnlinePlayers()) {
            assert p != null;
            t.teleport(p);
        }

        assert p != null;
        send(sender, "&aTeleported all players to &6%s", getEntityName(p));
        return true;
    }
}

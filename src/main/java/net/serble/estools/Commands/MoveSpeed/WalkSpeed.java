package net.serble.estools.Commands.MoveSpeed;

import net.serble.estools.Tuple;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WalkSpeed extends MoveSpeed {
    private static final String usage = genUsage("/walkspeed <speed> [players]");

    @Override
    protected String getUsage() {
        return usage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Tuple<List<Player>, Float> result = getTargets(sender, args);

        if (result == null) {
            return false;
        }

        for (Player p : result.a()) {
            p.setWalkSpeed(result.b());
        }

        send(sender, "&aSet walk speed to &6%s", String.valueOf(result.b() * 10));
        return true;
    }
}

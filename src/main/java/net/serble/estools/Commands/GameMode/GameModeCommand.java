package net.serble.estools.Commands.GameMode;

import net.serble.estools.MultiPlayerCommand;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class GameModeCommand extends MultiPlayerCommand {

    public static void setGamemode(CommandSender sender, String label, String[] args, GameMode gm) {
        ArrayList<Player> ps = new ArrayList<>();

        if (args.length == 0) {
            if (isNotPlayer(sender, genUsage("/%s [player]"), label))
                return;

            ps.add((Player) sender);

            send(sender, "&aGamemode set to &6%s", gm.toString());
        } else {
            ps = getPlayers(sender, args);

            if (ps.isEmpty())
                return;

            send(sender, "&aGamemode set to &6%s&a for &6%s", gm.toString(), argsToString(args, 0));
        }

        for (Player p : ps) {
            p.setGameMode(gm);
        }
    }
}

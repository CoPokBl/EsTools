package me.CoPokBl.EsTools.Commands.GameMode;

import me.CoPokBl.EsTools.MultiPlayerCommand;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class GM extends MultiPlayerCommand {
    public static void setGamemode(CommandSender sender, String label, String[] args, GameMode gm) {
        ArrayList<Player> ps = new ArrayList<Player>();

        if (args.length == 0) {
            if (isNotPlayer(sender, genUsage("/%s [player]"), label))
                return;

            ps.add((Player) sender);

            s(sender, "&aGamemode set to &6%s", gm.toString());
        } else {
            ps = getPlayers(sender, args);

            if (ps.isEmpty())
                return;

            s(sender, "&aGamemode set to &6%s&a for &6%s", gm.toString(), argsToString(args, 0));
        }

        for (Player p : ps) {
            p.setGameMode(gm);
        }
    }
}

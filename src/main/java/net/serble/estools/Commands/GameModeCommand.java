package net.serble.estools.Commands;

import net.serble.estools.MultiPlayerCommand;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameModeCommand extends MultiPlayerCommand {
    private GameMode gameMode;

    public GameModeCommand(String gameMode) {
        // If it's an old version it will throw when trying to get Enum Value, so we ignore it.
        try {
            this.gameMode = GameMode.valueOf(gameMode);
        } catch (IllegalArgumentException ignored) {}
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<Player> ps = new ArrayList<>();

        if (args.length == 0) {
            if (isNotPlayer(sender, genUsage("/%s [player]"), label)) {
                return false;
            }

            ps.add((Player) sender);

            send(sender, "&aGamemode set to &6%s", gameMode.toString());
        } else {
            ps = getPlayers(sender, args);

            if (ps.isEmpty())
                return false;

            send(sender, "&aGamemode set to &6%s&a for &6%s", gameMode.toString(), argsToString(args, 0));
        }

        for (Player p : ps) {
            p.setGameMode(gameMode);
        }

        return true;
    }
}

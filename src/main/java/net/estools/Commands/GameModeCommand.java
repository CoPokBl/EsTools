package net.estools.Commands;

import net.estools.MultiPlayerCommand;
import net.estools.ServerApi.EsGameMode;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;

public class GameModeCommand extends MultiPlayerCommand {
    private final EsGameMode gameMode;
    private final String cmd;

    public GameModeCommand(EsGameMode gameMode, String cmd) {
        this.gameMode = gameMode;
        this.cmd = cmd;
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        ArrayList<EsPlayer> ps = new ArrayList<>();

        if (args.length == 0) {
            if (isNotPlayer(sender, genUsage("/%s [player]"), cmd)) {
                return false;
            }

            ps.add((EsPlayer) sender);

            send(sender, "&aGamemode set to &6%s", gameMode.toString());
        } else {
            ps = getPlayers(sender, args);

            if (ps.isEmpty())
                return false;

            send(sender, "&aGamemode set to &6%s&a for &6%s", gameMode.toString(), argsToString(args, 0));
        }

        for (EsPlayer p : ps) {
            p.setGameMode(gameMode);
        }

        return true;
    }
}

package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

public abstract class SignType {
    public void run(EsPlayer p, String[] lines) { }

    public static void send(EsCommandSender s, String msg, Object... args) {
        EsToolsCommand.send(s, msg, args);
    }
}

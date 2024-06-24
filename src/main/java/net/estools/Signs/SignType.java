package net.estools.Signs;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

public abstract class SignType {
    public void run(EsPlayer p, String[] lines) { }

    public static void send(EsCommandSender s, String msg, Object... args) {
        EsToolsCommand.send(s, msg, args);
    }
}

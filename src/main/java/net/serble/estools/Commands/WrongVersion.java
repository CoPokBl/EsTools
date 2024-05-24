package net.serble.estools.Commands;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;

public class WrongVersion extends EsToolsCommand {
    private final String minVersion;

    public WrongVersion(int minVersion) {
        this.minVersion = minVersion + "";
    }

    public WrongVersion(String ver) {
        minVersion = ver;
    }

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        send(sender, "&cThis command requires Minecraft to be at least 1.%s", minVersion);
        return true;
    }
}

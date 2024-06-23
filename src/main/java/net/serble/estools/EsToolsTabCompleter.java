package net.serble.estools;

import net.serble.estools.ServerApi.Interfaces.EsCommandSender;

import java.util.List;

public interface EsToolsTabCompleter {
    List<String> onTabComplete(EsCommandSender sender, String[] args);
}

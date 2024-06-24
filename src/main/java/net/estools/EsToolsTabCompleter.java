package net.estools;

import net.estools.ServerApi.Interfaces.EsCommandSender;

import java.util.List;

public interface EsToolsTabCompleter {
    List<String> onTabComplete(EsCommandSender sender, String[] args);
}

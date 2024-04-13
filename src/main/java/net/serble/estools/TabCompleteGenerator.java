package net.serble.estools;

import org.bukkit.command.TabCompleter;

public class TabCompleteGenerator {
    public static TabCompleter generate(EsToolsTabCompleter tabCompleter) {
        return tabCompleter::onTabComplete;
    }
}

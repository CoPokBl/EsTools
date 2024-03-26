package net.serble.estools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.List;

public interface EsToolsTabCompleter {

    default void register(PluginCommand cmd) {
        cmd.setTabCompleter(TabCompleteGenerator.generate(this));
    }

    List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);

}

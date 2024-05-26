package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.EsToolsTabCompleter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BukkitTabCompleteGenerator {

    public static TabCompleter generate(EsToolsTabCompleter tabCompleter) {
        return new TabCompleter() {
            @Nullable
            @Override
            public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
                return tabCompleter.onTabComplete(BukkitHelper.fromBukkitCommandSender(sender), args);
            }
        };
    }
}

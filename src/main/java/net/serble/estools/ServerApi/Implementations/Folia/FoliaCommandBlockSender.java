package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitCommandBlockSender;
import org.bukkit.command.BlockCommandSender;

public class FoliaCommandBlockSender extends BukkitCommandBlockSender {
    private final BlockCommandSender bukkitSender;

    public FoliaCommandBlockSender(BlockCommandSender child) {
        super(child);
        bukkitSender = child;
    }
}

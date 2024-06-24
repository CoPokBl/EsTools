package net.estools.ServerApi.Implementations.Folia;

import net.estools.ServerApi.Implementations.Bukkit.BukkitCommandBlockSender;
import org.bukkit.command.BlockCommandSender;

public class FoliaCommandBlockSender extends BukkitCommandBlockSender {

    public FoliaCommandBlockSender(BlockCommandSender child) {
        super(child);
    }
}

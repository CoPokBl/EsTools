package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitBlock;
import org.bukkit.block.BlockState;

public class FoliaBlock extends BukkitBlock {
    private final BlockState bukkitState;

    public FoliaBlock(BlockState block) {
        super(block);
        bukkitState = block;
    }
}

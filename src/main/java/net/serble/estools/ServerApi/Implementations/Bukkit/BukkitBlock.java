package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsBlock;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class BukkitBlock implements EsBlock {
    private final BlockState bukkitState;

    public BukkitBlock(BlockState block) {
        bukkitState = block;
    }

    private Block getBukkitBlock() {
        return bukkitState.getBlock();
    }

    @Override
    public boolean breakNaturally() {
        return getBukkitBlock().breakNaturally();
    }

    @Override
    public int getX() {
        return getBukkitBlock().getX();
    }

    @Override
    public int getY() {
        return getBukkitBlock().getY();
    }

    @Override
    public int getZ() {
        return getBukkitBlock().getZ();
    }

    @Override
    public String getType() {
        return bukkitState.getType().name();
    }
}

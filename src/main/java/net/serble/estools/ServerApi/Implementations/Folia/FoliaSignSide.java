package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitSignSide;
import org.bukkit.block.sign.SignSide;

public class FoliaSignSide extends BukkitSignSide {
    private final SignSide bukkitSide;

    public FoliaSignSide(SignSide side) {
        super(side);
        this.bukkitSide = side;
    }
}

package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.Interfaces.EsSignSide;
import org.bukkit.block.sign.SignSide;

public class BukkitSignSide implements EsSignSide {
    private final SignSide bukkitSide;

    public BukkitSignSide(SignSide side) {
        this.bukkitSide = side;
    }

    @Override
    public void setGlowingText(boolean glowing) {
        bukkitSide.setGlowingText(glowing);
    }

    @Override
    public boolean isGlowingText() {
        return bukkitSide.isGlowingText();
    }

    @Override
    public void setLine(int line, String text) {
        bukkitSide.setLine(line, text);
    }
}

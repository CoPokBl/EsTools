package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.Interfaces.EsSign;
import net.estools.ServerApi.Interfaces.EsSignSide;
import org.bukkit.block.Sign;

public class BukkitSign extends BukkitBlock implements EsSign {
    private final Sign bukkitSign;

    public BukkitSign(Sign block) {
        super(block);
        bukkitSign = block;
    }

    @Override
    public void update() {
        bukkitSign.update();
    }

    @SuppressWarnings("deprecation")  // Method is version specific
    @Override
    public void setGlowingText(boolean glowing) {
        bukkitSign.setGlowingText(glowing);
    }

    @SuppressWarnings("deprecation")  // Method is version specific
    @Override
    public boolean isGlowingText() {
        return bukkitSign.isGlowingText();
    }

    @SuppressWarnings("deprecation")  // Method is version specific
    @Override
    public void setLine(int line, String text) {
        bukkitSign.setLine(line, text);
    }

    @Override
    public EsSignSide getTargetSide(EsPlayer player) {
        return new BukkitSignSide(bukkitSign.getTargetSide(((BukkitPlayer) player).getBukkitPlayer()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getLine(int line) {
        return bukkitSign.getLine(line);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String[] getLines() {
        return bukkitSign.getLines();
    }
}

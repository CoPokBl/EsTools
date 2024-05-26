package net.serble.estools.ServerApi.Implementations.Folia;

import net.serble.estools.ServerApi.Interfaces.EsPlayer;
import net.serble.estools.ServerApi.Interfaces.EsSign;
import net.serble.estools.ServerApi.Interfaces.EsSignSide;
import org.bukkit.block.Sign;

public class FoliaSign extends FoliaBlock implements EsSign {
    private final Sign bukkitSign;

    public FoliaSign(Sign block) {
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

    @Override
    public EsSignSide getTargetSide(EsPlayer player) {
        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaSignSide(bukkitSign.getTargetSide(((net.serble.estools.ServerApi.Implementations.Folia.FoliaPlayer) player).getBukkitPlayer()));
    }
}

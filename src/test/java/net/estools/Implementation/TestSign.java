package net.estools.Implementation;

import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.Interfaces.EsSign;
import net.estools.ServerApi.Interfaces.EsSignSide;

public class TestSign extends TestBlock implements EsSign {
    private final String[] lines = new String[4];

    // TEST METHODS

    public TestSign(int x, int y, int z) {
        super("SIGN", x, y, z);
    }

    // IMPLEMENTATION METHODS

    @Override
    public void update() {

    }

    @Override
    public void setGlowingText(boolean glowing) {
        throw new UnsupportedOperationException("Only for old versions");
    }

    @Override
    public boolean isGlowingText() {
        return false;
    }

    @Override
    public void setLine(int line, String text) {
        lines[line] = text;
    }

    @Override
    public String getLine(int line) {
        return lines[line];
    }

    @Override
    public String[] getLines() {
        return lines;
    }

    @Override
    public EsSignSide getTargetSide(EsPlayer player) {
        return ((TestPlayer) player).getTargetSignSide();
    }
}

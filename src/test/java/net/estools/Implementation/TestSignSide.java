package net.estools.Implementation;

import net.estools.ServerApi.Interfaces.EsSignSide;

public class TestSignSide implements EsSignSide {
    private final String[] lines = new String[4];
    private boolean glowing = false;

    // TEST METHODS

    public String[] getLines() {
        return lines;
    }

    // IMPLEMENTATION METHODS

    @Override
    public void setGlowingText(boolean glowing) {
        this.glowing = glowing;
    }

    @Override
    public boolean isGlowingText() {
        return glowing;
    }

    @Override
    public void setLine(int line, String text) {
        lines[line] = text;
    }
}

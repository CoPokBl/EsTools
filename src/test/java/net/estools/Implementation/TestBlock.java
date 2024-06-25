package net.estools.Implementation;

import net.estools.ServerApi.Interfaces.EsBlock;

public class TestBlock implements EsBlock {
    private final int x;
    private final int y;
    private final int z;
    private final String type;

    // TEST METHODS

    public TestBlock(String type, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    // IMPLEMENTATION METHODS

    @Override
    public boolean breakNaturally() {
        return true;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public String getType() {
        return type;
    }
}

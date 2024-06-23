package net.serble.estools;

public class Bitmask {
    private final boolean[] bits;

    public Bitmask(int value) {
        bits = new boolean[32];
        for (int i = 0; i < 32; i++) {
            bits[i] = (value & (1 << i)) != 0;
        }
    }

    public boolean getBit(int i) {
        return bits[i];
    }

    public int getValueOfFirstBits(int amount) {
        int value = 0;
        for (int i = 0; i < amount; i++) {
            if (bits[i]) {
                value |= 1 << i;
            }
        }
        return value;
    }
}

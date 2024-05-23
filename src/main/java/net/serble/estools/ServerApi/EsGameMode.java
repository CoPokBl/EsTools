package net.serble.estools.ServerApi;

public enum EsGameMode {
    Survival(0),
    Creative(1),
    Adventure(2),
    Spectator(3);

    private final int ordinal;

    EsGameMode(int val) {
        ordinal = val;
    }

    public int getOrdinal() {
        return ordinal;
    }
}

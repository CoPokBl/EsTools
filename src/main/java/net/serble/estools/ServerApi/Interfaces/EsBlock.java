package net.serble.estools.ServerApi.Interfaces;

@SuppressWarnings("unused")
public interface EsBlock {
    boolean breakNaturally();
    int getX();
    int getY();
    int getZ();
    String getType();
}

package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.Position;

@SuppressWarnings("unused")
public interface EsBlock {
    boolean breakNaturally();
    int getX();
    int getY();
    int getZ();
    String getType();

    default Position getLocation() {
        return new Position(getX(), getY(), getZ());
    }
}

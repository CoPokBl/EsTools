package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.EsLocation;

import java.util.List;
import java.util.UUID;

public interface EsWorld {
    String getName();
    UUID getUuid();
    List<EsEntity> getEntities();
    List<EsEntity> getNearbyEntities(EsLocation loc, double xoff, double yoff, double zoff);

    /**
     * Get the highest block at the specific position.
     * @param x The x position.
     * @param z The z position.
     * @return The highest block.
     * @implNote Implementations may return either null or air if no block is found.
     */
    EsBlock getHighestBlockAt(int x, int z);
    void setTime(long time);
    void setStorming(boolean val);
    void setThundering(boolean val);
    void strikeLightning(EsLocation loc);
}

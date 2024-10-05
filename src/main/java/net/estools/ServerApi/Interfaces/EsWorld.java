package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.EsLocation;

import java.util.List;
import java.util.UUID;

public interface EsWorld {
    String getName();
    UUID getUuid();
    List<EsEntity> getEntities();
    List<EsEntity> getNearbyEntities(EsLocation loc, double xoff, double yoff, double zoff);
    void setTime(long time);
    void setStorming(boolean val);
    void setThundering(boolean val);
    void strikeLightning(EsLocation loc);
}

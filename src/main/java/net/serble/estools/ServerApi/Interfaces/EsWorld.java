package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsLocation;

import java.util.List;

public interface EsWorld {
    String getName();
    List<EsEntity> getEntities();
    List<EsEntity> getNearbyEntities(EsLocation loc, double xoff, double yoff, double zoff);
    void setTime(long time);
}

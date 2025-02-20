package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.EsLocation;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unused")
public interface EsEntity extends EsCommandSender {
    String getType();
    String getName();
    UUID getUniqueId();
    void teleport(EsLocation loc);
    EsLocation getLocation();
    double getWidth();
    double getHeight();
    boolean leaveVehicle();
    List<EsEntity> getPassengers();
    Set<String> getScoreboardTags();
    void addScoreboardTag(String tag);
    boolean hasScoreboardTag(String tag);
    void setOnFireTicks(int ticks);
    void addPassenger(EsEntity entity);
    void setFallDistance(float dis);
}

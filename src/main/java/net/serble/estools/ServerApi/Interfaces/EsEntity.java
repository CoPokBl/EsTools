package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsLocation;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EsEntity extends EsCommandSender {
    String getType();
    String getName();
    UUID getUniqueId();
    void teleport(EsLocation loc);
    EsLocation getLocation();
    boolean leaveVehicle();
    List<EsEntity> getPassengers();
    Set<String> getScoreboardTags();
    void addScoreboardTag(String tag);
    boolean hasScoreboardTag(String tag);
}

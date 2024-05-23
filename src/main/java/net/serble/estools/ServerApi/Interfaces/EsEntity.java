package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.EsLocation;

import java.util.UUID;

public interface EsEntity extends EsCommandSender {
    String getName();
    UUID getUniqueId();
    void teleport(EsLocation loc);
    EsLocation getLocation();
    boolean leaveVehicle();
}

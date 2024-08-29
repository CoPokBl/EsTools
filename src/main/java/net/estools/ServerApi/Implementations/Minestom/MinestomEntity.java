package net.estools.ServerApi.Implementations.Minestom;

import net.estools.NotImplementedException;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Implementations.Minestom.Helpers.MinestomHelper;
import net.estools.ServerApi.Interfaces.EsEntity;
import net.kyori.adventure.text.TextComponent;
import net.minestom.server.entity.Entity;

import java.util.*;

public class MinestomEntity implements EsEntity {
    private final Entity entity;

    public MinestomEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public String getType() {
        return entity.getEntityType().name();
    }

    @Override
    public String getName() {
        if (entity.getCustomName() == null) {
            return getType();
        }

        if (!(entity.getCustomName() instanceof TextComponent)) {
            return getType();
        }

        return ((TextComponent) entity.getCustomName()).content();
    }

    @Override
    public UUID getUniqueId() {
        return entity.getUuid();
    }

    @Override
    public void teleport(EsLocation loc) {
        entity.teleport(MinestomHelper.toPos(loc));
    }

    @Override
    public EsLocation getLocation() {
        return MinestomHelper.toLocation(entity.getPosition(), entity.getInstance());
    }

    @Override
    public boolean leaveVehicle() {
        if (entity.getVehicle() == null) {
            return false;
        }

        entity.getVehicle().removePassenger(entity);
        return true;
    }

    @Override
    public List<EsEntity> getPassengers() {
        List<EsEntity> passengers = new ArrayList<>();
        for (Entity passenger : entity.getPassengers()) {
            passengers.add(MinestomHelper.toEntity(passenger));
        }
        return passengers;
    }

    @Override
    public Set<String> getScoreboardTags() {
        return new HashSet<>();  // TODO: How do you do this in Minestom?
    }

    @Override
    public void addScoreboardTag(String tag) {
        // TODO
    }

    @Override
    public boolean hasScoreboardTag(String tag) {
        return false;  // TODO
    }

    @Override
    public void setOnFireTicks(int ticks) {
        throw new NotImplementedException();
    }

    @Override
    public void addPassenger(EsEntity passenger) {
        entity.addPassenger(((MinestomEntity) passenger).entity);
    }

    @Override
    public void setFallDistance(float dis) {
        // TODO: Does minestom have this?
    }

    @Override
    public void sendMessage(String... msg) {
        // Entities can't be sent messages in Minestom
    }

    @Override
    public boolean hasPermission(String node) {
        return false;  // Entities don't have permissions in Minestom
    }

    @Override
    public boolean isPermissionSet(String node) {
        return false;  // Entities don't have permissions in Minestom
    }
}

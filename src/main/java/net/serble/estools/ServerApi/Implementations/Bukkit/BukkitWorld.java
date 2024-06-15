package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.Interfaces.EsEntity;
import net.serble.estools.ServerApi.Interfaces.EsWorld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BukkitWorld implements EsWorld {
    private final org.bukkit.World bukkitWorld;

    public BukkitWorld(org.bukkit.World world) {
        bukkitWorld = world;
    }

    @Override
    public String getName() {
        return bukkitWorld.getName();
    }

    @Override
    public List<EsEntity> getEntities() {
        List<org.bukkit.entity.Entity> bEntities = bukkitWorld.getEntities();
        List<EsEntity> entities = new ArrayList<>();
        for (org.bukkit.entity.Entity bEntity : bEntities) {
            entities.add(BukkitHelper.fromBukkitEntity(bEntity));
        }
        return entities;
    }

    @Override
    public List<EsEntity> getNearbyEntities(EsLocation loc, double xoff, double yoff, double zoff) {
        Collection<org.bukkit.entity.Entity> bEntities = bukkitWorld.getNearbyEntities(BukkitHelper.toBukkitLocation(loc), xoff, yoff, zoff);
        List<EsEntity> entities = new ArrayList<>();
        for (org.bukkit.entity.Entity bEntity : bEntities) {
            entities.add(BukkitHelper.fromBukkitEntity(bEntity));
        }
        return entities;
    }

    @Override
    public void setTime(long time) {
        bukkitWorld.setTime(time);
    }

    @Override
    public void setStorming(boolean val) {
        bukkitWorld.setStorm(val);
    }

    @Override
    public void setThundering(boolean val) {
        bukkitWorld.setThundering(val);
    }

    @Override
    public void strikeLightning(EsLocation loc) {
        bukkitWorld.strikeLightning(BukkitHelper.toBukkitLocation(loc));
    }
}

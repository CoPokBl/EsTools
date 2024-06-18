package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helper.BukkitHelper;
import net.serble.estools.ServerApi.Interfaces.EsEntity;
import net.serble.estools.ServerApi.Interfaces.EsWorld;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    private boolean isWithin(EsLocation loc, double xoff, double yoff, double zoff, Entity entity) {
        return Math.abs(loc.getX() - entity.getLocation().getX()) <= xoff &&
                Math.abs(loc.getY() - entity.getLocation().getY()) <= yoff &&
                Math.abs(loc.getZ() - entity.getLocation().getZ()) <= zoff;
    }

    @Override
    public List<EsEntity> getNearbyEntities(EsLocation loc, double xoff, double yoff, double zoff) {
        Collection<Entity> bEntities;
        if (Main.minecraftVersion.getMinor() > 7) {
            bEntities = bukkitWorld.getNearbyEntities(BukkitHelper.toBukkitLocation(loc), xoff, yoff, zoff);
        } else {
            Collection<Entity> allEntities = bukkitWorld.getEntities();
            bEntities = allEntities.stream().filter(e -> isWithin(loc, xoff, yoff, zoff, e)).collect(Collectors.toList());
        }
        List<EsEntity> entities = new ArrayList<>(bEntities.size());
        for (Entity bEntity : bEntities) {
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

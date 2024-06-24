package net.estools.ServerApi.Implementations.Folia;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Implementations.Bukkit.BukkitWorld;
import net.estools.ServerApi.Interfaces.EsEntity;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FoliaWorld extends BukkitWorld {
    private final World bukkitWorld;

    public FoliaWorld(World world) {
        super(world);
        bukkitWorld = world;
    }

    @Override
    public List<EsEntity> getEntities() {
        List<Entity> bEntities = bukkitWorld.getEntities();
        List<EsEntity> entities = new ArrayList<>();
        for (Entity bEntity : bEntities) {
            entities.add(FoliaHelper.fromBukkitEntity(bEntity));
        }
        return entities;
    }

    @Override
    public List<EsEntity> getNearbyEntities(EsLocation loc, double xoff, double yoff, double zoff) {
        Collection<Entity> bEntities = bukkitWorld.getNearbyEntities(FoliaHelper.toBukkitLocation(loc), xoff, yoff, zoff);
        List<EsEntity> entities = new ArrayList<>();
        for (Entity bEntity : bEntities) {
            entities.add(FoliaHelper.fromBukkitEntity(bEntity));
        }
        return entities;
    }

    @Override
    public void setTime(long time) {
        FoliaHelper.runTaskOnNextTick(() -> bukkitWorld.setTime(time));
    }

    @Override
    public void setStorming(boolean val) {
        FoliaHelper.runTaskOnNextTick(() -> bukkitWorld.setStorm(val));
    }

    @Override
    public void setThundering(boolean val) {
        FoliaHelper.runTaskOnNextTick(() -> bukkitWorld.setThundering(val));
    }

    @Override
    public void strikeLightning(EsLocation loc) {
        bukkitWorld.strikeLightning(FoliaHelper.toBukkitLocation(loc));
    }
}

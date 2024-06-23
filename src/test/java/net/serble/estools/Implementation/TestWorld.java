package net.serble.estools.Implementation;

import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.Interfaces.EsEntity;
import net.serble.estools.ServerApi.Interfaces.EsWorld;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")  // For future
public class TestWorld implements EsWorld {
    private final List<EsEntity> entities = new ArrayList<>();
    private final String name;
    private long time;
    private boolean storming = false;
    private boolean thundering = false;
    private final List<EsLocation> lightningBolts = new ArrayList<>();

    // TEST METHODS

    public TestWorld(String name) {
        this.name = name;
    }

    public boolean isStorming() {
        return storming;
    }

    public boolean isThundering() {
        return thundering;
    }

    public long getTime() {
        return time;
    }

    public List<EsLocation> getLightningBolts() {
        return lightningBolts;
    }

    public void addEntity(EsEntity entity) {
        entities.add(entity);
    }

    // IMPLEMENTATIONS

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<EsEntity> getEntities() {
        return entities;
    }

    @Override
    public List<EsEntity> getNearbyEntities(EsLocation loc, double xoff, double yoff, double zoff) {  // Check each entity.getLocation()
        List<EsEntity> nearby = new ArrayList<>();
        for (EsEntity entity : entities) {
            EsLocation enLoc = entity.getLocation();
            if (Math.abs(enLoc.getX() - loc.getX()) <= xoff &&
                    Math.abs(enLoc.getY() - loc.getY()) <= yoff &&
                    Math.abs(enLoc.getZ() - loc.getZ()) <= zoff) {
                nearby.add(entity);
            }
        }
        return nearby;
    }

    @Override
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public void setStorming(boolean val) {
        storming = val;
    }

    @Override
    public void setThundering(boolean val) {
        thundering = val;
    }

    @Override
    public void strikeLightning(EsLocation loc) {
        lightningBolts.add(loc);
    }
}

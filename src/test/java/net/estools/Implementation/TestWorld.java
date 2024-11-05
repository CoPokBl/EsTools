package net.estools.Implementation;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Interfaces.EsBlock;
import net.estools.ServerApi.Interfaces.EsEntity;
import net.estools.ServerApi.Interfaces.EsWorld;
import net.estools.ServerApi.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")  // For future
public class TestWorld implements EsWorld {
    private final List<EsEntity> entities = new ArrayList<>();
    private final List<Position> blocks = new ArrayList<>();
    private final String name;
    private final UUID uuid;
    private long time;
    private boolean storming = false;
    private boolean thundering = false;
    private final List<EsLocation> lightningBolts = new ArrayList<>();

    // TEST METHODS

    public TestWorld(String name) {
        this.name = name;
        uuid = UUID.randomUUID();
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

    public void addBlock(int x, int y, int z) {
        blocks.add(new Position(x, y, z));
    }

    // IMPLEMENTATIONS

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUuid() {
        return uuid;
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
    public EsBlock getHighestBlockAt(int x, int z) {
        Position highest = blocks
                .stream()
                .filter(b -> b.getX() == x && b.getZ() == z)
                .max(Comparator.comparingInt(p -> (int) p.getY()))
                .orElse(null);
        if (highest == null) {
            return null;
        }

        return new TestBlock("stone", (int) highest.getX(), (int) highest.getY(), (int) highest.getZ());
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EsWorld) {
            return ((EsWorld)obj).getUuid().equals(getUuid());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}

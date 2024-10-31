package net.estools.ServerApi;

import net.estools.Main;
import net.estools.ServerApi.Interfaces.EsWorld;

@SuppressWarnings("unused")  // SnakeYAML needs the setters
public class EsLocation extends Position {
    private EsWorld world;
    private String worldName;
    private Position direction;
    private double yaw;
    private double pitch;

    public EsLocation(EsWorld world, double x, double y, double z) {
        this.world = world;
        this.worldName = world.getName();
        setX(x);
        setY(y);
        setZ(z);
    }

    public EsLocation(EsWorld world, Position dir, double x, double y, double z) {
        this.world = world;
        this.worldName = world.getName();
        direction = dir;
        setX(x);
        setY(y);
        setZ(z);
    }

    public EsLocation(EsWorld world, Position dir, Position pos) {
        this(world, dir, pos.getX(), pos.getY(), pos.getZ());
    }

    public EsLocation(EsWorld world, Position dir, double x, double y, double z, double yaw, double pitch) {
        this.world = world;
        this.worldName = world.getName();
        direction = dir;
        this.yaw = yaw;
        this.pitch = pitch;
        setX(x);
        setY(y);
        setZ(z);
    }

    public EsLocation(EsWorld world, Position pos) {
        this(world, pos.getX(), pos.getY(), pos.getZ());
    }

    /** You probably shouldn't use this, it's here for SnakeYAML */
    public EsLocation() { }

    public EsWorld getWorld() {
        return world;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {  // Sets world based on name, for SnakeYAML to correctly use locations
        this.worldName = worldName;
        world = Main.server.getWorld(worldName);
    }

    public double distanceTo(EsLocation other) {
        return super.distanceTo(other);
    }

    public Position getDirection() {
        return direction;
    }

    public void setDirection(Position direction) {
        this.direction = direction;
    }

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public int getBlockX() {
        return (int) Math.floor(getX());
    }

    public int getBlockY() {
        return (int) Math.floor(getY());
    }

    public int getBlockZ() {
        return (int) Math.floor(getZ());
    }

    public EsLocation add(Position other) {
        return add(other.getX(), other.getY(), other.getZ());
    }

    public EsLocation add(double x, double y, double z) {
        return new EsLocation(world, getX() + x, getY() + y, getZ() + z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EsLocation)) {
            return false;
        }
        return toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return String.format("EsLocation{world=%s, x=%f, y=%f, z=%f, yaw=%f, pitch=%f}", worldName, getX(), getY(), getZ(), getYaw(), getPitch());
    }
}

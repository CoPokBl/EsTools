package net.serble.estools.ServerApi;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.Interfaces.EsWorld;

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
        return (int) Math.round(getX());
    }

    public int getBlockY() {
        return (int) Math.round(getY());
    }

    public int getBlockZ() {
        return (int) Math.round(getZ());
    }

    public EsLocation add(Position other) {
        return add(other.getX(), other.getY(), other.getZ());
    }

    public EsLocation add(double x, double y, double z) {
        return new EsLocation(world, getX() + x, getY() + y, getZ() + z);
    }
}

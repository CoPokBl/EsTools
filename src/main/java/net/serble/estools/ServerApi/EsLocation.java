package net.serble.estools.ServerApi;

import net.serble.estools.ServerApi.Interfaces.EsWorld;

public class EsLocation extends Position {
    private final EsWorld world;
    private Position direction;
    private double yaw;
    private double pitch;

    public EsLocation(EsWorld world, double x, double y, double z) {
        super(x, y, z);
        this.world = world;
    }

    public EsLocation(EsWorld world, Position dir, double x, double y, double z) {
        super(x, y, z);
        this.world = world;
        direction = dir;
    }

    public EsLocation(EsWorld world, Position dir, double x, double y, double z, double yaw, double pitch) {
        super(x, y, z);
        this.world = world;
        direction = dir;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public EsWorld getWorld() {
        return world;
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
}

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
        setDirection(dir);
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

    public EsLocation(EsWorld world, Position position) {
        this(world, position.getX(), position.getY(), position.getZ());
    }

    public EsLocation(EsWorld world, Position dir, Position position) {
        this(world, dir, position.getX(), position.getY(), position.getZ());
    }

    public EsLocation(EsWorld world, Position dir, Position position, double yaw, double pitch) {
        this(world, dir, position.getX(), position.getY(), position.getZ(), yaw, pitch);
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

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public void setDirection(Position direction) {
        this.direction = direction;
        updateYawPitchFromDirection();
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
        updateDirectionFromYawPitch();
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
        updateDirectionFromYawPitch();
    }

    private void updateDirectionFromYawPitch() {
        // Convert yaw and pitch to radians
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        // Compute the direction vector based on yaw and pitch
        double cosPitch = Math.cos(pitchRad);
        double sinPitch = Math.sin(pitchRad);
        double cosYaw = Math.cos(yawRad);
        double sinYaw = Math.sin(yawRad);

        this.direction = new Position(
                -cosPitch * sinYaw,  // X direction
                sinPitch,            // Y direction
                cosPitch * cosYaw    // Z direction
        );
    }

    private void updateYawPitchFromDirection() {
        if (direction == null) return;

        double x = direction.getX();
        double y = direction.getY();
        double z = direction.getZ();

        double hypotenuse = Math.sqrt(x * x + z * z);

        // Compute pitch as the angle from the horizontal plane
        this.pitch = Math.toDegrees(-Math.atan2(y, hypotenuse));

        // Compute yaw as the angle around the vertical axis
        this.yaw = Math.toDegrees(Math.atan2(-x, z));  // Adjust signs as needed
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

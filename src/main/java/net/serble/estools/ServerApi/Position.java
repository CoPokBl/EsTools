package net.serble.estools.ServerApi;

public class Position {
    private double x;
    private double y;
    private double z;

    public Position() {

    }

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double distanceTo(Position other) {
        return Math.sqrt(distanceSquared(other));
    }

    public double distanceSquared(Position other) {
        return Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2);
    }

    public Position subtract(Position other) {
        return new Position(x - other.x, y - other.y, z - other.z);
    }

    public Position normalise() {
        double length = length();
        return new Position(x / length, y / length, z / length);
    }

    public Position crossProduct(Position other) {
        return new Position(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }

    public double lengthSquared() {
        return (x * x + y * y + z * z);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double dot(Position other) {
        return x * other.x + y * other.y + z * other.z;
    }
}

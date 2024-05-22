package net.serble.estools;

public class PluginVersion {
    private final int major;
    private final int minor;
    private final int patch;
    private final String string;

    public PluginVersion(String str) {
        String[] parts = str.split("\\.");
        major = Integer.parseInt(parts[0]);
        minor = Integer.parseInt(parts[1]);
        patch = Integer.parseInt(parts[2]);
        string = str;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public String getString() {
        return string;
    }

    public boolean isLowerThan(PluginVersion other) {
        if (getMajor() < other.getMajor()) {
            return true;
        } else if (getMajor() > other.getMajor()) {
            return false;
        }

        if (getMinor() < other.getMinor()) {
            return true;
        } else if (getMinor() > other.getMinor()) {
            return false;
        }

        return getPatch() < other.getPatch();
    }
}

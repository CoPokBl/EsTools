package net.serble.estools;

public class SemanticVersion {
    private final int major;
    private final int minor;
    private final int patch;
    private final String string;

    public SemanticVersion(String str) {
        String[] parts = str.split("\\.");
        major = Integer.parseInt(parts[0]);
        minor = Integer.parseInt(parts[1]);
        patch = Integer.parseInt(parts[2]);
        string = str;
    }

    public SemanticVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        string = major + "." + minor + "." + patch;
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

    public boolean isLowerThan(SemanticVersion other) {
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

    public boolean isAtLeast(SemanticVersion other) {
        if (getMajor() < other.getMajor()) {
            return false;
        } else if (getMajor() > other.getMajor()) {
            return true;
        }

        if (getMinor() < other.getMinor()) {
            return false;
        } else if (getMinor() > other.getMinor()) {
            return true;
        }

        return getPatch() >= other.getPatch();
    }
}

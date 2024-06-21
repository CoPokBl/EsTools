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
        return isLowerThan(other.major, other.minor, other.patch);
    }

    public boolean isLowerThan(int major, int minor, int patch) {
        if (getMajor() < major) {
            return true;
        } else if (getMajor() > major) {
            return false;
        }

        if (getMinor() < minor) {
            return true;
        } else if (getMinor() > minor) {
            return false;
        }

        return getPatch() < patch;
    }

    @SuppressWarnings("unused")  // Future-proof
    public boolean isAtLeast(SemanticVersion other) {
        return isAtLeast(other.major, other.minor, other.patch);
    }

    public boolean isAtLeast(int major, int minor, int patch) {
        if (getMajor() < major) {
            return false;
        } else if (getMajor() > major) {
            return true;
        }

        if (getMinor() < minor) {
            return false;
        } else if (getMinor() > minor) {
            return true;
        }

        return getPatch() >= patch;
    }
}

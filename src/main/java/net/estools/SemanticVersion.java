package net.estools;

@SuppressWarnings("unused")  // Future-proof, it's a util class why not
public class SemanticVersion {
    private final int major;
    private final int minor;
    private final int patch;
    private final String string;

    public SemanticVersion(String str) {
        String[] parts = str.split("\\.");
        major = Integer.parseInt(parts[0]);
        minor = Integer.parseInt(parts[1]);
        patch = Integer.parseInt(parts.length > 2 ? parts[2] : "0");
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

    @Override
    public String toString() {
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

    public boolean isMoreThan(int major, int minor, int patch) {
        if (getMajor() > major) {
            return true;
        } else if (getMajor() < major) {
            return false;
        }

        if (getMinor() > minor) {
            return true;
        } else if (getMinor() < minor) {
            return false;
        }

        return getPatch() > patch;
    }

    public boolean isMoreThan(SemanticVersion other) {
        return isMoreThan(other.major, other.minor, other.patch);
    }
}

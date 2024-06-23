package net.serble.estools;

/**
 * A command schema containing information for the Tester.
 */
public class TestCommand {
    private final String cmd;
    private final double waitAfter;
    private final SemanticVersion minVersion;

    public TestCommand(String cmd, double waitAfter, SemanticVersion minVersion) {
        this.cmd = cmd;
        this.waitAfter = waitAfter;
        this.minVersion = minVersion;
    }

    public TestCommand(String cmd, double waitAfter) {
        this.cmd = cmd;
        this.waitAfter = waitAfter;
        minVersion = null;
    }

    public double getWaitAfter() {
        return waitAfter;
    }

    public SemanticVersion getMinVersion() {
        return minVersion;
    }

    public String getCmd() {
        return cmd;
    }
}

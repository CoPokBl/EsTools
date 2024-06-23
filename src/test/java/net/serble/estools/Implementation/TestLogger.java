package net.serble.estools.Implementation;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.ServerApi.Interfaces.EsLogger;

public class TestLogger implements EsLogger {
    private final EsToolsUnitTest test;

    public TestLogger(EsToolsUnitTest test) {
        this.test = test;
    }

    @Override
    public void debug(String msg) {
        test.consolePrint("[DEBUG] " + msg);
    }

    @Override
    public void info(String msg) {
        test.consolePrint("[INFO] " + msg);
    }

    @Override
    public void warning(String msg) {
        test.consolePrint("[WARN] " + msg);
    }

    @Override
    public void severe(String msg) {
        test.consolePrint("[ERROR] " + msg);
    }
}

package net.serble.estools.Implementation;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.ServerApi.Interfaces.EsLogger;

public class TestLogger implements EsLogger {

    @Override
    public void debug(String msg) {
        EsToolsUnitTest.consolePrint("[DEBUG] " + msg);
    }

    @Override
    public void info(String msg) {
        EsToolsUnitTest.consolePrint("[INFO] " + msg);
    }

    @Override
    public void warning(String msg) {
        EsToolsUnitTest.consolePrint("[WARN] " + msg);
    }

    @Override
    public void severe(String msg) {
        EsToolsUnitTest.consolePrint("[ERROR] " + msg);
    }
}

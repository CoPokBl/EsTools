package net.estools.Implementation;

import net.estools.EsToolsUnitTest;
import net.estools.ServerApi.Interfaces.EsLogger;

public class TestLogger implements EsLogger {

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

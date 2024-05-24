package net.serble.estools.ServerApi.Interfaces;

public interface EsLogger {
    void debug(String msg);
    void info(String msg);
    void warning(String msg);
    void severe(String msg);
}

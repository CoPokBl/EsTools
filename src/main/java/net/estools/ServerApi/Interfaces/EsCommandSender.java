package net.estools.ServerApi.Interfaces;

public interface EsCommandSender {
    void sendMessage(String... msg);
    boolean hasPermission(String node);
    boolean isPermissionSet(String node);
}

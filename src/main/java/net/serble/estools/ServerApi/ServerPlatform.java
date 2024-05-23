package net.serble.estools.ServerApi;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitServer;
import net.serble.estools.ServerApi.Interfaces.EsServerSoftware;

public enum ServerPlatform {
    Bukkit,  // Includes all derivatives
    Folia;

    public EsServerSoftware getServerInstance() {
        switch (this) {
            case Bukkit:
                return new BukkitServer();

            default:
                throw new IllegalArgumentException("Unsupported server software");
        }
    }
}

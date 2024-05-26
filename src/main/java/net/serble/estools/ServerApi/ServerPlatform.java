package net.serble.estools.ServerApi;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitServer;
import net.serble.estools.ServerApi.Implementations.Folia.FoliaServer;
import net.serble.estools.ServerApi.Interfaces.EsServerSoftware;

public enum ServerPlatform {
    Bukkit(true),  // Includes all derivatives
    Folia(true);

    final boolean hasMetrics;

    ServerPlatform(boolean bStats) {
        hasMetrics = bStats;
    }

    public boolean supportsMetrics() {
        return hasMetrics;
    }

    public EsServerSoftware getServerInstance(Object context) {
        switch (this) {
            case Bukkit:
                return new BukkitServer(context);

            case Folia:
                return new FoliaServer(context);

            default:
                throw new IllegalArgumentException("Unsupported server software");
        }
    }
}

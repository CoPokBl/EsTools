package net.serble.estools.ServerApi;

import net.serble.estools.ServerApi.Implementations.Bukkit.BukkitServer;
import net.serble.estools.ServerApi.Implementations.Folia.FoliaServer;
import net.serble.estools.ServerApi.Interfaces.EsServer;

public enum ServerPlatform {
    Bukkit(true),  // Includes all derivatives
    Folia(true),

    /**
     * This is a special platform where the plugin expects the platform to set Main.server themselves.
     */
    Injected(false);

    final boolean hasMetrics;

    ServerPlatform(boolean bStats) {
        hasMetrics = bStats;
    }

    public boolean supportsMetrics() {
        return hasMetrics;
    }

    public EsServer getServerInstance(Object context) {
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

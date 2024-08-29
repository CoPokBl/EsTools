package net.estools.ServerApi;

import net.estools.ServerApi.Implementations.Bukkit.BukkitServer;
import net.estools.ServerApi.Implementations.Folia.FoliaServer;
import net.estools.ServerApi.Interfaces.EsServer;

public enum ServerPlatform {
    Bukkit(true),  // Includes all derivatives
    Folia(true),
    Minestom(false),

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

            case Minestom:
                return null;

            default:
                throw new IllegalArgumentException("Unsupported server software");
        }
    }
}

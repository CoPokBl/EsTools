package net.estools.ServerApi;

import net.estools.Main;

import java.util.UUID;

// Set the props yourself without a constructor, I'm too lazy.
public class EsOfflinePlayer {
    private String name;
    private UUID uuid;
    private boolean banned;
    private long firstPlayed;
    private long lastPlayed;
    private boolean playedBefore;
    private EsLocation respawnLocation;
    private EsLocation lastDeathLocation;
    private EsLocation location;


    public boolean isOnline() {
        return Main.server.getOnlinePlayers().stream().anyMatch(p -> p.getUniqueId().equals(uuid));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public long getFirstPlayed() {
        return firstPlayed;
    }

    public void setFirstPlayed(long firstPlayed) {
        this.firstPlayed = firstPlayed;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public boolean hasPlayedBefore() {
        return playedBefore;
    }

    public void setPlayedBefore(boolean playedBefore) {
        this.playedBefore = playedBefore;
    }

    public EsLocation getRespawnLocation() {
        return respawnLocation;
    }

    public void setRespawnLocation(EsLocation respawnLocation) {
        this.respawnLocation = respawnLocation;
    }

    public EsLocation getLastDeathLocation() {
        return lastDeathLocation;
    }

    public void setLastDeathLocation(EsLocation lastDeathLocation) {
        this.lastDeathLocation = lastDeathLocation;
    }

    public EsLocation getLocation() {
        return location;
    }

    public void setLocation(EsLocation location) {
        this.location = location;
    }
}

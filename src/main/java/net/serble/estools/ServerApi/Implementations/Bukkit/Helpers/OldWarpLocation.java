package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * This class exists for the BukkitConfigMigrator so that it can parse the old warps.yml file.
 */
@SerializableAs("WarpLocation")
public class OldWarpLocation implements ConfigurationSerializable {
    public Location location;
    public String name;
    public boolean global;

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();

        result.put("location", location);
        result.put("name", name);
        result.put("global", global);

        return result;
    }

    @SuppressWarnings("unused")
    public static OldWarpLocation deserialize(Map<String, Object> args) {
        OldWarpLocation warp = new OldWarpLocation();

        warp.location = (Location)args.get("location");
        warp.name = (String)args.get("name");
        warp.global = (boolean)args.get("global");

        return warp;
    }
}

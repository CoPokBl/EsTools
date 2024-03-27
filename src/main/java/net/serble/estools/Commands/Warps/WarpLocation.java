package net.serble.estools.Commands.Warps;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Map;
import java.util.HashMap;

@SerializableAs("WarpLocation")
public class WarpLocation implements ConfigurationSerializable {
    public Location location;
    public String name;
    public boolean global;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();

        result.put("location", location);
        result.put("name", name);
        result.put("global", global);

        return result;
    }

    @SuppressWarnings("unused")
    public static WarpLocation deserialize(Map<String, Object> args) {
        WarpLocation warp = new WarpLocation();

        warp.location = (Location)args.get("location");
        warp.name = (String)args.get("name");
        warp.global = (boolean)args.get("global");

        return warp;
    }
}

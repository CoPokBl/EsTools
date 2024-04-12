package net.serble.estools.Commands.Warps;

import net.serble.estools.CMD;
import net.serble.estools.ConfigManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// this command is /warps
public class WarpManager extends CMD {
    private static final String addSetUsage = genUsage("/warps <add/set> <warp> [LOCAL/global] [x] [y] [z] [yaw] [pitch]");
    private static final String usage = genUsage("/warps <add/set> <warp> [LOCAL/global] [x] [y] [z] [yaw] [pitch]\n" +
                                                      "/warps remove <warp>\n" +
                                                      "/warps list");

    private static final String consoleUsage = genUsage("/warps list");

    public static final HashMap<String, WarpLocation> warps = new HashMap<>();

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(WarpLocation.class, "WarpLocation");

        FileConfiguration f = ConfigManager.load("warps.yml");

        List<?> warpList = f.getList("warps");
        if (warpList == null) {
            return;
        }

        warpList.sort((w1, w2) -> {
            if (w1 instanceof WarpLocation && w2 instanceof WarpLocation) {
                WarpLocation warp1 = (WarpLocation)w1;
                WarpLocation warp2 = (WarpLocation)w1;

                return warp1.name.compareTo(warp2.name);
            }

            return 0;
        });

        warpList.forEach(w -> {
            if (w instanceof WarpLocation) {
                WarpLocation warp = (WarpLocation)w;
                warps.put(warp.name, warp);
            }
        });
    }

    private static void saveWarps() {
        FileConfiguration f = new YamlConfiguration();

        List<WarpLocation> warpList = new ArrayList<>(warps.values());
        f.set("warps", warpList);

        ConfigManager.save("warps.yml", f);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (isNotPlayer(sender, consoleUsage)) {
                return false;
            }

            s(sender, usage);
            return false;
        }

        String command = args[0].toLowerCase();

        if (command.equals("list")) {
            if (warps.isEmpty()) {
                s(sender, "&cThere are no warps.");
                return true;
            }

            StringBuilder warpList = new StringBuilder("&aWarps:");

            for (WarpLocation warp : warps.values()) {
                warpList.append(t("\n&6%s &aat &6%s &ain &6%s &ais &6%s",
                        warp.name, locationToString(warp.location),
                        warp.location.getWorld().getName(), warp.global ? "global" : "local"));
            }

            s(sender, warpList.toString());
            return true;
        }

        if (isNotPlayer(sender, consoleUsage)) {
            return false;
        }

        if (args.length < 2) {
            s(sender, usage);
            return false;
        }

        String warpName = args[1].toLowerCase();
        WarpLocation warp = warps.get(warpName);

        if (command.equals("remove")) {
            if (warp == null) {
                s(sender, "&cWarp does not exist.");
                return false;
            }

            warps.remove(warpName);
            saveWarps();

            s(sender, "&aRemoved warp &6%s&a.", warpName);
            return true;
        }

        boolean global = false;
        if (args.length > 2) {
            switch (args[2].toLowerCase()) {
                case "global":
                    global = true;
                    break;
                case "local":
                    global = false;
                    break;

                default:
                    s(sender, usage);
                    return false;
            }
        }

        switch (args[0].toLowerCase()) {
            case "add": {
                if (warp != null) {
                    s(sender, "&cWarp does not exist, please use add to create it.");
                    return false;
                }

                if (createWarp((Player)sender, warpName, args, global)) {
                    return false;
                }

                s(sender, "&aAdded warp &6%s&a.", warpName);
                break;
            }

            case "set": {
                if (warp == null) {
                    s(sender, "&cWarp does not exist, please use add to create it.");
                    return false;
                }

                if (createWarp((Player)sender, warpName, args, global)) {
                    return false;
                }

                s(sender, "&aModified warp &6%s&a.", warpName);
                break;
            }

            default:
                s(sender, usage);
                return false;
        }
        return true;
    }

    // returns true if failed
    private static boolean createWarp(Player p, String warpName, String[] args, boolean global) {
        // /warps <add/set> <warp> [LOCAL/global] [x] [y] [z] [yaw] [pitch]

        Location loc;
        if (args.length <= 3) {
            loc = p.getLocation();
        }
        else if (args.length < 6) { // if you supplied only some of the coordinates, what are you doing???
            s(p, addSetUsage);
            return true;
        }
        else {
            loc = new Location(p.getWorld(),
                parseCoorinate(args[3], p.getLocation().getX()),
                parseCoorinate(args[4], p.getLocation().getY()),
                parseCoorinate(args[5], p.getLocation().getZ())
            );

            // add pitch and yaw only if there are enough arguments
            try {
                if (args.length >= 7) {
                    loc.setYaw(Float.parseFloat(args[6]));
                }
                else {
                    // default yaw to players look direction
                    loc.setYaw(p.getLocation().getYaw());
                }

                if (args.length >= 8) {
                    loc.setPitch(Float.parseFloat(args[7]));
                }
            } catch (NumberFormatException ignored) {
                s(p, addSetUsage);
                return true;
            }
        }

        WarpLocation warp = new WarpLocation();
        warp.location = loc;
        warp.global = global;
        warp.name = warpName;

        warps.put(warpName, warp);
        saveWarps();

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();
        // /warps <add/set> <warp> [LOCAL/global] [x] [y] [z] [yaw] [pitch]
        // /warps remove <warp>
        // /warps list

        if (args.length == 1) { // <add/set/remove/list>
            tab.add("add");
            tab.add("set");
            tab.add("remove");
            tab.add("list");
        }

        // only do the rest of the tab completion if you are doing add, set or remove
        if (!equalsOr(args[0].toLowerCase(), "add", "set", "remove")) {
            return tab;
        }

        if (args.length == 2 && !args[0].equalsIgnoreCase("add")) { // <warp>
            tab.addAll(warps.keySet());
        }

        if (!equalsOr(args[0].toLowerCase(), "add", "set")) {
            return tab;
        }

        switch (args.length) {
            case 3: // [LOCAL/global]
                tab.add("local");
                tab.add("global");
                break;

            case 4: // [x]
            case 5: // [y]
            case 6: // [z]
                tab.add("~");
                break;

            case 7: // [yaw]
            case 8: // [pitch]
                tab.add("0");
                break;
        }

        return tab;
    }
}

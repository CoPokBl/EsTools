package net.serble.estools.Commands.Warps;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Config.ConfigManager;
import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;
import net.serble.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this command is /warps
public class WarpManager extends EsToolsCommand {
    private static final String addSetUsage = genUsage("/warps <add/set> <warp> [LOCAL/global] [x] [y] [z] [yaw] [pitch]");
    private static final String usage = genUsage("/warps <add/set> <warp> [LOCAL/global] [x] [y] [z] [yaw] [pitch]\n" +
                                                      "/warps remove <warp>\n" +
                                                      "/warps list");

    private static final String consoleUsage = genUsage("/warps list");

    public static Map<String, WarpLocation> warps = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public void onEnable() {
        warps = ConfigManager.load("warps.yml", HashMap.class, WarpLocation.class);
    }

    private static void saveWarps() {
        ConfigManager.save("warps.yml", warps);
    }

    public boolean execute(EsCommandSender sender, String[] args) {
        if (args.length == 0) {
            if (isNotPlayer(sender, consoleUsage)) {
                return false;
            }

            send(sender, usage);
            return false;
        }

        String command = args[0].toLowerCase();

        if (command.equals("list")) {
            if (warps.isEmpty()) {
                send(sender, "&cThere are no warps.");
                return true;
            }

            StringBuilder warpList = new StringBuilder("&aWarps:");

            for (WarpLocation warp : warps.values()) {
                warpList.append(translate("\n&6%s &aat &6%s &ain &6%s &ais &6%s",
                        warp.getName(), locationToString(warp.getLocation()),
                        warp.getLocation().getWorld().getName(), warp.isGlobal() ? "global" : "local"));
            }

            send(sender, warpList.toString());
            return true;
        }

        if (isNotPlayer(sender, consoleUsage)) {
            return false;
        }

        if (args.length < 2) {
            send(sender, usage);
            return false;
        }

        String warpName = args[1].toLowerCase();
        WarpLocation warp = warps.get(warpName);

        if (command.equals("remove")) {
            if (warp == null) {
                send(sender, "&cWarp does not exist.");
                return false;
            }

            warps.remove(warpName);
            saveWarps();

            send(sender, "&aRemoved warp &6%s&a.", warpName);
            return true;
        }

        boolean global = false;
        if (args.length > 2) {
            switch (args[2].toLowerCase()) {
                case "global":
                    global = true;
                    break;

                case "local":
                    break;  // Global is already false

                default:
                    send(sender, usage);
                    return false;
            }
        }

        switch (args[0].toLowerCase()) {
            case "add": {
                if (warp != null) {
                    send(sender, "&cWarp already exists.");
                    return false;
                }

                if (createWarp((EsPlayer)sender, warpName, args, global)) {
                    return false;
                }

                send(sender, "&aAdded warp &6%s&a.", warpName);
                break;
            }

            case "set": {
                if (warp == null) {
                    send(sender, "&cWarp does not exist, please use add to create it.");
                    return false;
                }

                if (createWarp((EsPlayer)sender, warpName, args, global)) {
                    return false;
                }

                send(sender, "&aModified warp &6%s&a.", warpName);
                break;
            }

            default:
                send(sender, usage);
                return false;
        }
        return true;
    }

    // returns true if failed
    private static boolean createWarp(EsPlayer p, String warpName, String[] args, boolean global) {
        // /warps <add/set> <warp> [LOCAL/global] [x] [y] [z] [yaw] [pitch]

        EsLocation loc;
        if (args.length <= 3) {
            loc = p.getLocation();
        }
        else if (args.length < 6) { // if you supplied only some of the coordinates, what are you doing???
            send(p, addSetUsage);
            return true;
        }
        else {
            loc = new EsLocation(p.getWorld(),
                parseCoordinate(args[3], p.getLocation().getX()),
                parseCoordinate(args[4], p.getLocation().getY()),
                parseCoordinate(args[5], p.getLocation().getZ())
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
                send(p, addSetUsage);
                return true;
            }
        }

        WarpLocation warp = new WarpLocation();
        warp.setLocation(loc);
        warp.setGlobal(global);
        warp.setName(warpName);

        warps.put(warpName, warp);
        saveWarps();

        return false;
    }

    @Override
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
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

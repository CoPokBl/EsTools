package net.serble.estools.Commands.Warps;

import net.serble.estools.EsToolsCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Warp extends EsToolsCommand {
    private static final String usage = genUsage("/warp <warp>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        Player p = (Player)sender;

        if (args.length == 0) {
            send(p, usage);
            return false;
        }

        String warpName = args[0].toLowerCase();
        WarpLocation warp = WarpManager.warps.get(warpName);

        if (args.length != 1) {
            send(p, usage);
            return false;
        }

        if (!canUseWarp(p, warp)) {
            send(p, "&cWarp &6%s&c does not exist.", warpName);
            return false;
        }

        p.teleport(warp.location);
        send(p, "&aTeleported to warp &6%s&a.", warpName);
        return true;
    }

    private static boolean canUseWarp(Player p, WarpLocation warp) {
        if (warp == null) {
            return false;
        }

        boolean hasManage = p.hasPermission("estools.warps.manage");
        boolean hasDefault = p.hasPermission("estools.warps.default");
        String warpPermission = "estools.warp." + warp.name;

        // you can only use global warps if you are in the same world, or have manage permission
        if (!warp.global && !hasManage && !p.getWorld().equals(warp.location.getWorld())) {
            return false;
        }

        // If you don't have a warp specific permission, you need the default permission
        return (!p.isPermissionSet(warpPermission) && hasDefault) ||
                (p.isPermissionSet(warpPermission) && p.hasPermission(warpPermission));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        Player p = (Player)sender;

        if (args.length == 1) {
            for (WarpLocation warp : WarpManager.warps.values()) {
                boolean inSameWorld = p.getWorld().equals(warp.location.getWorld());

                if (canUseWarp(p, warp) && (warp.global || inSameWorld)) {
                    tab.add(warp.name);
                }
            }
        }

        return tab;
    }
}

package net.estools.Commands.Warps;

import net.estools.EsToolsCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.List;

public class Warp extends EsToolsCommand {
    private static final String usage = genUsage("/warp <warp>");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (isNotPlayer(sender)) {
            return false;
        }

        EsPlayer p = (EsPlayer) sender;

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

        p.teleport(warp.getLocation());
        send(p, "&aTeleported to warp &6%s&a.", warpName);
        return true;
    }

    private static boolean canUseWarp(EsPlayer p, WarpLocation warp) {
        if (warp == null) {
            return false;
        }

        boolean hasManage = p.hasPermission("estools.warps.manage");
        boolean hasDefault = p.hasPermission("estools.warps.default");
        String warpPermission = "estools.warp." + warp.getName();

        // you can only use global warps if you are in the same world, or have manage permission
        if (!warp.isGlobal() && !hasManage && !p.getWorld().equals(warp.getLocation().getWorld())) {
            return false;
        }

        // If you don't have a warp specific permission, you need the default permission
        return (!p.isPermissionSet(warpPermission) && hasDefault) ||
                (p.isPermissionSet(warpPermission) && p.hasPermission(warpPermission));
    }

    @Override
    public List<String> tabComplete(EsCommandSender sender, String[] args, String lArg) {
        List<String> tab = new ArrayList<>();

        EsPlayer p = (EsPlayer)sender;

        if (args.length == 1) {
            for (WarpLocation warp : WarpManager.warps.values()) {
                boolean inSameWorld = p.getWorld().equals(warp.getLocation().getWorld());

                if (canUseWarp(p, warp) && (warp.isGlobal() || inSameWorld)) {
                    tab.add(warp.getName());
                }
            }
        }

        return tab;
    }
}

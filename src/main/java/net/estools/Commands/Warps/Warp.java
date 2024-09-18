package net.estools.Commands.Warps;

import net.estools.MultiPlayerCommand;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warp extends MultiPlayerCommand {
    private static final String usage = genUsage("/warp <warp> [player] [player2] [player3]");
    private static final String consoleUsage = genUsage("/warp <warp> <player> [player2] [player3]");

    @Override
    public boolean execute(EsCommandSender sender, String[] args) {
        if (args.length == 0) {
            send(sender, sender instanceof EsPlayer ? usage : consoleUsage);
            return false;
        }

        List<EsPlayer> players;
        if (args.length >= 2) {
            players = getPlayers(sender, removeArgs(args, 1));

            if (players.isEmpty()) {
                return false;
            }
        } else {
            if (isNotPlayer(sender)) {
                return false;
            }

            players = Collections.singletonList((EsPlayer) sender);
        }

        String warpName = args[0].toLowerCase();
        WarpLocation warp = WarpManager.warps.get(warpName);

        if (!canUseWarp(sender, warp)) {
            send(sender, "&cWarp &6%s&c does not exist.", warpName);
            return false;
        }

        for (EsPlayer player : players) {
            player.teleport(warp.getLocation());
        }

        send(sender, "&aTeleported to warp &6%s&a.", warpName);
        return true;
    }

    private static boolean canUseWarp(EsCommandSender sender, WarpLocation warp) {
        if (warp == null) {
            return false;
        }

        boolean hasManage = sender.hasPermission("estools.warps.manage");
        boolean hasDefault = sender.hasPermission("estools.warps.default");
        String warpPermission = "estools.warp." + warp.getName();

        boolean sameWorld = true;
        if (sender instanceof EsPlayer) {
            sameWorld = ((EsPlayer)sender).getWorld().equals(warp.getLocation().getWorld());
        }

        // you can only use global warps if you are in the same world, or have manage permission
        if (!warp.isGlobal() && !hasManage && sameWorld) {
            return false;
        }

        // If you don't have a warp specific permission, you need the default permission
        return (!sender.isPermissionSet(warpPermission) && hasDefault) ||
                (sender.isPermissionSet(warpPermission) && sender.hasPermission(warpPermission));
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
        } else {
            return super.tabComplete(sender, args, lArg);
        }

        return tab;
    }
}

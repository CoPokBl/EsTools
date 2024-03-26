package net.serble.estools.Commands.Teleport;

import net.serble.estools.EntityCommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class TP extends EntityCommand {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (isNotPlayer(sender)) {
                return false;
            }

            LivingEntity target = getEntity(sender, args[0]);
            if (target == null) {
                return false;
            }

            ((Player)sender).teleport(target);
            s(sender, "&aTeleported to %s", getEntityName(target));
            return true;
        }

        if (args.length == 2) {
            LivingEntity player = getEntity(sender, args[0]);
            LivingEntity target = getEntity(sender, args[1]);

            if (target == null || player == null) {
                return false;
            }

            player.teleport(target);
            s(sender, "&aTeleported %s to %s", getEntityName(player), getEntityName(target));
            return true;
        }

        if (args.length == 3) {
            if (isNotPlayer(sender)) {
                return false;
            }

            Player player = (Player)sender;
            Location loc = new Location(player.getWorld(),
                parseCoorinate(args[0], player.getLocation().getX()),
                parseCoorinate(args[1], player.getLocation().getY()),
                parseCoorinate(args[2], player.getLocation().getZ())
            );

            player.teleport(loc);
            s(sender, "&aTeleported to %s", locToString(loc));
            return true;
        }

        if (args.length == 4) {
            LivingEntity player = getEntity(sender, args[0]);
            if (player == null) {
                return false;
            }

            Location loc = new Location(player.getWorld(),
                parseCoorinate(args[1], player.getLocation().getX()),
                parseCoorinate(args[2], player.getLocation().getY()),
                parseCoorinate(args[3], player.getLocation().getZ())
            );

            player.teleport(loc);
            s(sender, "&aTeleported %s to %s", getEntityName(player), locToString(loc));
            return true;
        }

        s(sender, "&cInvalid arguments.");
        return true;
    }

    private double parseCoorinate(String coord, double playerLoc) {
        if (coord.startsWith("~")) {
            return tryParseDouble(coord.substring(1), 0) + playerLoc;
        }

        return tryParseDouble(coord, 0);
    }

    private String locToString(Location loc) {
        return String.format("%d %d %d", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}

package net.estools.ServerApi.Implementations.Bukkit;

import net.estools.ServerApi.EsCommand.EsRelativePosition;
import net.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Position;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

public class BukkitRelativePosition extends EsRelativePosition {
    @Override
    public Position fromSender(EsCommandSender esSender) {
        CommandSender sender = BukkitHelper.toBukkitCommandSender(esSender);

        if (getRelativeTypeX().equals(RelativeType.SENDER_LOOK)) {
            Position direction = BukkitHelper.fromVector(getSenderLook(sender));
            Position senderPosition = BukkitHelper.fromVector(getSenderPosition(sender));

            Vector3f dir = aroundPosition(new Vector3f((float)direction.getX(), (float)direction.getY(), (float)direction.getZ()),
                    new Vector3f((float)x(), (float)y(), (float)z()));

            return new Position(dir.x, dir.y, dir.z).add(senderPosition);
        }

        Position output = new Position();
        for (int i = 0; i < 3; i++) {
            setSenderValue(output, i, sender);
        }

        return output;
    }

    private Vector3f aroundPosition(Vector3f direction, Vector3f input) {
        // Compute the right vector (perpendicular to the direction and up vector)
        Vector3f right = new Vector3f(direction.z, 0, -direction.x);
        right.normalize();

        // Compute the up vector (perpendicular to the direction and right vectors)
        Vector3f up = new Vector3f();
        right.cross(direction, up);
        up.normalize();

        // Calculate the movement vector
        Vector3f movement = new Vector3f();
        movement.add(right.mul(-input.x));  // Move right
        movement.add(up.mul(-input.y));     // Move up
        movement.add(direction.mul(input.z)); // Move forward

        return movement;
    }

    private void setSenderValue(Position output, int index, CommandSender sender) {
        switch (getRelativeType(index)) {
            case GLOBAL: setPositionPos(output, index, getPos(index)); break;
            case SENDER: {
                Position senderPos = BukkitHelper.fromVector(getSenderPosition(sender));
                setPositionPos(output, index, getPos(index) + getPositionPos(senderPos, index));
            } break;
            // SENDER_LOOK is handled elsewhere
        }
    }

    private Vector getSenderPosition(CommandSender sender) {
        if (sender instanceof Entity) {
            return ((Entity) sender).getLocation().toVector();
        } else if (sender instanceof BlockCommandSender) {
            return ((BlockCommandSender) sender).getBlock().getLocation().toVector();
        } else if (sender instanceof ProxiedCommandSender) {
            return getSenderPosition(((ProxiedCommandSender) sender).getCallee());
        }

        return new Vector();
    }

    private Vector getSenderLook(CommandSender sender) {
        if (sender instanceof Entity) {
            return ((Entity) sender).getLocation().getDirection();
        } else if (sender instanceof ProxiedCommandSender) {
            return getSenderLook(((ProxiedCommandSender) sender).getCallee());
        }

        return new Vector(0, 0, 1);
    }
}

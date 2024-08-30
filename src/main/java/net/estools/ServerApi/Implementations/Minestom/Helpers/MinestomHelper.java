package net.estools.ServerApi.Implementations.Minestom.Helpers;

import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.Implementations.Minestom.*;
import net.estools.ServerApi.Interfaces.EsCommandSender;
import net.estools.ServerApi.Interfaces.EsPlayer;
import net.estools.ServerApi.Position;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;

public class MinestomHelper {

    public static EsPlayer getPlayer(Player player) {
        return null;
    }

    public static Pos toPos(EsLocation loc) {
        return new Pos(
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                (float) loc.getYaw(),
                (float) loc.getPitch());
    }

    public static MinestomWorld toWorld(Instance instance) {
        return new MinestomWorld(instance);
    }

    public static Position toEsPosition(Vec pos) {
        return new Position(
                pos.x(),
                pos.y(),
                pos.z());
    }

    public static EsLocation toLocation(Pos pos, Instance world) {
        return new EsLocation(
                toWorld(world),
                toEsPosition(pos.direction()),
                pos.x(),
                pos.y(),
                pos.z(),
                pos.yaw(),
                pos.pitch());
    }

    public static MinestomEntity toEntity(Entity entity) {
        if (entity instanceof Player) {

        }
        if (entity instanceof LivingEntity) {
            return new MinestomLivingEntity((LivingEntity) entity);
        }
        return new MinestomEntity(entity);
    }

    public static MinestomItemStack toItem(ItemStack item) {
        return new MinestomItemStack(item);
    }

    public static CommandSender getUnderlyingSender(EsCommandSender sender) {
        if (sender instanceof MinestomPlayer) {
            return ((MinestomPlayer) sender).getInternal();
        }
        if (sender instanceof MinestomConsoleSender) {
            return MinecraftServer.getCommandManager().getConsoleSender();
        }
        throw new UnsupportedOperationException("Unsupported sender type: " + sender.getClass().getName());
    }
}

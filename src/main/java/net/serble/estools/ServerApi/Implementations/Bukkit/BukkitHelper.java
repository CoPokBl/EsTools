package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Position;
import net.serble.estools.ServerApi.EsGameMode;
import net.serble.estools.ServerApi.Interfaces.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;

public class BukkitHelper {
    public static Location toBukkitLocation(EsLocation loc) {
        return new Location(Bukkit.getWorld(loc.getWorld().getName()), loc.getX(), loc.getY(), loc.getZ());
    }

    public static EsLocation fromBukkitLocation(Location loc) {
        return new EsLocation(
                new BukkitWorld(loc.getWorld()),
                fromVector(loc.getDirection()),
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch());
    }

    public static Position fromVector(Vector vec) {
        return new Position(vec.getX(), vec.getY(), vec.getZ());
    }

    public static EsCommandSender fromBukkitCommandSender(org.bukkit.command.CommandSender sender) {
        if (sender instanceof Player) {
            return new BukkitPlayer((Player) sender);
        }

        if (sender instanceof LivingEntity) {
            return new BukkitLivingEntity((org.bukkit.entity.LivingEntity) sender);
        }

        if (sender instanceof Entity) {
            return new BukkitEntity((org.bukkit.entity.Entity) sender);
        }

        if (sender instanceof ConsoleCommandSender) {
            return new BukkitConsoleSender((ConsoleCommandSender) sender);
        }

        if (sender instanceof BlockCommandSender) {
            return new BukkitCommandBlockSender((BlockCommandSender) sender);
        }

        throw new RuntimeException("Unrecognised command sender");
    }

    public static CommandSender toBukkitCommandSender(EsCommandSender sender) {
        if (sender instanceof EsPlayer) {
            return ((BukkitPlayer) sender).getBukkitPlayer();
        }

        if (sender instanceof EsLivingEntity) {
            return ((BukkitLivingEntity) sender).getBukkitEntity();
        }

        if (sender instanceof EsEntity) {
            return ((BukkitEntity) sender).getBukkitEntity();
        }

        if (sender instanceof EsConsoleSender) {
            return Bukkit.getConsoleSender();
        }

        if (sender instanceof EsCommandBlockSender) {
            return ((BukkitCommandBlockSender) sender).getBukkitSender();
        }

        throw new RuntimeException("Unrecognised command sender");
    }

    public static EsEntity fromBukkitEntity(org.bukkit.entity.Entity entity) {
        if (entity instanceof org.bukkit.entity.LivingEntity) {
            return new BukkitLivingEntity((LivingEntity) entity);
        }

        return new BukkitEntity(entity);
    }

    public static GameMode toBukkitGameMode(EsGameMode mode) {
        switch (mode) {
            case Creative:
                return GameMode.CREATIVE;
            case Survival:
                return GameMode.SURVIVAL;
            case Adventure:
                return GameMode.ADVENTURE;
            case Spectator:
                return GameMode.SPECTATOR;
        }

        throw new RuntimeException("Invalid GameMode");
    }

    public static EsGameMode fromBukkitGameMode(GameMode mode) {
        switch (mode) {
            case CREATIVE:
                return EsGameMode.Creative;
            case SURVIVAL:
                return EsGameMode.Survival;
            case ADVENTURE:
                return EsGameMode.Adventure;
            case SPECTATOR:
                return EsGameMode.Spectator;
        }

        throw new RuntimeException("Invalid GameMode");
    }

    public static EsBlock fromBukkitBlock(Block block) {
        BlockState state = block.getState();

        if (state instanceof Sign) {
            return new BukkitSign((Sign) state);
        }

        return new BukkitBlock(state);
    }

    @SuppressWarnings("deprecation")
    public static Enchantment getBukkitEnchantment(String name) {
        if (Main.minecraftVersion.getMinor() >= 13) {
            Objects.requireNonNull(Registry.ENCHANTMENT.get(NamespacedKey.minecraft(name)));
        }

        // We have to use deprecated method for pre 1.13
        return Enchantment.getByName(name);
    }
}

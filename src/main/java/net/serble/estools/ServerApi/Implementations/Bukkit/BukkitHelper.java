package net.serble.estools.ServerApi.Implementations.Bukkit;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.ServerApi.*;
import net.serble.estools.Main;
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
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class BukkitHelper {
    public static Location toBukkitLocation(EsLocation loc) {
        Location bLoc = new Location(Bukkit.getWorld(loc.getWorld().getName()), loc.getX(), loc.getY(), loc.getZ());
        if (loc.getDirection() != null) bLoc.setDirection(toVector(loc.getDirection()));
        bLoc.setPitch((float) loc.getPitch());
        bLoc.setYaw((float) loc.getYaw());
        return bLoc;
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

    public static Vector toVector(Position pos) {
        return new Vector(pos.getX(), pos.getY(), pos.getZ());
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

    public static PlayerTeleportEvent.TeleportCause toBukkitTeleportCause(EsTeleportCause esCause) {
        switch (esCause) {
            case EnderPearl:
                return PlayerTeleportEvent.TeleportCause.ENDER_PEARL;
            case Command:
                return PlayerTeleportEvent.TeleportCause.COMMAND;
            case Plugin:
                return PlayerTeleportEvent.TeleportCause.PLUGIN;
            case NetherPortal:
                return PlayerTeleportEvent.TeleportCause.NETHER_PORTAL;
            case EndPortal:
                return PlayerTeleportEvent.TeleportCause.END_PORTAL;
            case Spectate:
                return PlayerTeleportEvent.TeleportCause.SPECTATE;
            case EndGateway:
                return PlayerTeleportEvent.TeleportCause.END_GATEWAY;
            case ChorusFruit:
                return PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT;
            case Dismount:
                return PlayerTeleportEvent.TeleportCause.DISMOUNT;
            case ExitBed:
                return PlayerTeleportEvent.TeleportCause.EXIT_BED;
            case Unknown:
                return PlayerTeleportEvent.TeleportCause.UNKNOWN;
            default:
                throw new IllegalArgumentException("Unknown EsTeleportCause: " + esCause);
        }
    }

    public static EsTeleportCause fromBukkitTeleportCause(PlayerTeleportEvent.TeleportCause bukkitCause) {
        switch (bukkitCause) {
            case ENDER_PEARL:
                return EsTeleportCause.EnderPearl;
            case COMMAND:
                return EsTeleportCause.Command;
            case PLUGIN:
                return EsTeleportCause.Plugin;
            case NETHER_PORTAL:
                return EsTeleportCause.NetherPortal;
            case END_PORTAL:
                return EsTeleportCause.EndPortal;
            case SPECTATE:
                return EsTeleportCause.Spectate;
            case END_GATEWAY:
                return EsTeleportCause.EndGateway;
            case CHORUS_FRUIT:
                return EsTeleportCause.ChorusFruit;
            case DISMOUNT:
                return EsTeleportCause.Dismount;
            case EXIT_BED:
                return EsTeleportCause.ExitBed;
            case UNKNOWN:
                return EsTeleportCause.Unknown;
            default:
                throw new IllegalArgumentException("Invalid TeleportCause");
        }
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
            Enchantment ench = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(name));
            if (ench == null) {
                // Dump info and throw
                Bukkit.getLogger().severe("Failed to find enchantment: " + name);
                for (Enchantment e : Registry.ENCHANTMENT) {
                    Bukkit.getLogger().severe("This exists: " + e.getName());
                }
                throw new RuntimeException("Could not find enchantment: " + name);
            }
        }

        // We have to use deprecated method for pre 1.13
        return Enchantment.getByName(BukkitEnchantmentsHelper.getByName(name));
    }

    public static PersistentDataType toBukkitPersistentDataType(EsPersistentDataType type) {
        switch (type) {
            case Byte:
                return PersistentDataType.BYTE;
            case Long:
                return PersistentDataType.LONG;
            case Float:
                return PersistentDataType.FLOAT;
            case Short:
                return PersistentDataType.SHORT;
            case Double:
                return PersistentDataType.DOUBLE;
            case String:
                return PersistentDataType.STRING;
            case Boolean:
                return PersistentDataType.BOOLEAN;
            case Integer:
                return PersistentDataType.INTEGER;
            case IntArray:
                return PersistentDataType.INTEGER_ARRAY;
            case ByteArray:
                return PersistentDataType.BYTE_ARRAY;
            case LongArray:
                return PersistentDataType.LONG_ARRAY;
        }

        throw new RuntimeException("Unsupported data type");
    }

    @SuppressWarnings("UnstableApiUsage")
    public static NamespacedKey getNamespacedKey(String keyString) {
        if (Main.minecraftVersion.getMinor() >= 16) {
            return NamespacedKey.fromString(keyString, EsToolsBukkit.plugin);
        }

        String[] parts = keyString.split(":");
        if (parts.length == 2) {
            return new NamespacedKey(parts[0], parts[1]);
        } else if (parts.length == 1) {  // Incorrectly formatted key
            String pluginName = Main.server.getPluginName();
            return new NamespacedKey(pluginName, parts[0]);
        }

        return null;
    }

    public static EsBlock fromBukkitBlock(BlockState block) {
        if (block instanceof Sign) {
            return new BukkitSign((Sign) block);
        }

        return new BukkitBlock(block);
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static EquipmentSlot toBukkitEquipmentSlot(EsEquipmentSlot slot) {
        switch (slot) {
            case Feet:
                return EquipmentSlot.FEET;
            case Hand:
                return EquipmentSlot.HAND;
            case Head:
                return EquipmentSlot.HEAD;
            case legs:
                return EquipmentSlot.LEGS;
            case Chest:
                return EquipmentSlot.CHEST;
            case OffHand:
                return EquipmentSlot.OFF_HAND;
        }

        throw new RuntimeException("idfk");
    }

    public static EsEquipmentSlot fromBukkitEquipmentSlot(EquipmentSlot slot) {
        switch (slot) {
            case FEET:
                return EsEquipmentSlot.Feet;
            case HAND:
                return EsEquipmentSlot.Hand;
            case HEAD:
                return EsEquipmentSlot.Head;
            case LEGS:
                return EsEquipmentSlot.legs;
            case CHEST:
                return EsEquipmentSlot.Chest;
            case OFF_HAND:
                return EsEquipmentSlot.OffHand;
            default:
                throw new IllegalArgumentException("Invalid EquipmentSlot");
        }
    }
}

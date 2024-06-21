package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.SemanticVersion;
import net.serble.estools.ServerApi.*;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.Implementations.Bukkit.*;
import net.serble.estools.ServerApi.Interfaces.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

// DO NOT IMPORT THE FOLLOWING BECAUSE THEY BREAK OLDER VERSIONS BECAUSE THEY DON'T EXIST
// org.bukkit.inventory.EquipmentSlot


@SuppressWarnings("unused")
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

    public static Sound toBukkitSound(EsSound sound) {
        if (Main.minecraftVersion.isAtLeast(new SemanticVersion(1, 16, 4))) {
            return Registry.SOUNDS.get(NamespacedKey.minecraft(sound.getKey()));
        } else {
            return Sound.valueOf(BukkitSoundEnumConverter.convertKeyToEnum(sound));
        }
    }

    public static EsSound fromBukkitSound(Sound sound) {
        if (Main.minecraftVersion.isAtLeast(new SemanticVersion(1, 16, 4))) {
            return EsSound.createUnchecked(sound.getKey().getKey());
        } else {
            return BukkitSoundEnumConverter.convertEnumToKey(sound.name());
        }
    }

    public static Position fromVector(Vector vec) {
        return new Position(vec.getX(), vec.getY(), vec.getZ());
    }

    public static Vector toVector(Position pos) {
        return new Vector(pos.getX(), pos.getY(), pos.getZ());
    }

    public static EsCommandSender fromBukkitCommandSender(CommandSender sender) {
        if (sender == null) {
            return null;
        }

        if (sender instanceof Player) {
            return new BukkitPlayer((Player) sender);
        }

        if (sender instanceof LivingEntity) {
            return new BukkitLivingEntity((LivingEntity) sender);
        }

        if (sender instanceof Entity) {
            return new BukkitEntity((Entity) sender);
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

    public static EsEntity fromBukkitEntity(Entity entity) {
        if (entity instanceof Player) {
            return new BukkitPlayer((Player) entity);
        }

        if (entity instanceof LivingEntity) {
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
        if (block == null) {
            return null;
        }

        BlockState state = block.getState();
        return fromBukkitBlock(state);
    }

    @SuppressWarnings("rawtypes")
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
        if (Main.minecraftVersion.getMinor() >= 17) {
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
        if (block == null) {
            return null;
        }

        if (block instanceof Sign) {
            return new BukkitSign((Sign) block);
        }

        return new BukkitBlock(block);
    }

    public static EsItemStack fromBukkitItem(ItemStack item) {
        if (item == null) {
            return null;
        }

        if (Main.minecraftVersion.getMinor() >= 9) {
            if (item.getItemMeta() != null && item.getItemMeta() instanceof PotionMeta) {
                return new BukkitPotion(item);
            }

            return new BukkitItemStack(item);
        } else if (Main.minecraftVersion.getMinor() >= 4) {
            if (item.getType().name().endsWith("POTION")) {
                return new BukkitPotion(item);
            }

            return new BukkitItemStack(item);
        } else {
            throw new RuntimeException("Potions aren't support in this Minecraft version");
        }
    }

    public static EsPotType fromBukkitPotType(Material type) {
        return fromBukkitPotType(type.name());
    }

    public static EsPotType fromBukkitPotType(String type) {
        switch (type) {
            case "SPLASH_POTION":
                return EsPotType.splash;
            case "POTION":
                return EsPotType.drink;
            case "LINGERING_POTION":
                return EsPotType.lingering;
        }

        return null;
    }

    public static PotionEffect toBukkitPotionEffect(EsPotionEffect effect) {
        return new PotionEffect(BukkitEffectHelper.toBukkitEffectType(effect.getType()), effect.getDuration(), effect.getAmp());
    }

    public static EsPotionEffect fromBukkitPotionEffect(PotionEffect effect) {
        return new EsPotionEffect(BukkitEffectHelper.fromBukkitEffectType(effect.getType()), effect.getAmplifier(), effect.getDuration());
    }

    public static Material toBukkitMaterial(EsMaterial mat) {
        if (Main.minecraftVersion.getMinor() > 13) {
            return Registry.MATERIAL.get(NamespacedKey.minecraft(mat.getKey()));
        }

        return Material.valueOf(mat.getKey().toUpperCase());
    }

    public static EsMaterial fromBukkitMaterial(Material mat) {
        if (Main.minecraftVersion.getMinor() > 13) {
            return EsMaterial.createUnchecked(mat.getKey().getKey());
        }

        return EsMaterial.createUnchecked(mat.name());
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static EsClickType fromBukkitClickType(ClickType click) {
        switch (click) {
            case CREATIVE:
                return EsClickType.Creative;
            case DROP:
                return EsClickType.Drop;
            case LEFT:
                return EsClickType.Left;
            case RIGHT:
                return EsClickType.Right;
            case SHIFT_LEFT:
                return EsClickType.ShiftLeft;
            case SHIFT_RIGHT:
                return EsClickType.ShiftRight;
            case MIDDLE:
                return EsClickType.Middle;
            case UNKNOWN:
                return EsClickType.Unknown;
            case NUMBER_KEY:
                return EsClickType.NumberKey;
            case CONTROL_DROP:
                return EsClickType.ControlDrop;
            case DOUBLE_CLICK:
                return EsClickType.DoubleClick;
            case SWAP_OFFHAND:
                return EsClickType.SwapOffhand;
            case WINDOW_BORDER_LEFT:
                return EsClickType.WindowBorderLeft;
            case WINDOW_BORDER_RIGHT:
                return EsClickType.WindowBorderRight;
            default:
                throw new RuntimeException("Invalid click type");
        }
    }

    public static ClickType toBukkitClickType(EsClickType esClick) {
        switch (esClick) {
            case Creative:
                return ClickType.CREATIVE;
            case Drop:
                return ClickType.DROP;
            case Left:
                return ClickType.LEFT;
            case Right:
                return ClickType.RIGHT;
            case ShiftLeft:
                return ClickType.SHIFT_LEFT;
            case ShiftRight:
                return ClickType.SHIFT_RIGHT;
            case Middle:
                return ClickType.MIDDLE;
            case Unknown:
                return ClickType.UNKNOWN;
            case NumberKey:
                return ClickType.NUMBER_KEY;
            case ControlDrop:
                return ClickType.CONTROL_DROP;
            case DoubleClick:
                return ClickType.DOUBLE_CLICK;
            case SwapOffhand:
                return ClickType.SWAP_OFFHAND;
            case WindowBorderLeft:
                return ClickType.WINDOW_BORDER_LEFT;
            case WindowBorderRight:
                return ClickType.WINDOW_BORDER_RIGHT;
            default:
                throw new IllegalArgumentException("Invalid EsClickType");
        }
    }

    public static EsInventoryAction fromBukkitInventoryAction(InventoryAction action) {
        switch (action) {
            case NOTHING:
                return EsInventoryAction.Nothing;
            case PICKUP_ALL:
                return EsInventoryAction.PickupAll;
            case PICKUP_SOME:
                return EsInventoryAction.PickupSome;
            case PICKUP_HALF:
                return EsInventoryAction.PickupHalf;
            case PICKUP_ONE:
                return EsInventoryAction.PickupOne;
            case PLACE_ALL:
                return EsInventoryAction.PlaceAll;
            case PLACE_SOME:
                return EsInventoryAction.PlaceSome;
            case PLACE_ONE:
                return EsInventoryAction.PlaceOne;
            case SWAP_WITH_CURSOR:
                return EsInventoryAction.SwapWithCursor;
            case DROP_ALL_CURSOR:
                return EsInventoryAction.DropAllCursor;
            case DROP_ONE_CURSOR:
                return EsInventoryAction.DropOneCursor;
            case DROP_ALL_SLOT:
                return EsInventoryAction.DropAllSlot;
            case DROP_ONE_SLOT:
                return EsInventoryAction.DropOneSlot;
            case MOVE_TO_OTHER_INVENTORY:
                return EsInventoryAction.MoveToOtherInventory;
            case HOTBAR_MOVE_AND_READD:
                return EsInventoryAction.HotbarMoveAndReadd;
            case HOTBAR_SWAP:
                return EsInventoryAction.HotbarSwap;
            case CLONE_STACK:
                return EsInventoryAction.CloneStack;
            case COLLECT_TO_CURSOR:
                return EsInventoryAction.CollectToCursor;
            case UNKNOWN:
                return EsInventoryAction.Unknown;
            default:
                throw new IllegalArgumentException("Invalid InventoryAction");
        }
    }

    public static InventoryAction toBukkitInventoryAction(EsInventoryAction esAction) {
        switch (esAction) {
            case Nothing:
                return InventoryAction.NOTHING;
            case PickupAll:
                return InventoryAction.PICKUP_ALL;
            case PickupSome:
                return InventoryAction.PICKUP_SOME;
            case PickupHalf:
                return InventoryAction.PICKUP_HALF;
            case PickupOne:
                return InventoryAction.PICKUP_ONE;
            case PlaceAll:
                return InventoryAction.PLACE_ALL;
            case PlaceSome:
                return InventoryAction.PLACE_SOME;
            case PlaceOne:
                return InventoryAction.PLACE_ONE;
            case SwapWithCursor:
                return InventoryAction.SWAP_WITH_CURSOR;
            case DropAllCursor:
                return InventoryAction.DROP_ALL_CURSOR;
            case DropOneCursor:
                return InventoryAction.DROP_ONE_CURSOR;
            case DropAllSlot:
                return InventoryAction.DROP_ALL_SLOT;
            case DropOneSlot:
                return InventoryAction.DROP_ONE_SLOT;
            case MoveToOtherInventory:
                return InventoryAction.MOVE_TO_OTHER_INVENTORY;
            case HotbarMoveAndReadd:
                return InventoryAction.HOTBAR_MOVE_AND_READD;
            case HotbarSwap:
                return InventoryAction.HOTBAR_SWAP;
            case CloneStack:
                return InventoryAction.CLONE_STACK;
            case CollectToCursor:
                return InventoryAction.COLLECT_TO_CURSOR;
            case Unknown:
                return InventoryAction.UNKNOWN;
            default:
                throw new IllegalArgumentException("Invalid EsInventoryAction");
        }
    }

    public static SoundCategory toBukkitSoundCategory(EsSoundCategory esSoundCategory) {
        switch (esSoundCategory) {
            case Master:
                return SoundCategory.MASTER;
            case Music:
                return SoundCategory.MUSIC;
            case Records:
                return SoundCategory.RECORDS;
            case Weather:
                return SoundCategory.WEATHER;
            case Blocks:
                return SoundCategory.BLOCKS;
            case Hostile:
                return SoundCategory.HOSTILE;
            case Neutral:
                return SoundCategory.NEUTRAL;
            case Players:
                return SoundCategory.PLAYERS;
            case Ambient:
                return SoundCategory.AMBIENT;
            case Voice:
                return SoundCategory.VOICE;
            default:
                throw new IllegalArgumentException("Invalid EsSoundCategory");
        }
    }

    public static EsAction fromBukkitAction(Action action) {
        switch (action) {
            case LEFT_CLICK_BLOCK:
                return EsAction.LeftClickBlock;
            case RIGHT_CLICK_BLOCK:
                return EsAction.RightClickBlock;
            case LEFT_CLICK_AIR:
                return EsAction.LeftClickAir;
            case RIGHT_CLICK_AIR:
                return EsAction.RightClickAir;
            case PHYSICAL:
                return EsAction.Physical;
            default:
                throw new IllegalArgumentException("Invalid Action");
        }
    }

    public static EsInventory fromBukkitInventory(Inventory inv) {
        if (inv == null) {
            return null;
        }

        if (inv instanceof PlayerInventory) {
            return new BukkitPlayerInventory((PlayerInventory) inv);
        }

        return new BukkitInventory(inv);
    }

    public static EsEventResult fromBukkitEventResult(Event.Result result) {
        switch (result) {
            case ALLOW:
                return EsEventResult.ALLOW;
            case DENY:
                return EsEventResult.DENY;
            case DEFAULT:
                return EsEventResult.DEFAULT;
            default:
                throw new IllegalArgumentException("Invalid Event.Result");
        }
    }

    public static Event.Result toBukkitEventResult(EsEventResult result) {
        switch (result) {
            case ALLOW:
                return Event.Result.ALLOW;
            case DENY:
                return Event.Result.DENY;
            case DEFAULT:
                return Event.Result.DEFAULT;
            default:
                throw new IllegalArgumentException("Invalid EsEventResult");
        }
    }
}

package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.*;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

// DO NOT IMPORT THE FOLLOWING BECAUSE THEY BREAK OLDER VERSIONS BECAUSE THEY DON'T EXIST
// org.bukkit.inventory.EquipmentSlot
// org.bukkit.event.player.PlayerTeleportEvent
// org.bukkit.potion.Potion
// org.bukkit.potion.PotionType
// org.bukkit.inventory.InventoryHolder
// org.bukkit.inventory.meta.PotionMeta
// org.bukkit.inventory.meta.ItemMeta
// org.bukkit.Sound
// org.bukkit.event.inventory.ClickType
// org.bukkit.event.inventory.InventoryAction

/**
 * Min version: 1.0
 */
@SuppressWarnings("unused")
public class BukkitHelper {
    public static Location toBukkitLocation(EsLocation loc) {
        Location bLoc = new Location(Bukkit.getWorld(loc.getWorld().getName()), loc.getX(), loc.getY(), loc.getZ());
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
        if (Main.minecraftVersion.isAtLeast(1, 16, 4)) {
            return Registry.SOUNDS.get(NamespacedKey.minecraft(sound.getKey()));
        } else {
            return Sound.valueOf(BukkitSoundEnumConverter.convertKeyToEnum(sound));
        }
    }

    public static EsSound fromBukkitSound(Sound sound) {
        if (Main.minecraftVersion.isAtLeast(1, 16, 4)) {
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
            if (item.getType().name().endsWith("POTION")) {
                return new BukkitPotionVeryOld(item);  // The Potion and PotionMeta classes don't exist
            }

            return new BukkitItemStack(item);
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

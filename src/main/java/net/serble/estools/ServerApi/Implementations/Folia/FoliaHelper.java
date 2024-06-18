package net.serble.estools.ServerApi.Implementations.Folia;

import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsEquipmentSlot;
import net.serble.estools.ServerApi.EsLocation;
import net.serble.estools.ServerApi.Implementations.Bukkit.Helpers.BukkitHelper;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class FoliaHelper extends BukkitHelper {

    public static GlobalRegionScheduler getGlobalScheduler() {
        GlobalRegionScheduler grs;
        try {
            Object serverInstance = EsToolsBukkit.plugin.getServer();

            Method getSchedulerMethod = serverInstance.getClass().getMethod("getGlobalRegionScheduler");
            Object schObject = getSchedulerMethod.invoke(serverInstance);

            if (schObject instanceof GlobalRegionScheduler) {
                grs = (GlobalRegionScheduler) schObject;
            } else {
                Main.logger.severe("schObject is not an instance of GlobalRegionScheduler");
                throw new RuntimeException("Failed to get global scheduler, are you running folia?");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to get global scheduler, are you running folia?");
        }

        return grs;
    }

    private static RegionScheduler getRegionScheduler() {
        RegionScheduler grs;
        try {
            Object serverInstance = EsToolsBukkit.plugin.getServer();

            Method getSchedulerMethod = serverInstance.getClass().getMethod("getRegionScheduler");
            Object schObject = getSchedulerMethod.invoke(serverInstance);

            if (schObject instanceof RegionScheduler) {
                grs = (RegionScheduler) schObject;
            } else {
                Main.logger.severe("schObject is not an instance of RegionScheduler");
                throw new RuntimeException("Failed to get regional scheduler, are you running folia?");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to get regional scheduler, are you running folia?");
        }

        return grs;
    }

    private static EntityScheduler getEntityScheduler(Entity entity) {
        EntityScheduler grs;
        try {
            Method getSchedulerMethod = entity.getClass().getMethod("getScheduler");
            Object schObject = getSchedulerMethod.invoke(entity);

            if (schObject instanceof EntityScheduler) {
                grs = (EntityScheduler) schObject;
            } else {
                Main.logger.severe("schObject is not an instance of EntityScheduler");
                throw new RuntimeException("Failed to get entity scheduler, are you running folia?");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to get entity scheduler, are you running folia?");
        }

        return grs;
    }

    public static ScheduledTask runTaskLater(Runnable task, long ticks) {
        return getGlobalScheduler().runDelayed(EsToolsBukkit.plugin, (t) -> task.run(), ticks);
    }

    public static void runTaskOnNextTick(Runnable task) {
        getGlobalScheduler().run(EsToolsBukkit.plugin, (t) -> task.run());
    }

    public static void runFromLocation(EsLocation loc, Runnable task) {
        runFromLocation(toBukkitLocation(loc), task);
    }

    public static void runFromLocation(Location loc, Runnable task) {
        getRegionScheduler().run(EsToolsBukkit.plugin, loc, (t) -> task.run());
    }

    public static void runFromEntity(EsEntity entity, Runnable task) {
        runFromEntity(((FoliaEntity)entity).getBukkitEntity(), task);
    }

    public static void runFromEntity(Entity entity, Runnable task) {
        getEntityScheduler(entity).run(EsToolsBukkit.plugin, (t) -> task.run(), null);
    }

    public static void runSync(Runnable task) {
        FoliaServer.newChain().sync(task::run).sync(() -> Bukkit.getLogger().info("Main thread: " + Thread.currentThread().getName())).execute();
    }

    public static EsCommandSender fromBukkitCommandSender(org.bukkit.command.CommandSender sender) {
        if (sender == null) {
            return null;
        }

        if (sender instanceof Player) {
            return new net.serble.estools.ServerApi.Implementations.Folia.FoliaPlayer((Player) sender);
        }

        if (sender instanceof LivingEntity) {
            return new net.serble.estools.ServerApi.Implementations.Folia.FoliaLivingEntity((org.bukkit.entity.LivingEntity) sender);
        }

        if (sender instanceof Entity) {
            return new net.serble.estools.ServerApi.Implementations.Folia.FoliaEntity((org.bukkit.entity.Entity) sender);
        }

        if (sender instanceof ConsoleCommandSender) {
            return new net.serble.estools.ServerApi.Implementations.Folia.FoliaConsoleSender((ConsoleCommandSender) sender);
        }

        if (sender instanceof BlockCommandSender) {
            return new net.serble.estools.ServerApi.Implementations.Folia.FoliaCommandBlockSender((BlockCommandSender) sender);
        }

        throw new RuntimeException("Unrecognised command sender");
    }

    public static CommandSender toBukkitCommandSender(EsCommandSender sender) {
        if (sender instanceof EsPlayer) {
            return ((net.serble.estools.ServerApi.Implementations.Folia.FoliaPlayer) sender).getBukkitPlayer();
        }

        if (sender instanceof EsLivingEntity) {
            return ((net.serble.estools.ServerApi.Implementations.Folia.FoliaLivingEntity) sender).getBukkitEntity();
        }

        if (sender instanceof EsEntity) {
            return ((net.serble.estools.ServerApi.Implementations.Folia.FoliaEntity) sender).getBukkitEntity();
        }

        if (sender instanceof EsConsoleSender) {
            return Bukkit.getConsoleSender();
        }

        if (sender instanceof EsCommandBlockSender) {
            return ((net.serble.estools.ServerApi.Implementations.Folia.FoliaCommandBlockSender) sender).getBukkitSender();
        }

        throw new RuntimeException("Unrecognised command sender");
    }

    public static EsEntity fromBukkitEntity(org.bukkit.entity.Entity entity) {
        if (entity instanceof org.bukkit.entity.LivingEntity) {
            return new net.serble.estools.ServerApi.Implementations.Folia.FoliaLivingEntity((LivingEntity) entity);
        }

        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaEntity(entity);
    }

    public static EsBlock fromBukkitBlock(Block block) {
        BlockState state = block.getState();

        if (state instanceof Sign) {
            return new net.serble.estools.ServerApi.Implementations.Folia.FoliaSign((Sign) state);
        }

        return new net.serble.estools.ServerApi.Implementations.Folia.FoliaBlock(state);
    }

    public static EsItemStack fromBukkitItem(ItemStack item) {
        if (item == null) {
            return null;
        }

        if (Main.minecraftVersion.getMinor() >= 9) {
            if (item.getItemMeta() instanceof PotionMeta) {
                return new FoliaPotion(item);
            }

            return new FoliaItemStack(item);
        } else if (Main.minecraftVersion.getMinor() >= 4) {
            if (item.getType().name().endsWith("POTION")) {
                return new FoliaPotion(item);
            }

            return new FoliaItemStack(item);
        } else {
            throw new RuntimeException("Potions aren't support in this Minecraft version");
        }
    }

    public static EsBlock fromBukkitBlock(BlockState block) {
        if (block == null) {
            return null;
        }

        if (block instanceof Sign) {
            return new FoliaSign((Sign) block);
        }

        return new FoliaBlock(block);
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static EsInventory fromBukkitInventory(Inventory inv) {
        if (inv == null) {
            return null;
        }

        return new FoliaInventory(inv);
    }

    public static EquipmentSlot toBukkitEquipmentSlot(EsEquipmentSlot slot) {
        switch (slot) {
            case Feet:
                return EquipmentSlot.FEET;
            case Hand:
                return EquipmentSlot.HAND;
            case Head:
                return EquipmentSlot.HEAD;
            case Legs:
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
                return EsEquipmentSlot.Legs;
            case CHEST:
                return EsEquipmentSlot.Chest;
            case OFF_HAND:
                return EsEquipmentSlot.OffHand;
            default:
                throw new IllegalArgumentException("Invalid EquipmentSlot");
        }
    }
}

package net.serble.estools.ServerApi.Implementations.Folia;

import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.serble.estools.Entrypoints.EsToolsBukkit;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsClickType;
import net.serble.estools.ServerApi.EsEquipmentSlot;
import net.serble.estools.ServerApi.EsInventoryAction;
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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
            return new FoliaPlayer((Player) sender);
        }

        if (sender instanceof LivingEntity) {
            return new FoliaLivingEntity((org.bukkit.entity.LivingEntity) sender);
        }

        if (sender instanceof Entity) {
            return new FoliaEntity((org.bukkit.entity.Entity) sender);
        }

        if (sender instanceof ConsoleCommandSender) {
            return new FoliaConsoleSender((ConsoleCommandSender) sender);
        }

        if (sender instanceof BlockCommandSender) {
            return new FoliaCommandBlockSender((BlockCommandSender) sender);
        }

        throw new RuntimeException("Unrecognised command sender");
    }

    public static CommandSender toBukkitCommandSender(EsCommandSender sender) {
        if (sender instanceof EsPlayer) {
            return ((FoliaPlayer) sender).getBukkitPlayer();
        }

        if (sender instanceof EsLivingEntity) {
            return ((FoliaLivingEntity) sender).getBukkitEntity();
        }

        if (sender instanceof EsEntity) {
            return ((FoliaEntity) sender).getBukkitEntity();
        }

        if (sender instanceof EsConsoleSender) {
            return Bukkit.getConsoleSender();
        }

        if (sender instanceof EsCommandBlockSender) {
            return ((FoliaCommandBlockSender) sender).getBukkitSender();
        }

        throw new RuntimeException("Unrecognised command sender");
    }

    public static EsEntity fromBukkitEntity(Entity entity) {
        if (entity instanceof Player) {
            return new FoliaPlayer((Player) entity);
        }

        if (entity instanceof LivingEntity) {
            return new FoliaLivingEntity((LivingEntity) entity);
        }

        return new FoliaEntity(entity);
    }

    public static EsBlock fromBukkitBlock(Block block) {
        if (block == null) {
            return null;
        }

        BlockState state = block.getState();

        if (state instanceof Sign) {
            return new FoliaSign((Sign) state);
        }

        return new FoliaBlock(state);
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

    public static EsInventory fromBukkitInventory(Inventory inv) {
        if (inv == null) {
            return null;
        }

        if (inv instanceof PlayerInventory) {
            return new FoliaPlayerInventory((PlayerInventory) inv);
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

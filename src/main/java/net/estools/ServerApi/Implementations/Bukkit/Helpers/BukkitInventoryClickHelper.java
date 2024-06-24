package net.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.estools.ServerApi.EsClickType;
import net.estools.ServerApi.EsInventoryAction;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

/**
 * Min version: 1.5
 */
@SuppressWarnings("unused")
public class BukkitInventoryClickHelper {
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
}

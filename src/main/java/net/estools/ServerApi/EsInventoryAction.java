package net.estools.ServerApi;

/**
 * The action that was triggered by the player's input
 */
public enum EsInventoryAction {
    /**
     * Nothing happened, I'm not sure why you would ever call the event for this, but it's here.
     */
    Nothing,
    /**
     * All the items on the clicked slot are moved to the cursor.
     */
    PickupAll,
    /**
     * Some of the items on the clicked slot are moved to the cursor.
     */
    PickupSome,
    /**
     * Half of the items on the clicked slot are moved to the cursor.
     */
    PickupHalf,
    /**
     * One of the items on the clicked slot are moved to the cursor.
     */
    PickupOne,
    /**
     * All the items on the cursor are moved to the clicked slot.
     */
    PlaceAll,
    /**
     * Some of the items from the cursor are moved to the clicked slot
     * (usually up to the max stack size).
     */
    PlaceSome,
    /**
     * A single item from the cursor is moved to the clicked slot.
     */
    PlaceOne,
    /**
     * The clicked item and the cursor are exchanged.
     */
    SwapWithCursor,
    /**
     * The entire cursor item is dropped.
     */
    DropAllCursor,
    /**
     * One item is dropped from the cursor.
     */
    DropOneCursor,
    /**
     * The entire clicked slot is dropped.
     */
    DropAllSlot,
    /**
     * One item is dropped from the clicked slot.
     */
    DropOneSlot,
    /**
     * The item is moved to the opposite inventory if a space is found.
     */
    MoveToOtherInventory,
    /**
     * The clicked item is moved to the hotbar, and the item currently there
     * is re-added to the player's inventory.
     * The hotbar includes the player's offhand.
     */
    HotbarMoveAndReadd,
    /**
     * The clicked slot and the picked hotbar slot are swapped.
     * The hotbar includes the player's offhand.
     */
    HotbarSwap,
    /**
     * A max-size stack of the clicked item is put on the cursor.
     */
    CloneStack,
    /**
     * The inventory is searched for the same material, and they are put on
     * the cursor up to the max stack size.
     */
    CollectToCursor,
    /**
     * An unrecognized ClickType.
     */
    Unknown
}

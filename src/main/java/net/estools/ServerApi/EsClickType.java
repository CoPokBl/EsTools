package net.estools.ServerApi;

import org.jetbrains.annotations.ApiStatus;

/**
 * How the action was triggered, not what it did, see {@link net.estools.ServerApi.EsInventoryAction}
 * for the action.
 */
@SuppressWarnings("unused")  // Important methods that might be helpful in the future
public enum EsClickType {

    /**
     * The left (or primary) mouse button.
     */
    Left,
    /**
     * Holding shift while pressing the left mouse button.
     */
    ShiftLeft,
    /**
     * The right mouse button.
     */
    Right,
    /**
     * Holding shift while pressing the right mouse button.
     */
    ShiftRight,
    /**
     * Clicking the left mouse button on the grey area around the inventory.
     */
    WindowBorderLeft,
    /**
     * Clicking the right mouse button on the grey area around the inventory.
     */
    WindowBorderRight,
    /**
     * The middle mouse button, or a "scrollwheel click".
     */
    Middle,
    /**
     * One of the number keys 1-9, correspond to slots on the hotbar.
     */
    NumberKey,
    /**
     * Pressing the left mouse button twice in quick succession.
     */
    DoubleClick,
    /**
     * The "Drop" key (defaults to Q).
     */
    Drop,
    /**
     * Holding Ctrl while pressing the "Drop" key (defaults to Q).
     */
    ControlDrop,
    /**
     * Any action done with the Creative inventory open.
     */
    Creative,
    /**
     * The "swap item with offhand" key (defaults to F).
     */
    SwapOffhand,
    /**
     * Should only be called if the implementation is not updated for a Minecraft version.
     * Getting this value is a sign of a bug.
     */
    @ApiStatus.Experimental
    Unknown,
    ;

    /**
     * Gets whether this ClickType represents the pressing of a key on a
     * keyboard.
     *
     * @return true if this ClickType represents the pressing of a key
     */
    public boolean isKeyboardClick() {
        return (this == EsClickType.NumberKey) || (this == EsClickType.Drop) || (this == EsClickType.ControlDrop) || (this == EsClickType.SwapOffhand);
    }

    /**
     * Gets whether this ClickType represents the pressing of a mouse button
     *
     * @return true if this ClickType represents the pressing of a mouse button
     */
    public boolean isMouseClick() {
        return (this == EsClickType.DoubleClick) || (this == EsClickType.Left) || (this == EsClickType.Right) || (this == EsClickType.Middle)
                || (this == EsClickType.WindowBorderLeft) || (this == EsClickType.ShiftLeft) || (this == EsClickType.ShiftRight) || (this == EsClickType.WindowBorderRight);
    }

    /**
     * Gets whether this ClickType represents an action that can only be
     * performed by a Player in creative mode.
     *
     * @return true if this action requires Creative mode
     */
    public boolean isCreativeAction() {
        // Why use middle click?
        return (this == EsClickType.Middle) || (this == EsClickType.Creative);
    }

    /**
     * Gets whether this ClickType represents a right click.
     *
     * @return true if this ClickType represents a right click
     */
    public boolean isRightClick() {
        return (this == EsClickType.Right) || (this == EsClickType.ShiftRight);
    }

    /**
     * Gets whether this ClickType represents a left click.
     *
     * @return true if this ClickType represents a left click
     */
    public boolean isLeftClick() {
        return (this == EsClickType.Left) || (this == EsClickType.ShiftLeft) || (this == EsClickType.DoubleClick) || (this == EsClickType.Creative);
    }

    /**
     * Gets whether this ClickType indicates that the shift key was pressed
     * down when the click was made.
     *
     * @return true if the action uses Shift.
     */
    public boolean isShiftClick() {
        return (this == EsClickType.ShiftLeft) || (this == EsClickType.ShiftRight);
    }
}

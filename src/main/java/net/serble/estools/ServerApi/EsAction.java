package net.serble.estools.ServerApi;

public enum EsAction {
    /**
     * Left-clicking a block
     */
    LeftClickBlock,
    /**
     * Right-clicking a block
     */
    RightClickBlock,
    /**
     * Left-clicking the air
     */
    LeftClickAir,
    /**
     * Right-clicking the air
     */
    RightClickAir,
    /**
     * Stepping onto or into a block (Ass-pressure)
     * <p>
     * Examples:
     * <ul>
     * <li>Jumping on soil
     * <li>Standing on pressure plate
     * <li>Triggering redstone ore
     * <li>Triggering tripwire
     * </ul>
     */
    Physical
}

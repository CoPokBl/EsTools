package net.serble.estools.ServerApi.Interfaces;

public interface EsSign extends EsBlock {
    void update();

    /** Only for 1.17-1.19 where signs are single sided but support glow */
    void setGlowingText(boolean glowing);
    boolean isGlowingText();

    /** Only for pre 1.20 where signs are singlesided */
    void setLine(int line, String text);

    /** Gets the side targeted by the player, only for 1.20+ */
    EsSignSide getTargetSide(EsPlayer player);
}

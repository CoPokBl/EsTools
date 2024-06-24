package net.estools.ServerApi.Interfaces;

/** This should only be used if the version is 1.20+ where double-sided signs exist */
public interface EsSignSide {
    void setGlowingText(boolean glowing);
    boolean isGlowingText();
    void setLine(int line, String text);
}

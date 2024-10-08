package net.estools.ServerApi;

import net.estools.Main;
import net.estools.Utils;
import org.jetbrains.annotations.Nullable;

public class EsPotionEffectType {
    private String key;

    public static EsPotionEffectType createUnchecked(String key) {
        EsPotionEffectType type = new EsPotionEffectType();
        type.key = key.toLowerCase();

        return type;
    }

    public static @Nullable EsPotionEffectType fromKey(String key) {
        EsPotionEffectType type = EsPotionEffectType.createUnchecked(key);

        if (Main.server.getPotionEffectTypes().contains(type)) {
            return type;
        }

        return null;
    }

    public String getKey() {
        return key;
    }

    /** This exists for YAML serialisation only, DO NOT USE, use EsPotionEffectType.fromKey(String) instead. */
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EsPotionEffectType) {
            return ((EsPotionEffectType) obj).getKey().equals(key);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return Utils.keyToDisplayName(key);
    }
}

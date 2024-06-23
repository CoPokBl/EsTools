package net.serble.estools.ServerApi;

import net.serble.estools.Main;
import net.serble.estools.Utils;
import org.jetbrains.annotations.Nullable;

public class EsSound {
    private String key;

    public static EsSound createUnchecked(String key) {
        EsSound type = new EsSound();
        type.key = key.toLowerCase();

        return type;
    }

    public static @Nullable EsSound fromKey(String key) {
        EsSound type = EsSound.createUnchecked(key);

        if (Main.server.getSounds().contains(type)) {
            return type;
        }

        return null;
    }

    public String getKey() {
        return key;
    }

    /** This exists for YAML serialisation only, DO NOT USE, use EsSound.fromKey(String) instead. */
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EsSound) {
            return ((EsSound) obj).getKey().equals(key);
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

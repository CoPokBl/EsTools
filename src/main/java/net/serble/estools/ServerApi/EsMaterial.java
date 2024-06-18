package net.serble.estools.ServerApi;

import net.serble.estools.Main;
import net.serble.estools.Utils;
import org.jetbrains.annotations.Nullable;

public class EsMaterial {
    private String key;

    public static EsMaterial createUnchecked(String key) {
        EsMaterial type = new EsMaterial();
        type.key = key;

        return type;
    }

    public static @Nullable EsMaterial fromKey(String key) {
        EsMaterial type = EsMaterial.createUnchecked(key.toLowerCase());

        if (Main.server.getMaterials(false).contains(type)) {
            return type;
        }

        return null;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EsMaterial) {
            return ((EsMaterial) obj).getKey().equals(key);
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

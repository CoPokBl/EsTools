package net.serble.estools.ServerApi;

import net.serble.estools.Main;
import net.serble.estools.Utils;
import org.jetbrains.annotations.Nullable;

public class EsEnchantment {
    private String key;

    public static EsEnchantment createUnchecked(String key) {
        EsEnchantment type = new EsEnchantment();
        type.key = key.toLowerCase();

        return type;
    }

    public static @Nullable EsEnchantment fromKey(String key) {
        EsEnchantment type = EsEnchantment.createUnchecked(key);

        if (Main.server.getEnchantments().contains(type)) {
            return type;
        }

        return null;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EsEnchantment) {
            return ((EsEnchantment) obj).getKey().equals(key);
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

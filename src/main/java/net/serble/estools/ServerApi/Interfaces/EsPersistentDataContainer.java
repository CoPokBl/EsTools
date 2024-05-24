package net.serble.estools.ServerApi.Interfaces;

import net.serble.estools.ServerApi.EsPersistentDataType;

public interface EsPersistentDataContainer {
    void set(String key, EsPersistentDataType type, Object val);
    Object get(String key, EsPersistentDataType type);
    void remove(String key);
}

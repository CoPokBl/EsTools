package net.estools.ServerApi.Interfaces;

import net.estools.ServerApi.EsPersistentDataType;

import java.util.Set;

public interface EsPersistentDataContainer {
    void set(String key, EsPersistentDataType type, Object val);
    Object get(String key, EsPersistentDataType type);
    void remove(String key);
    boolean has(String key);
    Set<String> getKeys();
    EsPersistentDataType getType(String key);
}

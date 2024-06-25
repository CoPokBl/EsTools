package net.estools.Implementation;

import net.estools.ServerApi.EsPersistentDataType;
import net.estools.ServerApi.Interfaces.EsPersistentDataContainer;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class TestPersistentDataContainer implements EsPersistentDataContainer {
    private final Map<String, Object> values = new HashMap<>();

    // TEST METHODS

    public Map<String, Object> getValues() {
        return values;
    }

    // IMPLEMENTATION METHODS

    @Override
    public void set(String key, EsPersistentDataType type, Object val) {
        values.put(key, val);
    }

    @Override
    public Object get(String key, EsPersistentDataType type) {
        return values.get(key);
    }

    @Override
    public void remove(String key) {
        values.remove(key);
    }
}

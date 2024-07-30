package net.estools.Implementation;

import net.estools.ServerApi.EsPersistentDataType;
import net.estools.ServerApi.Interfaces.EsPersistentDataContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class TestPersistentDataContainer implements EsPersistentDataContainer {
    private final Map<String, Object> values = new HashMap<>();
    private final Map<String, EsPersistentDataType> types = new HashMap<>();

    // TEST METHODS

    public Map<String, Object> getValues() {
        return values;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public TestPersistentDataContainer clone() {
        TestPersistentDataContainer clone = new TestPersistentDataContainer();
        clone.values.putAll(values);
        clone.types.putAll(types);
        return clone;
    }

    // IMPLEMENTATION METHODS

    @Override
    public void set(String key, EsPersistentDataType type, Object val) {
        values.put(key, val);
        types.put(key, type);
    }

    @Override
    public Object get(String key, EsPersistentDataType type) {
        return values.get(key);
    }

    @Override
    public void remove(String key) {
        values.remove(key);
    }

    @Override
    public boolean has(String key) {
        return values.containsKey(key);
    }

    @Override
    public Set<String> getKeys() {
        return values.keySet();
    }

    @Override
    public EsPersistentDataType getType(String key) {
        return types.get(key);
    }
}

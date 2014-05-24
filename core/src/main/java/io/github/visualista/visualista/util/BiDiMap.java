package io.github.visualista.visualista.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BiDiMap<K, V> {

    private final Map<K, V> keyToValue;

    private final Map<V, K> valueToKey;

    public BiDiMap() {

        keyToValue = new HashMap<K, V>();

        valueToKey = new HashMap<V, K>();

    }

    public void clear() {

        keyToValue.clear();

        valueToKey.clear();

    }

    public int size() {

        return keyToValue.size();

    }

    public V put(final K key, final V val) {
        valueToKey.put(val, key);
        return keyToValue.put(key, val);

    }

    public V getValue(final K key) {
        return keyToValue.get(key);

    }

    public K getKey(final V value) {
        return valueToKey.get(value);
    }

    public V removeByKey(final K key) {
        V value = keyToValue.get(key);
        if (value != null) {
            valueToKey.remove(value);
            keyToValue.remove(key);
        }
        return value;
    }

    public K removeByValue(final V value) {
        K key = valueToKey.get(value);
        if (key != null) {
            keyToValue.remove(key);
            valueToKey.remove(value);
        }
        return key;
    }

    public Set<K> getAllKeys() {
        return keyToValue.keySet();
    }

    public Set<V> getAllValues() {
        return valueToKey.keySet();
    }

}

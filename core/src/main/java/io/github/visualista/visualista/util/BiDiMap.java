package io.github.visualista.visualista.util;

import java.util.HashMap;
import java.util.Set;

public class BiDiMap<K, V>{

    private HashMap<K, V> keyToValue;

    private HashMap<V, K> valueToKey;

    public BiDiMap()

    {

        keyToValue = new HashMap<K, V>();

        valueToKey = new HashMap<V, K>();

    }

    public void clear()

    {

        keyToValue.clear();

        valueToKey.clear();

    }

    public int size()

    {

        return keyToValue.size();

    }

    public V put(K key, V val)

    {
        valueToKey.put(val, key);
        return keyToValue.put(key, val);

    }

    public V getValue(K key)

    {
        return keyToValue.get(key);

    }

    public K getKey(V value) {
        return valueToKey.get(value);
    }

    public V removeByKey(K key) {
        V value = keyToValue.get(key);
        if (value != null) {
            valueToKey.remove(value);
            keyToValue.remove(key);
        }
        return value;
    }

    public K removeByValue(V value) {
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
    
    public Set<V> getAllValues(){
        return valueToKey.keySet();
    }

}

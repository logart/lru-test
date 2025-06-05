import java.util.HashMap;
import java.util.Map;

public class DefaultLRUCache<T> implements LRUCache<T> {

    private int capacity = 0;
    private final Map<String, T> values;
    private final Map<String, Integer> keyPosition;
    private final String[] usageOrder;
    private final int size;
    private int usageTimeSize = 0;
    private int leastRecentlyUsedIndex = 0;

    public DefaultLRUCache(int size) {
        this.size = size;
        this.values = new HashMap<>(size);
        this.keyPosition = new HashMap<>(size);
        this.usageOrder = new String[size];
    }

    @Override
    public T get(String key) {
        updateUsageTime(key);
        return values.get(key);
    }

    @Override
    public void set(String key, T value) {
        if (capacity > 0 && capacity >= size) {
            String lruKey = removeRecentlyUsedKey();
            values.remove(lruKey);
            keyPosition.remove(lruKey);
            capacity--;
        }
        capacity++;
        assert capacity <= size;
        values.put(key, value);
        updateUsageTime(key);
    }

    private String removeRecentlyUsedKey() {
        String lruKey = usageOrder[leastRecentlyUsedIndex];
        usageOrder[leastRecentlyUsedIndex] = null;
        updateLeastRecentlyUsedIndex();
        return lruKey;
    }

    public long getUsageTime(String key) {
        return keyPosition.getOrDefault(key, -1);
    }

    private void updateUsageTime(String key) {
        Integer position = keyPosition.get(key);
        if (position != null) {
            usageOrder[position] = null;
            if (leastRecentlyUsedIndex == position) {
                updateLeastRecentlyUsedIndex();
            }
        }

        usageOrder[usageTimeSize] = key;
        keyPosition.put(key, usageTimeSize);
        updateUsageTimeSize();
    }

    private void updateLeastRecentlyUsedIndex() {
        if (leastRecentlyUsedIndex == size) {
            leastRecentlyUsedIndex = -1;
        }
        leastRecentlyUsedIndex++;
    }

    private int updateUsageTimeSize() {
        if (usageTimeSize == size - 1) {
            usageTimeSize = -1;
        }
        return usageTimeSize++;
    }
}

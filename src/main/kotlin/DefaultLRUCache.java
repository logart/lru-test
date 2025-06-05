import java.util.HashMap;
import java.util.Map;

public class DefaultLRUCache<T> implements LRUCache<T> {

    private final Map<String, LRURecord<T>> values;
    private final int size;
    private int capacity = 0;
    private LRURecord<T> leastRecentlyUsedRecord = null;
    private LRURecord<T> mostRecentlyUsedRecord = null;

    public DefaultLRUCache(int size) {
        this.size = size;
        this.values = new HashMap<>(size);
    }

    @Override
    public T get(String key) {
        LRURecord<T> record = values.get(key);
        if (record == null) {
            return null;
        }
        setMostRecentlyUsed(record);
        return record.getValue();
    }

    @Override
    public void set(String key, T value) {
        LRURecord<T> newRecord = new LRURecord<>(key, value);
        if (capacity == 0) {
            leastRecentlyUsedRecord = newRecord;
        }
        if (capacity >= size) {
            LRURecord<T> lruRecord = removeRecentlyUsedKey();
            values.remove(lruRecord.getKey());
            capacity--;
        }
        capacity++;
        assert capacity <= size;
        setMostRecentlyUsed(newRecord);
        values.put(key, newRecord);
    }

    private LRURecord<T> removeRecentlyUsedKey() {
        LRURecord<T> currentLeastRecentlyUsedRecord = leastRecentlyUsedRecord;
        if (leastRecentlyUsedRecord != null) {
            leastRecentlyUsedRecord = leastRecentlyUsedRecord.getNext();
            if (leastRecentlyUsedRecord != null) {
                leastRecentlyUsedRecord.setPrev(null);
            }
        }
        return currentLeastRecentlyUsedRecord;
    }

    private void setMostRecentlyUsed(LRURecord<T> newMostRecentlyUsedRecord) {
        LRURecord<T> currentMostRecentlyUsedRecord = mostRecentlyUsedRecord;
        LRURecord<T> currentLeastRecentlyUsedRecord = leastRecentlyUsedRecord;
        if (currentLeastRecentlyUsedRecord == newMostRecentlyUsedRecord) {
            leastRecentlyUsedRecord = currentLeastRecentlyUsedRecord.getNext();
        }
        if (currentMostRecentlyUsedRecord != null) {
            // tie prev and next together
            LRURecord<T> prev = newMostRecentlyUsedRecord.getPrev();
            LRURecord<T> next = newMostRecentlyUsedRecord.getNext();
            if (prev != null) {
                prev.setNext(next);
            }
            if (next != null) {
                next.setPrev(prev);
            }

            newMostRecentlyUsedRecord.setPrev(currentMostRecentlyUsedRecord);
            newMostRecentlyUsedRecord.setNext(null);
            currentMostRecentlyUsedRecord.setNext(newMostRecentlyUsedRecord);
        }
        mostRecentlyUsedRecord = newMostRecentlyUsedRecord;
        if (leastRecentlyUsedRecord == null) {
            leastRecentlyUsedRecord = mostRecentlyUsedRecord;
        }
    }
}

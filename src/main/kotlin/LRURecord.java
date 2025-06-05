public class LRURecord<T> {
    private final String key;
    private final T value;
    private LRURecord<T> prev;
    private LRURecord<T> next;

    public LRURecord(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public LRURecord<T> getNext() {
        return next;
    }

    public void setNext(LRURecord<T> next) {
        this.next = next;
    }

    public LRURecord<T> getPrev() {
        return prev;
    }

    public void setPrev(LRURecord<T> prev) {
        this.prev = prev;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "LRURecord{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", prev=" + prev +
                ", next=" + next +
                '}';
    }
}

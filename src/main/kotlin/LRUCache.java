public interface LRUCache<T> {
    T get(String key);

    void set(String key, T value);
}

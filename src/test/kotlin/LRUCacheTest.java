import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LRUCacheTest {
    @Test
    public void should_set_and_get_value() {
        LRUCache cache = new DefaultLRUCache<String>(1);
        cache.set("key", "value");
        assertThat(cache.get("key")).isEqualTo("value");
    }

    @Test
    public void should_remove_previous_element_when_the_new_element_is_added_and_capacity_reached() {
        LRUCache cache = new DefaultLRUCache<String>(1);
        cache.set("key1", "value1");
        cache.set("key2", "value2");
        assertThat(cache.get("key2")).isEqualTo("value2");
        assertThat(cache.get("key1")).isNull();
    }

    @Test
    public void should_remove_least_recently_used_element_when_capacity_is_reached() {
        LRUCache cache = new DefaultLRUCache<String>(1);
        cache.set("key1", "value1");
        cache.set("key2", "value2");
        assertThat(cache.get("key2")).isEqualTo("value2");
        assertThat(cache.get("key1")).isNull();
    }

    @Test
    public void should_remove_least_recently_used_element_when_capacity_is_reached_size_2() {
        LRUCache cache = new DefaultLRUCache<String>(2);
        cache.set("key1", "value1");
        cache.set("key2", "value2");
        cache.set("key3", "value3");
        assertThat(cache.get("key3")).isEqualTo("value3");
        assertThat(cache.get("key2")).isEqualTo("value2");
        assertThat(cache.get("key1")).isNull();
    }

    @Test
    public void should_remove_least_recently_used_element_when_capacity_is_reached_size_2_reordering() {
        LRUCache cache = new DefaultLRUCache<String>(2);
        cache.set("key1", "value1");
        cache.set("key2", "value2");
        cache.get("key1"); // Access key1 to update its usage time
        cache.set("key3", "value3");
        assertThat(cache.get("key3")).isEqualTo("value3");
        assertThat(cache.get("key1")).isEqualTo("value1");
        assertThat(cache.get("key2")).isNull();
    }

    @Test
    public void should_remove_least_recently_used_element_when_capacity_is_reached_size_3_reordering() {
        LRUCache cache = new DefaultLRUCache<String>(3);
        cache.set("key1", "value1");
        cache.set("key2", "value2");
        cache.set("key3", "value3");
        cache.get("key1"); // Access key1 to update its usage time
        cache.set("key4", "value4");
        assertThat(cache.get("key4")).isEqualTo("value4");
        assertThat(cache.get("key3")).isEqualTo("value3");
        assertThat(cache.get("key1")).isEqualTo("value1");
        assertThat(cache.get("key2")).isNull();
    }
}
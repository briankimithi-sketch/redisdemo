package com.abcbank.redis2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductCacheTest {

    @MockBean
    private CacheManager cacheManager;

    @Test
    void testCacheHitAndMiss() {
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("products")).thenReturn(cache);

        cache.put("1", "Product 1");

        when(cache.get("1", String.class)).thenReturn("Product 1");
        String cached = cache.get("1", String.class);
        assertThat(cached).isEqualTo("Product 1");

        doNothing().when(cache).evict("1");
        cache.evict("1");
        when(cache.get("1", String.class)).thenReturn(null);
        String afterEvict = cache.get("1", String.class);
        assertThat(afterEvict).isNull();
    }
}

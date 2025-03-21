package caffeineT.inSpring;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-20
 * @Description: Cache管理，添加caffeine实现
 * @Version: 1.0
 */
@Configuration
public class MyCacheManager {
    // 每个cache存一类缓存，manager管理这些cache
    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        // 集成spring使用CaffeineCache
        List<CaffeineCache> caches = new ArrayList<>();
        for (CacheConst.CacheInfo cacheInfo : CacheConst.CacheInfo.values()) {
            // 每个CaffeineCache使用一个名字和对应的cache来初始化
            // 在使用spring的@Cacheable等注解的时候通过指定 cacheManager='caffeineCacheManager', value='对应的名字' 来缓存到指定的cache
            caches.add(new CaffeineCache(
                    cacheInfo.cacheName,
                    Caffeine.newBuilder()
                            .maximumSize(cacheInfo.maxSize)
                            .expireAfterWrite(Duration.ofSeconds(cacheInfo.expireSeconds))
                            .recordStats()
                            .build()
            ));
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}


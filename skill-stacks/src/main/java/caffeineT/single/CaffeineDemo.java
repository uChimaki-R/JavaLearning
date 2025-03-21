package caffeineT.single;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-20
 * @Description: 简单使用caffeine本地缓存
 * @Version: 1.0
 */
public class CaffeineDemo {
    public static void main(String[] args) {
        // 不带loader的cache，在get里手写未命中时的返回值(同步函数)
        syncCacheWithoutLoader();
        // build时指定了loader的操作，所有get直接调用即可
        syncCacheWithLoader();
        // 指定异步回调
        asyncCacheWithLoader();
    }

    private static void asyncCacheWithLoader() {
        AsyncLoadingCache<Object, String> asyncCacheWithLoader = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .recordStats()
                .buildAsync(k -> k + "'s value");
        // 调用返回CompletableFuture
        CompletableFuture<String> v = asyncCacheWithLoader.get("key1");
        String s = null;
        try {
            // get阻塞获取
            s = v.get();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        System.out.println(s);
    }

    private static void syncCacheWithLoader() {
        LoadingCache<Object, String> cacheWithLoader = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .recordStats()
                .build(k -> k + "'s value");
        cacheWithLoader.put("key1", "value1");
        String v2 = cacheWithLoader.get("key2");
        // 因为设置了loader，所以无需指明未命中时的回调函数
        System.out.println(v2);
    }

    private static void syncCacheWithoutLoader() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .recordStats() // 统计命中信息等
                .build();
        cache.put("key1", "value1");
        Object v2 = cache.getIfPresent("key2");
        // 没有v2，所以是null
        System.out.println(v2);
        v2 = cache.get("key2", k -> "value2");
        // 设置了不存在时的构造方式，此时可以获得v2
        System.out.println(v2);
    }
}

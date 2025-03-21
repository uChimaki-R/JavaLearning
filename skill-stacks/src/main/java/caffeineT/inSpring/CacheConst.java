package caffeineT.inSpring;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-20
 * @Description: 缓存相关常量
 * @Version: 1.0
 */
public class CacheConst {
    public enum CacheInfo {
        Po1("cache-po1", 3600, 10),
        Po2("cache-po2", 3600, 10);
        final String cacheName;
        final long expireSeconds;
        final long maxSize;

        CacheInfo(String cacheName, long expireSeconds, long maxSize) {
            this.cacheName = cacheName;
            this.expireSeconds = expireSeconds;
            this.maxSize = maxSize;
        }
    }
}

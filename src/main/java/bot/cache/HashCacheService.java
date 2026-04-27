package bot.cache;

import java.util.concurrent.ConcurrentHashMap;

public class HashCacheService {
    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    public String getCash(String fileUniqueId) {
        return cache.get(fileUniqueId);
    }

    public void saveCache(String fileUniqueId, String hash) {
        cache.put(fileUniqueId, hash);
        System.out.println("💾 Сохраняю в кэш: " + fileUniqueId + " -> " + hash);
    }

    public int getCacheSize() {
        return cache.size();
    }
}

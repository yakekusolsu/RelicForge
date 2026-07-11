package jp.yourname.charmorb.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CooldownManager {
    private final Map<String, Long> cooldowns = new HashMap<>();

    public boolean ready(UUID uuid, String key) {
        return remainingMillis(uuid, key) <= 0L;
    }

    public long remainingMillis(UUID uuid, String key) {
        return cooldowns.getOrDefault(uuid + ":" + key, 0L) - System.currentTimeMillis();
    }

    public void set(UUID uuid, String key, long millis) {
        cooldowns.put(uuid + ":" + key, System.currentTimeMillis() + millis);
    }
}

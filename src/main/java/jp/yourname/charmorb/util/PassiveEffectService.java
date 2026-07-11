package jp.yourname.charmorb.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class PassiveEffectService {
    private final Map<UUID, Map<PotionEffectType, Set<String>>> activeSources = new HashMap<>();
    private final Map<UUID, Map<String, Set<PotionEffectType>>> sourceEffects = new HashMap<>();
    private final Map<UUID, Set<PotionEffectType>> managedTypes = new HashMap<>();

    public void begin(Player player, String source) {
        UUID id = player.getUniqueId();
        Set<PotionEffectType> previous = sourceEffects
            .computeIfAbsent(id, ignored -> new HashMap<>())
            .remove(source);
        if (previous == null) {
            return;
        }
        Map<PotionEffectType, Set<String>> byType = activeSources.get(id);
        if (byType == null) {
            return;
        }
        for (PotionEffectType type : previous) {
            Set<String> sources = byType.get(type);
            if (sources == null) {
                continue;
            }
            sources.remove(source);
            if (sources.isEmpty()) {
                byType.remove(type);
            }
        }
    }

    public void apply(Player player, String source, PotionEffectType type, int duration, int amplifier) {
        UUID id = player.getUniqueId();
        activeSources.computeIfAbsent(id, ignored -> new HashMap<>())
            .computeIfAbsent(type, ignored -> new HashSet<>())
            .add(source);
        sourceEffects.computeIfAbsent(id, ignored -> new HashMap<>())
            .computeIfAbsent(source, ignored -> new HashSet<>())
            .add(type);
        managedTypes.computeIfAbsent(id, ignored -> new HashSet<>()).add(type);
        player.addPotionEffect(new PotionEffect(type, duration, amplifier, true, false, true));
    }

    public void end(Player player) {
        UUID id = player.getUniqueId();
        Set<PotionEffectType> managed = managedTypes.get(id);
        if (managed == null) {
            return;
        }
        Map<PotionEffectType, Set<String>> byType = activeSources.getOrDefault(id, Map.of());
        for (PotionEffectType type : new HashSet<>(managed)) {
            Set<String> sources = byType.get(type);
            if (sources == null || sources.isEmpty()) {
                player.removePotionEffect(type);
                managed.remove(type);
            }
        }
        if (managed.isEmpty()) {
            managedTypes.remove(id);
        }
    }

    public void clear(Player player) {
        UUID id = player.getUniqueId();
        Set<PotionEffectType> managed = managedTypes.remove(id);
        if (managed != null) {
            for (PotionEffectType type : managed) {
                player.removePotionEffect(type);
            }
        }
        activeSources.remove(id);
        sourceEffects.remove(id);
    }
}

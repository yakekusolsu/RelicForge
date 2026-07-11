package jp.yourname.charmorb.config;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.orb.OrbRarity;
import org.bukkit.configuration.file.FileConfiguration;

public final class PluginConfig {
    private final CharmOrbPlugin plugin;
    private int orbMaxSlots;
    private int orbUnlockMaxSlots;
    private final int[] slotWeights = new int[6];
    private int soulMaxLevel;
    private long soulDecayStartMillis;
    private long soulDecayIntervalMillis;
    private double soulAttackPerLevel;
    private final Map<OrbRarity, Integer> sellPrices = new EnumMap<>(OrbRarity.class);
    private double powerDamage;
    private double lifeStealRate;
    private double cursedBloodRate;
    private double berserkerDamage;
    private double berserkerDefensePenalty;
    private double sacrificeDamage;
    private double sacrificeSelfDamage;
    private double guardianReduction;
    private double divineGuardianReduction;
    private double crownBonus;
    private double counterChance;
    private double thunderGodChance;
    private double crushingChance;
    private long unyieldingCooldownMillis;
    private long phoenixCooldownMillis;
    private int charmMaxSlots;
    private int charmUnlockExtraSlots;
    private int charmHighTierLimit;
    private int charmMiddleHighTierLimit;

    public PluginConfig(CharmOrbPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        orbMaxSlots = clamp(config.getInt("orb.max-slots", 5), 0, 5);
        orbUnlockMaxSlots = clamp(config.getInt("orb.unlock.max-slots", 9), orbMaxSlots, 9);
        for (int i = 0; i <= 5; i++) {
            slotWeights[i] = Math.max(0, config.getInt("orb.slot-roll.slot-" + i, defaultSlotWeight(i)));
        }
        soulMaxLevel = Math.max(1, config.getInt("orb.soul-eater.max-level", 50));
        soulDecayStartMillis = Math.max(1, config.getLong("orb.soul-eater.decay-start-seconds", 180)) * 1000L;
        soulDecayIntervalMillis = Math.max(1, config.getLong("orb.soul-eater.decay-interval-seconds", 60)) * 1000L;
        soulAttackPerLevel = config.getDouble("orb.soul-eater.attack-per-level", 0.003);
        for (OrbRarity rarity : OrbRarity.values()) {
            sellPrices.put(rarity, config.getInt("orb.sell-prices." + rarity.name().toLowerCase(Locale.ROOT), rarity.price()));
        }
        powerDamage = config.getDouble("orb.effects.power-damage", 0.03);
        lifeStealRate = config.getDouble("orb.effects.life-steal-rate", 0.02);
        cursedBloodRate = config.getDouble("orb.effects.cursed-blood-rate", 0.10);
        berserkerDamage = config.getDouble("orb.effects.berserker-damage", 0.40);
        berserkerDefensePenalty = config.getDouble("orb.effects.berserker-defense-penalty", 0.30);
        sacrificeDamage = config.getDouble("orb.effects.sacrifice-damage", 0.25);
        sacrificeSelfDamage = config.getDouble("orb.effects.sacrifice-self-damage", 1.0);
        guardianReduction = config.getDouble("orb.effects.guardian-reduction", 0.06);
        divineGuardianReduction = config.getDouble("orb.effects.divine-guardian-reduction", 0.10);
        crownBonus = config.getDouble("orb.effects.crown-bonus", 0.10);
        counterChance = config.getDouble("orb.effects.counter-chance", 0.05);
        thunderGodChance = config.getDouble("orb.effects.thunder-god-chance", 0.03);
        crushingChance = config.getDouble("orb.effects.crushing-chance", 0.15);
        unyieldingCooldownMillis = Math.max(1, config.getLong("orb.effects.unyielding-cooldown-seconds", 600)) * 1000L;
        phoenixCooldownMillis = Math.max(1, config.getLong("orb.effects.phoenix-cooldown-seconds", 1800)) * 1000L;
        charmMaxSlots = Math.max(1, config.getInt("charm.max-slots", 7));
        charmUnlockExtraSlots = Math.max(0, config.getInt("charm.unlock.max-extra-slots", 20));
        charmHighTierLimit = Math.max(0, config.getInt("charm.high-tier-limit", 2));
        charmMiddleHighTierLimit = Math.max(0, config.getInt("charm.middle-high-tier-limit", 3));
    }

    private int defaultSlotWeight(int slot) {
        return switch (slot) {
            case 0 -> 35;
            case 1 -> 30;
            case 2 -> 20;
            case 3 -> 10;
            case 4 -> 4;
            case 5 -> 1;
            default -> 0;
        };
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public int orbMaxSlots() { return orbMaxSlots; }
    public int orbUnlockMaxSlots() { return orbUnlockMaxSlots; }
    public int slotWeight(int slot) { return slot >= 0 && slot < slotWeights.length ? slotWeights[slot] : 0; }
    public int soulMaxLevel() { return soulMaxLevel; }
    public long soulDecayStartMillis() { return soulDecayStartMillis; }
    public long soulDecayIntervalMillis() { return soulDecayIntervalMillis; }
    public double soulAttackPerLevel() { return soulAttackPerLevel; }
    public int sellPrice(OrbRarity rarity) { return sellPrices.getOrDefault(rarity, rarity.price()); }
    public double powerDamage() { return powerDamage; }
    public double lifeStealRate() { return lifeStealRate; }
    public double cursedBloodRate() { return cursedBloodRate; }
    public double berserkerDamage() { return berserkerDamage; }
    public double berserkerDefensePenalty() { return berserkerDefensePenalty; }
    public double sacrificeDamage() { return sacrificeDamage; }
    public double sacrificeSelfDamage() { return sacrificeSelfDamage; }
    public double guardianReduction() { return guardianReduction; }
    public double divineGuardianReduction() { return divineGuardianReduction; }
    public double crownBonus() { return crownBonus; }
    public double counterChance() { return counterChance; }
    public double thunderGodChance() { return thunderGodChance; }
    public double crushingChance() { return crushingChance; }
    public long unyieldingCooldownMillis() { return unyieldingCooldownMillis; }
    public long phoenixCooldownMillis() { return phoenixCooldownMillis; }
    public int charmMaxSlots() { return charmMaxSlots; }
    public int charmUnlockExtraSlots() { return charmUnlockExtraSlots; }
    public int charmHighTierLimit() { return charmHighTierLimit; }
    public int charmMiddleHighTierLimit() { return charmMiddleHighTierLimit; }
}

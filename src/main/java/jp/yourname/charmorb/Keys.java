package jp.yourname.charmorb;

import org.bukkit.NamespacedKey;

public final class Keys {
    public final NamespacedKey orbSystem;
    public final NamespacedKey orbSlots;
    public final NamespacedKey orbSoulLevel;
    public final NamespacedKey orbSoulKills;
    public final NamespacedKey orbSoulLastKill;
    public final NamespacedKey orbVersion;
    public final NamespacedKey orbItem;
    public final NamespacedKey orbId;
    public final NamespacedKey orbRarity;
    public final NamespacedKey orbSkillIndex;
    public final NamespacedKey unlockScroll;
    public final NamespacedKey charmItem;
    public final NamespacedKey charmId;
    public final NamespacedKey charmRarity;
    public final NamespacedKey charmElement;
    public final NamespacedKey charmLevel;
    public final NamespacedKey charmPenalty;
    public final NamespacedKey charmExtraSlots;

    public Keys(CharmOrbPlugin plugin) {
        orbSystem = key(plugin, "orb_system");
        orbSlots = key(plugin, "orb_slots");
        orbSoulLevel = key(plugin, "orb_soul_level");
        orbSoulKills = key(plugin, "orb_soul_kills");
        orbSoulLastKill = key(plugin, "orb_soul_last_kill");
        orbVersion = key(plugin, "orb_version");
        orbItem = key(plugin, "orb_item");
        orbId = key(plugin, "orb_id");
        orbRarity = key(plugin, "orb_rarity");
        orbSkillIndex = key(plugin, "orb_skill_index");
        unlockScroll = key(plugin, "unlock_scroll");
        charmItem = key(plugin, "charm_item");
        charmId = key(plugin, "charm_id");
        charmRarity = key(plugin, "charm_rarity");
        charmElement = key(plugin, "charm_element");
        charmLevel = key(plugin, "charm_level");
        charmPenalty = key(plugin, "charm_penalty");
        charmExtraSlots = key(plugin, "charm_extra_slots");
    }

    public NamespacedKey orbSlot(int slot) {
        return key(CharmOrbPlugin.getInstance(), "orb_slot_" + slot);
    }

    private static NamespacedKey key(CharmOrbPlugin plugin, String id) {
        return new NamespacedKey(plugin, id);
    }
}

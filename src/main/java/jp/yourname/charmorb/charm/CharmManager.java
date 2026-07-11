package jp.yourname.charmorb.charm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import jp.yourname.charmorb.CharmOrbPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public final class CharmManager {
    private final CharmOrbPlugin plugin;
    private final CharmItemFactory factory;
    private final Map<UUID, CharmPlayerData> data = new HashMap<>();

    public CharmManager(CharmOrbPlugin plugin) {
        this.plugin = plugin;
        this.factory = new CharmItemFactory(plugin);
    }

    public CharmPlayerData data(Player player) {
        return data.computeIfAbsent(player.getUniqueId(), ignored -> {
            CharmPlayerData loaded = new CharmPlayerData();
            loaded.setExtraSlots(player.getPersistentDataContainer().getOrDefault(plugin.keys().charmExtraSlots, PersistentDataType.INTEGER, 0));
            return loaded;
        });
    }

    public boolean unlockExtraSlot(Player player) {
        CharmPlayerData playerData = data(player);
        if (!playerData.unlockExtraSlot(plugin.settings().charmUnlockExtraSlots())) {
            return false;
        }
        player.getPersistentDataContainer().set(plugin.keys().charmExtraSlots, PersistentDataType.INTEGER, playerData.extraSlots());
        return true;
    }

    public List<ItemStack> equipped(Player player) {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack item : data(player).slots()) {
            if (factory.read(item) != null) list.add(item);
        }
        return list;
    }

    public boolean canEquip(Player player, ItemStack charm) {
        CharmType next = factory.read(charm);
        if (next == null) return false;
        int high = 0;
        int mid = 0;
        for (ItemStack item : equipped(player)) {
            CharmType type = factory.read(item);
            if (type.element().conflicts(next.element())) return false;
            if (type.rarity() == CharmRarity.MYTHIC || type.rarity() == CharmRarity.UNIQUE) high++;
            if (type.rarity() == CharmRarity.EPIC || type.rarity() == CharmRarity.LEGENDARY) mid++;
        }
        if ((next.rarity() == CharmRarity.MYTHIC || next.rarity() == CharmRarity.UNIQUE) && high >= plugin.settings().charmHighTierLimit()) return false;
        return (next.rarity() != CharmRarity.EPIC && next.rarity() != CharmRarity.LEGENDARY) || mid < plugin.settings().charmMiddleHighTierLimit();
    }

    public CharmItemFactory factory() {
        return factory;
    }
}

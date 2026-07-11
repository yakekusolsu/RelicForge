package jp.yourname.charmorb.orb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.ItemUtil;
import jp.yourname.charmorb.util.Text;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class OrbEquipmentManager {
    public static final int MAX_SLOTS = 9;
    private final CharmOrbPlugin plugin;
    private final Random random = new Random();

    public OrbEquipmentManager(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isOrbEquipment(ItemStack item) {
        if (!ItemUtil.isOrbTarget(item) || !item.hasItemMeta()) {
            return false;
        }
        Byte value = item.getItemMeta().getPersistentDataContainer().get(plugin.keys().orbSystem, PersistentDataType.BYTE);
        return value != null && value == (byte) 1;
    }

    public int rollSlots() {
        int total = 0;
        for (int i = 0; i <= MAX_SLOTS; i++) {
            total += plugin.settings().slotWeight(i);
        }
        if (total <= 0) {
            return 0;
        }
        int roll = random.nextInt(total);
        int cursor = 0;
        for (int i = 0; i <= MAX_SLOTS; i++) {
            cursor += plugin.settings().slotWeight(i);
            if (roll < cursor) {
                return Math.min(i, plugin.settings().orbMaxSlots());
            }
        }
        return 0;
    }

    public boolean initialize(ItemStack item, int slots) {
        if (!ItemUtil.isOrbTarget(item) || slots < 0 || slots > plugin.settings().orbUnlockMaxSlots()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(plugin.keys().orbSystem, PersistentDataType.BYTE, (byte) 1);
        pdc.set(plugin.keys().orbSlots, PersistentDataType.INTEGER, slots);
        pdc.set(plugin.keys().orbVersion, PersistentDataType.INTEGER, 1);
        pdc.set(plugin.keys().orbSoulLevel, PersistentDataType.INTEGER, pdc.getOrDefault(plugin.keys().orbSoulLevel, PersistentDataType.INTEGER, 0));
        pdc.set(plugin.keys().orbSoulKills, PersistentDataType.INTEGER, pdc.getOrDefault(plugin.keys().orbSoulKills, PersistentDataType.INTEGER, 0));
        pdc.set(plugin.keys().orbSoulLastKill, PersistentDataType.LONG, pdc.getOrDefault(plugin.keys().orbSoulLastKill, PersistentDataType.LONG, 0L));
        item.setItemMeta(meta);
        updateLore(item);
        return true;
    }

    public int slots(ItemStack item) {
        if (!isOrbEquipment(item)) {
            return 0;
        }
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(plugin.keys().orbSlots, PersistentDataType.INTEGER, 0);
    }

    public OrbType orbAt(ItemStack item, int slot) {
        if (slot < 1 || slot > MAX_SLOTS || !isOrbEquipment(item)) {
            return null;
        }
        String id = item.getItemMeta().getPersistentDataContainer().get(plugin.keys().orbSlot(slot), PersistentDataType.STRING);
        return OrbType.byId(id);
    }

    public List<OrbType> installed(ItemStack item) {
        if (!isOrbEquipment(item)) {
            return Collections.emptyList();
        }
        List<OrbType> result = new ArrayList<>();
        for (int i = 1; i <= slots(item); i++) {
            OrbType type = orbAt(item, i);
            if (type != null) {
                result.add(type);
            }
        }
        return result;
    }

    public boolean has(ItemStack item, OrbType type) {
        return installed(item).contains(type);
    }

    public boolean insert(ItemStack item, int slot, OrbType type) {
        if (type == null || slot < 1 || slot > slots(item)) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (pdc.has(plugin.keys().orbSlot(slot), PersistentDataType.STRING)) {
            return false;
        }
        pdc.set(plugin.keys().orbSlot(slot), PersistentDataType.STRING, type.id());
        item.setItemMeta(meta);
        updateLore(item);
        return true;
    }

    public boolean unlockSlot(ItemStack item) {
        if (!isOrbEquipment(item)) {
            return false;
        }
        int current = slots(item);
        if (current >= plugin.settings().orbUnlockMaxSlots()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(plugin.keys().orbSlots, PersistentDataType.INTEGER, current + 1);
        item.setItemMeta(meta);
        updateLore(item);
        return true;
    }

    public OrbType remove(ItemStack item, int slot) {
        OrbType existing = orbAt(item, slot);
        if (existing == null) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().remove(plugin.keys().orbSlot(slot));
        item.setItemMeta(meta);
        updateLore(item);
        return existing;
    }

    public int soulLevel(ItemStack item) {
        if (!isOrbEquipment(item)) {
            return 0;
        }
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(plugin.keys().orbSoulLevel, PersistentDataType.INTEGER, 0);
    }

    public int soulKills(ItemStack item) {
        if (!isOrbEquipment(item)) {
            return 0;
        }
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(plugin.keys().orbSoulKills, PersistentDataType.INTEGER, 0);
    }

    public void addSoulKill(ItemStack item, int amount) {
        if (!isOrbEquipment(item) || amount <= 0) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        int level = Math.min(plugin.settings().soulMaxLevel(), pdc.getOrDefault(plugin.keys().orbSoulLevel, PersistentDataType.INTEGER, 0) + amount);
        int kills = pdc.getOrDefault(plugin.keys().orbSoulKills, PersistentDataType.INTEGER, 0) + amount;
        pdc.set(plugin.keys().orbSoulLevel, PersistentDataType.INTEGER, level);
        pdc.set(plugin.keys().orbSoulKills, PersistentDataType.INTEGER, kills);
        pdc.set(plugin.keys().orbSoulLastKill, PersistentDataType.LONG, System.currentTimeMillis());
        item.setItemMeta(meta);
        updateLore(item);
    }

    public void decaySoul(ItemStack item) {
        if (!isOrbEquipment(item) || !has(item, OrbType.SOUL_EATER)) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        int level = pdc.getOrDefault(plugin.keys().orbSoulLevel, PersistentDataType.INTEGER, 0);
        long lastKill = pdc.getOrDefault(plugin.keys().orbSoulLastKill, PersistentDataType.LONG, 0L);
        if (level <= 0 || lastKill <= 0L || System.currentTimeMillis() - lastKill < plugin.settings().soulDecayStartMillis()) {
            return;
        }
        pdc.set(plugin.keys().orbSoulLevel, PersistentDataType.INTEGER, level - 1);
        pdc.set(plugin.keys().orbSoulLastKill, PersistentDataType.LONG, System.currentTimeMillis() - plugin.settings().soulDecayStartMillis() + plugin.settings().soulDecayIntervalMillis());
        item.setItemMeta(meta);
        updateLore(item);
    }

    public void updateLore(ItemStack item) {
        if (!ItemUtil.isOrbTarget(item) || !item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(plugin.keys().orbSystem, PersistentDataType.BYTE)) {
            return;
        }
        int slots = pdc.getOrDefault(plugin.keys().orbSlots, PersistentDataType.INTEGER, 0);
        if (slots <= 0) {
            meta.lore(null);
            item.setItemMeta(meta);
            return;
        }
        List<net.kyori.adventure.text.Component> lore = new ArrayList<>();
        lore.add(Text.c("&8&m----------------"));
        lore.add(Text.c("&a基本ステータス"));
        lore.add(Text.c("&7  オーブスロット: &a" + slots + "&7/" + plugin.settings().orbUnlockMaxSlots() + " &8" + quality(slots)));
        lore.add(Text.c("&bオプション効果"));
        for (int i = 1; i <= slots; i++) {
            OrbType type = OrbType.byId(pdc.get(plugin.keys().orbSlot(i), PersistentDataType.STRING));
            if (type == null) {
                lore.add(Text.c("&7[" + i + "] 空きスロット"));
            } else {
                lore.add(Text.c("&7[" + i + "] " + OrbFlavor.coloredName(type) + " &7/ &f" + type.description()));
            }
        }
        if (has(item, OrbType.SOUL_EATER)) {
            lore.add(Text.c("&c魂レベル: &f" + soulLevel(item) + "&7/" + plugin.settings().soulMaxLevel()));
            lore.add(Text.c("&c討伐記憶: &f" + soulKills(item)));
        }
        lore.add(Text.c("&8&m----------------"));
        meta.lore(lore);
        item.setItemMeta(meta);
    }

    public String quality(int slots) {
        return switch (slots) {
            case 0 -> "スロットなし";
            case 1 -> "粗悪";
            case 2 -> "通常";
            case 3 -> "良質";
            case 4 -> "希少";
            case 5 -> "完全";
            case 6 -> "解放 I";
            case 7 -> "解放 II";
            case 8 -> "解放 III";
            case 9 -> "解放 IV";
            default -> "不明";
        };
    }
}

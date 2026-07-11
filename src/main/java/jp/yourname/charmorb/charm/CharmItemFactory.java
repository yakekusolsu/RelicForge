package jp.yourname.charmorb.charm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.Text;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class CharmItemFactory {
    private final CharmOrbPlugin plugin;
    private final Random random = new Random();

    public CharmItemFactory(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack create(CharmType type) {
        int level = random.nextInt(type.rarity().maxLevel() - type.rarity().minLevel() + 1) + type.rarity().minLevel();
        ItemStack item = new ItemStack(type.rarity() == CharmRarity.CURSE ? Material.ECHO_SHARD : Material.PRISMARINE_CRYSTALS);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Text.c(CharmFlavor.coloredName(type)));
        List<String> lore = new ArrayList<>();
        lore.add("&8チャーム護符");
        lore.add("");
        lore.add("&a基本ステータス");
        lore.add(CharmFlavor.baseSpec(type, level));
        lore.add("&7  属性傾向: &e" + CharmFlavor.elementTitle(type.element()));
        lore.add("&7  装備枠: &fチャームスロット");
        lore.add("");
        if (type.rarity() == CharmRarity.CURSE) {
            addCurseLore(lore, type, level);
        } else {
            addNormalLore(lore, type, level);
        }
        meta.lore(lore.stream().map(Text::c).toList());
        meta.getPersistentDataContainer().set(plugin.keys().charmItem, PersistentDataType.BYTE, (byte) 1);
        meta.getPersistentDataContainer().set(plugin.keys().charmId, PersistentDataType.STRING, type.id());
        meta.getPersistentDataContainer().set(plugin.keys().charmRarity, PersistentDataType.STRING, type.rarity().name());
        meta.getPersistentDataContainer().set(plugin.keys().charmElement, PersistentDataType.STRING, type.element().name());
        meta.getPersistentDataContainer().set(plugin.keys().charmLevel, PersistentDataType.INTEGER, level);
        item.setItemMeta(meta);
        return item;
    }

    private void addNormalLore(List<String> lore, CharmType type, int level) {
        List<CharmPositiveEffectType> global = pick(CharmPositiveEffectType.global(), globalPlusCount(type.rarity()));
        List<CharmPositiveEffectType> elemental = pick(CharmPositiveEffectType.elemental(type.element()), elementalPlusCount(type.rarity()));
        List<CharmNegativeEffectType> negatives = pick(List.of(CharmNegativeEffectType.values()), negativeCount(type.rarity()));
        List<CharmResonanceType> resonances = resonanceHints(type);

        lore.add("&bプレイヤー本体");
        addPositiveLines(lore, global, level, 1);
        lore.add("");
        lore.add("&6属性効果");
        addPositiveLines(lore, elemental, level, 1);
        lore.add("");
        lore.add("&d共鳴");
        for (CharmResonanceType resonance : resonances) {
            lore.add("&7  &d共鳴候補: &f" + resonance.displayName() + " &8" + resonance.condition() + " &7/ &f" + resonance.description());
        }
        lore.add("");
        lore.add("&c不安定効果");
        if (negatives.isEmpty()) {
            lore.add("&7  &c-効果: &7なし");
        } else {
            for (int i = 0; i < negatives.size(); i++) {
                CharmNegativeEffectType negative = negatives.get(i);
                lore.add("&7  &c-効果" + (i + 1) + ": &f" + negative.displayName() + " &cLv" + penaltyLevel(level, type.rarity()) + " &7/ &f" + negative.description());
            }
        }
    }

    private void addCurseLore(List<String> lore, CharmType type, int level) {
        List<CharmCursePositiveEffectType> positives = pick(List.of(CharmCursePositiveEffectType.values()), random.nextBoolean() ? 1 : 2);
        List<CharmCurseNegativeEffectType> negatives = pick(List.of(CharmCurseNegativeEffectType.values()), random.nextBoolean() ? 1 : 2);
        List<CharmResonanceType> resonances = resonanceHints(type);

        lore.add("&4呪いの恩恵");
        for (int i = 0; i < positives.size(); i++) {
            CharmCursePositiveEffectType positive = positives.get(i);
            lore.add("&7  &a+呪効果" + (i + 1) + ": &f" + positive.displayName() + " &aLv" + level + " &7/ &f" + positive.description());
        }
        lore.add("");
        lore.add("&5呪い共鳴");
        for (CharmResonanceType resonance : resonances) {
            lore.add("&7  &d共鳴候補: &f" + resonance.displayName() + " &8" + resonance.condition() + " &7/ &f" + resonance.description());
        }
        lore.add("");
        lore.add("&c呪いの代償");
        for (int i = 0; i < negatives.size(); i++) {
            CharmCurseNegativeEffectType negative = negatives.get(i);
            lore.add("&7  &c-呪効果" + (i + 1) + ": &f" + negative.displayName() + " &cLv" + penaltyLevel(level, type.rarity()) + " &7/ &f" + negative.description());
        }
    }

    private void addPositiveLines(List<String> lore, List<CharmPositiveEffectType> effects, int level, int start) {
        for (int i = 0; i < effects.size(); i++) {
            CharmPositiveEffectType effect = effects.get(i);
            lore.add("&7  &a+効果" + (start + i) + ": &f" + effect.displayName() + " &aLv" + level + " &7/ &f" + effect.description());
        }
    }

    private List<CharmResonanceType> resonanceHints(CharmType type) {
        List<CharmResonanceType> same = CharmResonanceType.sameElement(type.element());
        int count = switch (type.rarity()) {
            case COMMON, UNCOMMON -> 1;
            case RARE, EPIC -> 2;
            default -> 3;
        };
        return same.subList(0, Math.min(count, same.size()));
    }

    private <T> List<T> pick(List<T> source, int count) {
        if (count <= 0 || source.isEmpty()) {
            return List.of();
        }
        List<T> copy = new ArrayList<>(source);
        Collections.shuffle(copy, random);
        return copy.subList(0, Math.min(count, copy.size()));
    }

    private int globalPlusCount(CharmRarity rarity) {
        return switch (rarity) {
            case COMMON -> 1;
            case UNCOMMON, RARE -> 1;
            case EPIC, LEGENDARY -> 2;
            case MYTHIC, UNIQUE -> 2;
            case CURSE -> 0;
        };
    }

    private int elementalPlusCount(CharmRarity rarity) {
        return switch (rarity) {
            case COMMON -> 0;
            case UNCOMMON -> random.nextBoolean() ? 0 : 1;
            case RARE, EPIC -> 1;
            case LEGENDARY -> 1;
            case MYTHIC, UNIQUE -> 2;
            case CURSE -> 0;
        };
    }

    private int negativeCount(CharmRarity rarity) {
        return switch (rarity) {
            case COMMON, UNCOMMON -> 0;
            case RARE -> random.nextInt(100) < 35 ? 1 : 0;
            case EPIC, LEGENDARY -> random.nextInt(100) < 55 ? 1 : 0;
            case MYTHIC -> 1;
            case UNIQUE -> random.nextBoolean() ? 1 : 0;
            case CURSE -> 0;
        };
    }

    private int penaltyLevel(int level, CharmRarity rarity) {
        int value = Math.max(1, level - 1);
        return rarity == CharmRarity.CURSE ? Math.max(2, value) : value;
    }

    public CharmType read(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        return CharmType.byId(item.getItemMeta().getPersistentDataContainer().get(plugin.keys().charmId, PersistentDataType.STRING));
    }

    public int level(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return 0;
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(plugin.keys().charmLevel, PersistentDataType.INTEGER, 0);
    }
}

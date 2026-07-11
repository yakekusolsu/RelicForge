package jp.yourname.charmorb.orb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class OrbFlavor {
    private OrbFlavor() {
    }

    public static List<String> itemLore(OrbType type) {
        List<String> lore = new ArrayList<>();
        lore.add("&8オーブ強化素材");
        lore.add("");
        lore.add("&a基本ステータス");
        lore.add("&7  種別: &fオーブ強化素材");
        lore.add("&7  希少度: " + rarityName(type));
        lore.add("&7  適正装備: &f" + targetSummary(type));
        lore.add("");
        if (type.rarity() == OrbRarity.CURSE) {
            cursePlusEffects(type).forEach(lore::add);
            curseMinusEffects(type).forEach(lore::add);
            curseSkills(type).forEach(lore::add);
        } else {
            lore.add("&bプラス効果");
            plusEffects(type).forEach(lore::add);
            minusEffects(type).forEach(lore::add);
            comboEffects(type).forEach(lore::add);
            skillEffects(type).forEach(lore::add);
        }
        lore.add("&7  操作: &eShift+Scroll &7でスキル切替");
        lore.add("&7  発動: &eShift+右クリック &7で選択中スキル使用");
        return lore;
    }

    public static String socketLine(OrbType type) {
        return coloredName(type) + " &7- &f" + type.description();
    }

    public static String coloredName(OrbType type) {
        return type.rarity().color() + type.displayName()
            + " &8[" + rarityName(type) + "&8]";
    }

    public static List<String> plusEffects(OrbType type) {
        List<String> effects = new ArrayList<>();
        List<OrbPositiveEffectType> picked = generatedPlusEffects(type);
        for (int i = 0; i < picked.size(); i++) {
            OrbPositiveEffectType effect = picked.get(i);
            effects.add("&7  &a+効果" + (i + 1) + ": &f" + effect.displayName() + " &8" + effect.target().displayName() + " &7/ &f" + effect.description());
        }
        return effects;
    }

    public static List<String> minusEffects(OrbType type) {
        List<OrbNegativeEffectType> picked = generatedMinusEffects(type);
        if (picked.isEmpty()) {
            return List.of();
        }
        List<String> effects = new ArrayList<>();
        for (int i = 0; i < picked.size(); i++) {
            OrbNegativeEffectType effect = picked.get(i);
            effects.add("&7  &c-効果" + (i + 1) + ": &f" + effect.displayName() + " &8" + effect.target().displayName() + " &7/ &f" + effect.description());
        }
        return effects;
    }

    public static List<String> comboEffects(OrbType type) {
        int count = switch (type.rarity()) {
            case COMMON, UNCOMMON -> 0;
            case RARE -> 1;
            case EPIC, LEGENDARY -> 2;
            case MYTHIC, UNIQUE -> 3;
            case CURSE -> 0;
        };
        if (count <= 0) {
            return List.of();
        }
        List<String> lines = new ArrayList<>();
        lines.add("&dコンボ能力");
        for (OrbComboType combo : pick(Arrays.asList(OrbComboType.values()), count, type.ordinal() + 61)) {
            lines.add("&7  &dコンボ: &f" + combo.displayName() + " &8" + combo.target().displayName() + " &7/ &f" + combo.description());
        }
        return lines;
    }

    public static List<String> skillEffects(OrbType type) {
        int count = skillCount(type);
        if (count <= 0) {
            return List.of();
        }
        List<String> lines = new ArrayList<>();
        lines.add("&e通常スキル");
        for (OrbSkillType skill : generatedSkills(type)) {
            lines.add("&7  &eスキル: &f" + skill.displayName() + " &8CT " + skill.cooldownSeconds() + "秒 &7/ &f" + skill.description());
        }
        return lines;
    }

    public static List<String> cursePlusEffects(OrbType type) {
        List<String> lines = new ArrayList<>();
        lines.add("&4呪いの超効果");
        List<OrbCursePositiveEffectType> picked = generatedCursePlusEffects(type);
        for (int i = 0; i < picked.size(); i++) {
            OrbCursePositiveEffectType effect = picked.get(i);
            lines.add("&7  &a+呪効果" + (i + 1) + ": &f" + effect.displayName() + " &8" + effect.target().displayName() + " &7/ &f" + effect.description());
        }
        return lines;
    }

    public static List<String> curseMinusEffects(OrbType type) {
        List<String> lines = new ArrayList<>();
        lines.add("&c呪いの代償");
        List<OrbCurseNegativeEffectType> picked = generatedCurseMinusEffects(type);
        for (int i = 0; i < picked.size(); i++) {
            OrbCurseNegativeEffectType effect = picked.get(i);
            lines.add("&7  &c-呪効果" + (i + 1) + ": &f" + effect.displayName() + " &8" + effect.target().displayName() + " &7/ &f" + effect.description());
        }
        return lines;
    }

    public static List<String> curseSkills(OrbType type) {
        int count = curseSkillCount(type);
        if (count <= 0) {
            return List.of();
        }
        List<String> lines = new ArrayList<>();
        lines.add("&5カーススキル");
        for (OrbCurseSkillType skill : generatedCurseSkills(type)) {
            lines.add("&7  &5カース: &f" + skill.displayName() + " &8CT " + skill.cooldownSeconds() + "秒 &7/ &f" + skill.description());
            lines.add("&7    &c代償: &f" + skill.cost());
        }
        return lines;
    }

    public static String flavor(OrbType type) {
        return switch (type.rarity()) {
            case COMMON -> "淡い魔力を宿した、冒険者の基本となる欠片。";
            case UNCOMMON -> "戦場で磨かれた気配を持つ、癖の少ない強化核。";
            case RARE -> "魔物の血と鉱脈の熱が混じった、鋭い輝きの結晶。";
            case EPIC -> "装備に刻むと、武具そのものが脈打つように応える。";
            case LEGENDARY -> "古い英雄譚に名を残す、規格外の力を秘めた宝珠。";
            case MYTHIC -> "世界法則の一部を削り取って封じた神話級の核。";
            case UNIQUE -> "同じものは二つとない、運命をねじ曲げる特異点。";
            case CURSE -> "代償を求める呪核。力は強いが、扱う者を選ばない。";
        };
    }

    public static String rarityName(OrbType type) {
        return switch (type.rarity()) {
            case COMMON -> "&f一般";
            case UNCOMMON -> "&a上等";
            case RARE -> "&9希少";
            case EPIC -> "&5秘宝";
            case LEGENDARY -> "&6伝説";
            case MYTHIC -> "&d神話";
            case UNIQUE -> "&b唯一";
            case CURSE -> "&4呪い";
        };
    }

    public static int plusCount(OrbType type) {
        return switch (type.rarity()) {
            case COMMON, UNCOMMON -> 1;
            case RARE, EPIC -> 2;
            case LEGENDARY -> 3;
            case MYTHIC -> 4;
            case UNIQUE -> 4;
            case CURSE -> 0;
        };
    }

    public static List<OrbSkillType> generatedSkills(OrbType type) {
        return pick(Arrays.asList(OrbSkillType.values()), skillCount(type), type.ordinal() + 97);
    }

    public static List<OrbCurseSkillType> generatedCurseSkills(OrbType type) {
        return pick(Arrays.asList(OrbCurseSkillType.values()), curseSkillCount(type), type.ordinal() + 199);
    }

    public static List<OrbPositiveEffectType> generatedPlusEffects(OrbType type) {
        return pick(Arrays.asList(OrbPositiveEffectType.values()), plusCount(type), type.ordinal());
    }

    public static List<OrbNegativeEffectType> generatedMinusEffects(OrbType type) {
        return pick(Arrays.asList(OrbNegativeEffectType.values()), minusCount(type), type.ordinal() + 31);
    }

    public static List<OrbCursePositiveEffectType> generatedCursePlusEffects(OrbType type) {
        return pick(Arrays.asList(OrbCursePositiveEffectType.values()), cursePlusCount(type), type.ordinal() + 131);
    }

    public static List<OrbCurseNegativeEffectType> generatedCurseMinusEffects(OrbType type) {
        return pick(Arrays.asList(OrbCurseNegativeEffectType.values()), curseMinusCount(type), type.ordinal() + 167);
    }

    private static int minusCount(OrbType type) {
        return switch (type.rarity()) {
            case COMMON, UNCOMMON -> 0;
            case RARE -> type.ordinal() % 2 == 0 ? 1 : 0;
            case EPIC, LEGENDARY -> 1;
            case MYTHIC -> 2;
            case UNIQUE -> 2;
            case CURSE -> 0;
        };
    }

    private static int skillCount(OrbType type) {
        return switch (type.rarity()) {
            case COMMON, UNCOMMON, RARE, EPIC, CURSE -> 0;
            case LEGENDARY -> type.ordinal() % 2;
            case MYTHIC -> 1 + type.ordinal() % 2;
            case UNIQUE -> 1 + type.ordinal() % 3;
        };
    }

    private static int cursePlusCount(OrbType type) {
        return 1 + type.ordinal() % 5;
    }

    private static int curseMinusCount(OrbType type) {
        return 1 + type.ordinal() % 3;
    }

    private static int curseSkillCount(OrbType type) {
        return type.ordinal() % 5;
    }

    private static String targetSummary(OrbType type) {
        List<OrbPositiveEffectType> picked = pick(Arrays.asList(OrbPositiveEffectType.values()), Math.max(1, plusCount(type)), type.ordinal());
        if (type.rarity() == OrbRarity.CURSE) {
            return "武器・防具・ツール";
        }
        return picked.stream().map(effect -> effect.target().displayName()).distinct().limit(3).reduce((a, b) -> a + " / " + b).orElse("汎用");
    }

    private static <T> List<T> pick(List<T> source, int count, int seed) {
        if (count <= 0 || source.isEmpty()) {
            return List.of();
        }
        List<T> copy = new ArrayList<>(source);
        Collections.rotate(copy, Math.floorMod(seed * 7, copy.size()));
        return copy.subList(0, Math.min(count, copy.size()));
    }
}

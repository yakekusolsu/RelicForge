package jp.yourname.charmorb.charm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class CharmFlavor {
    private CharmFlavor() {
    }

    public static String elementTitle(CharmElement element) {
        return switch (element) {
            case FIRE -> "燃焼と破壊";
            case ICE -> "凍結と堅牢";
            case WATER -> "再生と流転";
            case WIND -> "速度と回避";
            case EARTH -> "防護と持久";
            case LIGHT -> "浄化と加護";
            case DARK -> "吸収と報復";
            case NATURE -> "生命と収穫";
            case THUNDER -> "瞬撃と感電";
            case VOID -> "侵食と虚無";
        };
    }

    public static String elementColor(CharmElement element) {
        return switch (element) {
            case FIRE -> "&c";
            case ICE -> "&b";
            case WATER -> "&3";
            case WIND -> "&a";
            case EARTH -> "&2";
            case LIGHT -> "&e";
            case DARK -> "&5";
            case NATURE -> "&a";
            case THUNDER -> "&6";
            case VOID -> "&8";
        };
    }

    public static String coloredName(CharmType type) {
        return elementColor(type.element()) + type.displayName()
            + " &8[" + rarityName(type)
            + "&8 / " + elementColor(type.element()) + type.element().jp() + "&8]";
    }

    public static String effectName(String effect) {
        return switch (effect) {
            case "attack" -> "攻撃強化";
            case "defense" -> "防御強化";
            case "speed" -> "移動速度上昇";
            case "health" -> "生命力上昇";
            case "luck" -> "幸運上昇";
            case "regen" -> "自然回復強化";
            case "berserk_attack" -> "狂戦士の加護";
            case "life_steal" -> "吸命";
            case "curse_regen" -> "呪われた再生";
            case "revenge_power" -> "報復の力";
            case "executioner" -> "処刑者の印";
            case "soul_eater" -> "魂喰い";
            case "dark_armor" -> "闇の守り";
            case "forbidden_speed" -> "禁じられた俊足";
            case "curse_luck" -> "歪んだ幸運";
            default -> prettify(effect);
        };
    }

    public static String penaltyName(String penalty) {
        return switch (penalty) {
            case "fragile_body" -> "脆い肉体";
            case "armor_decay" -> "装甲劣化";
            case "unstable_soul" -> "不安定な魂";
            case "life_drain" -> "生命流出";
            case "hunger_curse" -> "飢餓の呪い";
            case "slow_curse" -> "鈍足の呪い";
            case "weakness_curse" -> "弱体の呪い";
            case "healing_block" -> "治癒阻害";
            case "sun_burn" -> "日光灼け";
            case "water_fear" -> "水への恐怖";
            case "soul_price" -> "魂の代価";
            case "curse_noise" -> "呪いの囁き";
            default -> prettify(penalty);
        };
    }

    public static String flavor(CharmType type) {
        return type.rarity() == CharmRarity.CURSE
            ? "強烈な恩恵と代償が同居する、危険な護符。"
            : elementTitle(type.element()) + "の性質を帯びた、装備者のビルドを尖らせる護符。";
    }

    public static String baseSpec(CharmType type, int level) {
        return "&7  基礎値: &aLv" + level + " " + elementColor(type.element()) + type.element().jp() + "属性 &8/ " + rarityName(type) + "級";
    }

    public static List<String> plusEffectLines(CharmType type, String effect, int level) {
        List<String> lines = new ArrayList<>();
        lines.add("&7  &a+効果1: &f" + effectName(effect) + " &aLv" + level);
        int count = plusCount(type);
        if (count >= 2) {
            lines.add("&7  &a+効果2: &f" + type.element().jp() + "属性の威力上昇");
        }
        if (count >= 3) {
            lines.add("&7  &a+効果3: &f同属性ビルド補正");
        }
        if (count >= 4) {
            lines.add("&7  &a+効果4: &f特化構成で追加補正");
        }
        return lines;
    }

    public static List<String> minusEffectLines(CharmType type, String penalty) {
        if (type.rarity() != CharmRarity.CURSE || penalty == null || penalty.isEmpty()) {
            return List.of("&7  &c-効果: &7なし");
        }
        List<String> lines = new ArrayList<>();
        lines.add("&7  &c-効果1: &f" + penaltyName(penalty));
        lines.add("&7  &c-効果2: &f呪いの反動");
        return lines;
    }

    public static String rarityName(CharmType type) {
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

    private static String prettify(String value) {
        String[] parts = value.toLowerCase(Locale.ROOT).split("_");
        StringBuilder out = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) continue;
            if (!out.isEmpty()) out.append(' ');
            out.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return out.toString();
    }

    private static int plusCount(CharmType type) {
        return switch (type.rarity()) {
            case COMMON, UNCOMMON -> 1;
            case RARE, EPIC -> 2;
            case LEGENDARY, MYTHIC, CURSE -> 3;
            case UNIQUE -> 4;
        };
    }
}

package jp.yourname.charmorb.orb;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OrbType {
    VITALITY("vitality", OrbRarity.COMMON, "活力のオーブ", "最大体力+2"),
    MINING("mining", OrbRarity.COMMON, "採掘のオーブ", "採掘速度+5%"),
    LIGHTWEIGHT("lightweight", OrbRarity.COMMON, "軽量のオーブ", "移動速度+3%"),
    GUARDIAN("guardian", OrbRarity.COMMON, "守護のオーブ", "防御力+2"),
    POWER("power", OrbRarity.COMMON, "力のオーブ", "攻撃力+3%"),
    DURABILITY("durability", OrbRarity.COMMON, "耐久のオーブ", "耐久値消費-10%"),
    SATIETY("satiety", OrbRarity.COMMON, "満腹のオーブ", "空腹消費-10%"),
    SWIMMING("swimming", OrbRarity.COMMON, "水泳のオーブ", "水中移動速度+10%"),
    GALE("gale", OrbRarity.UNCOMMON, "疾風のオーブ", "ダッシュ速度+10%"),
    LIFE_STEAL("life_steal", OrbRarity.UNCOMMON, "吸命のオーブ", "与ダメージの2%回復"),
    FORTUNE("fortune", OrbRarity.UNCOMMON, "幸運のオーブ", "ドロップ率+5%"),
    PRECISION("precision", OrbRarity.UNCOMMON, "精密のオーブ", "飛び道具の命中率向上"),
    FORESTMAN("forestman", OrbRarity.UNCOMMON, "森人のオーブ", "木材採取速度+20%"),
    DEEP_SEA("deep_sea", OrbRarity.UNCOMMON, "深海のオーブ", "水中呼吸時間延長"),
    TORCHLIGHT("torchlight", OrbRarity.UNCOMMON, "灯火のオーブ", "暗所で夜目効果"),
    COUNTER("counter", OrbRarity.UNCOMMON, "反撃のオーブ", "被ダメ時5%で反撃"),
    SCORCHING("scorching", OrbRarity.RARE, "灼熱のオーブ", "攻撃で炎上"),
    FREEZING("freezing", OrbRarity.RARE, "氷結のオーブ", "攻撃で鈍足付与"),
    THUNDERCLAP("thunderclap", OrbRarity.RARE, "迅雷のオーブ", "攻撃速度+10%"),
    CRUSHING("crushing", OrbRarity.RARE, "粉砕のオーブ", "範囲ダメージ発生"),
    REGENERATION("regeneration", OrbRarity.RARE, "再生のオーブ", "戦闘外で体力回復"),
    MANA("mana", OrbRarity.RARE, "魔力のオーブ", "エンチャント効果+10%"),
    HEAVY_STRIKE("heavy_strike", OrbRarity.RARE, "重撃のオーブ", "クリティカルダメージ増加"),
    PROSPECTING("prospecting", OrbRarity.RARE, "探鉱のオーブ", "鉱石発見時に発光"),
    THUNDER_GOD("thunder_god", OrbRarity.EPIC, "雷神のオーブ", "低確率で落雷"),
    UNYIELDING("unyielding", OrbRarity.EPIC, "不屈のオーブ", "致命傷を一度だけ耐える"),
    BLOOD_RAGE("blood_rage", OrbRarity.EPIC, "血狂のオーブ", "体力半分以下で攻撃+25%"),
    DIVINE_GUARDIAN("divine_guardian", OrbRarity.EPIC, "守護神のオーブ", "ダメージ軽減シールド発生"),
    HARVEST("harvest", OrbRarity.EPIC, "豊穣のオーブ", "作物収穫量増加"),
    SWIFT_STEP("swift_step", OrbRarity.EPIC, "瞬足のオーブ", "回避後速度上昇"),
    STORM("storm", OrbRarity.EPIC, "暴風のオーブ", "ノックバック強化"),
    VENOM("venom", OrbRarity.EPIC, "猛毒のオーブ", "毒を付与"),
    PHOENIX("phoenix", OrbRarity.LEGENDARY, "不死鳥のオーブ", "死亡時一度だけ復活"),
    VOID("void", OrbRarity.LEGENDARY, "虚空のオーブ", "エンダー系の敵特効"),
    HOLY_LIGHT("holy_light", OrbRarity.LEGENDARY, "聖光のオーブ", "アンデッド特効・暗闇無効"),
    SPACE_TIME("space_time", OrbRarity.LEGENDARY, "時空のオーブ", "短距離ブリンク"),
    AWAKENING("awakening", OrbRarity.LEGENDARY, "覚醒のオーブ", "体力30%以下で全能力強化"),
    GIANT("giant", OrbRarity.LEGENDARY, "巨人のオーブ", "最大体力大幅増加"),
    HUNT_GOD("hunt_god", OrbRarity.LEGENDARY, "狩神のオーブ", "魔物へのダメージ+20%"),
    KING("king", OrbRarity.LEGENDARY, "王者のオーブ", "ボスへのダメージ+15%"),
    GODSPEED("godspeed", OrbRarity.MYTHIC, "神速のオーブ", "攻撃速度・採掘速度大幅上昇"),
    END("end", OrbRarity.MYTHIC, "終焉のオーブ", "敵撃破で攻撃力が一時上昇"),
    STAR_CORE("star_core", OrbRarity.MYTHIC, "星核のオーブ", "周囲へ星の欠片を放つ"),
    WORLD_TREE("world_tree", OrbRarity.MYTHIC, "世界樹のオーブ", "味方を徐々に回復"),
    ABYSS("abyss", OrbRarity.MYTHIC, "奈落のオーブ", "体力が少ないほど攻撃上昇"),
    SKY("sky", OrbRarity.MYTHIC, "天穹のオーブ", "落下ダメージ無効・ジャンプ強化"),
    DRAGON_SOUL("dragon_soul", OrbRarity.MYTHIC, "竜魂のオーブ", "ドラゴン系への大ダメージ"),
    CREATION("creation", OrbRarity.MYTHIC, "創造のオーブ", "ブロック設置・破壊速度上昇"),
    FATE("fate", OrbRarity.UNIQUE, "運命のオーブ", "ランダムで能力が変化する"),
    RULER("ruler", OrbRarity.UNIQUE, "支配者のオーブ", "周囲の敵を弱体化"),
    TIME("time", OrbRarity.UNIQUE, "時のオーブ", "クールタイムを20%短縮"),
    INFINITY("infinity", OrbRarity.UNIQUE, "無限のオーブ", "耐久値を消費しなくなることがある"),
    CHAOS("chaos", OrbRarity.UNIQUE, "混沌のオーブ", "ランダムな強化・弱体化が発生"),
    HERO("hero", OrbRarity.UNIQUE, "英雄のオーブ", "パーティ人数に応じて能力上昇"),
    CROWN("crown", OrbRarity.UNIQUE, "王冠のオーブ", "全ステータス+10%"),
    GENESIS("genesis", OrbRarity.UNIQUE, "創世のオーブ", "全オーブ効果を少し強化"),
    BERSERKER("berserker", OrbRarity.CURSE, "狂戦士のオーブ", "攻撃+40%、防御-30%"),
    CURSED_BLOOD("cursed_blood", OrbRarity.CURSE, "呪血のオーブ", "与ダメ回復+10%、自然回復不可"),
    RUNAWAY("runaway", OrbRarity.CURSE, "暴走のオーブ", "攻撃速度+30%、空腹消費2倍"),
    SACRIFICE("sacrifice", OrbRarity.CURSE, "代償のオーブ", "与ダメ+25%、攻撃時に自傷"),
    FRAILTY("frailty", OrbRarity.CURSE, "虚弱のオーブ", "最大体力-30%、移動速度+20%"),
    BINDING("binding", OrbRarity.CURSE, "呪縛のオーブ", "防御+30%、移動速度-25%"),
    DEEP_ABYSS("deep_abyss", OrbRarity.CURSE, "深淵のオーブ", "夜は超強化、昼は弱体化"),
    SOUL_EATER("soul_eater", OrbRarity.CURSE, "魂喰いのオーブ", "敵撃破で成長、時間経過で弱体");

    private static final Map<String, OrbType> BY_ID = Arrays.stream(values()).collect(Collectors.toMap(OrbType::id, Function.identity()));

    private final String id;
    private final OrbRarity rarity;
    private final String displayName;
    private final String description;

    OrbType(String id, OrbRarity rarity, String displayName, String description) {
        this.id = id;
        this.rarity = rarity;
        this.displayName = displayName;
        this.description = description;
    }

    public String id() {
        return id;
    }

    public OrbRarity rarity() {
        return rarity;
    }

    public String displayName() {
        return displayName;
    }

    public String description() {
        return description;
    }

    public static OrbType byId(String raw) {
        if (raw == null) return null;
        String id = raw.toLowerCase(Locale.ROOT);
        if (id.equals("common_growth")) id = "soul_eater";
        return BY_ID.get(id);
    }

    public static List<OrbType> byRarity(OrbRarity rarity) {
        return Arrays.stream(values()).filter(type -> type.rarity == rarity).toList();
    }
}

package jp.yourname.charmorb.charm;

import java.util.Arrays;
import java.util.List;

public enum CharmResonanceType {
    FIRE_2("fire_resonance_2", "火の共鳴", "火2個", "攻撃時に低確率で炎上", CharmElement.FIRE, 2),
    FIRE_3("fire_resonance_3", "紅蓮共鳴", "火3個", "炎上中の敵へのダメージ上昇", CharmElement.FIRE, 3),
    FIRE_5("fire_resonance_5", "業火共鳴", "火5個", "周囲に炎ダメージ", CharmElement.FIRE, 5),
    FIRE_7("fire_resonance_7", "太陽共鳴", "火7個", "攻撃力大幅上昇、水中で弱体化", CharmElement.FIRE, 7),
    ICE_2("ice_resonance_2", "氷の共鳴", "氷2個", "攻撃時に鈍足付与", CharmElement.ICE, 2),
    ICE_3("ice_resonance_3", "凍結共鳴", "氷3個", "鈍足中の敵へのダメージ上昇", CharmElement.ICE, 3),
    ICE_5("ice_resonance_5", "絶零共鳴", "氷5個", "周囲の敵を定期的に鈍足化", CharmElement.ICE, 5),
    ICE_7("ice_resonance_7", "白銀共鳴", "氷7個", "被ダメージ軽減、移動速度少し低下", CharmElement.ICE, 7),
    WATER_2("water_resonance_2", "水の共鳴", "水2個", "水中呼吸", CharmElement.WATER, 2),
    WATER_3("water_resonance_3", "清流共鳴", "水3個", "雨・水中で回復", CharmElement.WATER, 3),
    WATER_5("water_resonance_5", "海王共鳴", "水5個", "水中で攻撃・防御上昇", CharmElement.WATER, 5),
    WATER_7("water_resonance_7", "大海共鳴", "水7個", "雨天時に大幅強化", CharmElement.WATER, 7),
    WIND_2("wind_resonance_2", "風の共鳴", "風2個", "移動速度上昇", CharmElement.WIND, 2),
    WIND_3("wind_resonance_3", "疾風共鳴", "風3個", "回避率上昇", CharmElement.WIND, 3),
    WIND_5("wind_resonance_5", "暴風共鳴", "風5個", "ノックバック強化", CharmElement.WIND, 5),
    WIND_7("wind_resonance_7", "天空共鳴", "風7個", "落下無効・空中制御強化", CharmElement.WIND, 7),
    EARTH_2("earth_resonance_2", "地の共鳴", "地2個", "被ダメージ軽減", CharmElement.EARTH, 2),
    EARTH_3("earth_resonance_3", "岩壁共鳴", "地3個", "ノックバック軽減", CharmElement.EARTH, 3),
    EARTH_5("earth_resonance_5", "地脈共鳴", "地5個", "採掘速度・防御上昇", CharmElement.EARTH, 5),
    EARTH_7("earth_resonance_7", "大地共鳴", "地7個", "立ち止まるほど防御上昇", CharmElement.EARTH, 7),
    LIGHT_2("light_resonance_2", "光の共鳴", "光2個", "暗視・悪効果時間短縮", CharmElement.LIGHT, 2),
    LIGHT_3("light_resonance_3", "聖光共鳴", "光3個", "アンデッド特効", CharmElement.LIGHT, 3),
    LIGHT_5("light_resonance_5", "聖域共鳴", "光5個", "周囲の味方を回復", CharmElement.LIGHT, 5),
    LIGHT_7("light_resonance_7", "天啓共鳴", "光7個", "致命傷を一度だけ防ぐ", CharmElement.LIGHT, 7),
    DARK_2("dark_resonance_2", "闇の共鳴", "闇2個", "夜間攻撃上昇", CharmElement.DARK, 2),
    DARK_3("dark_resonance_3", "影共鳴", "闇3個", "与ダメージの一部を回復", CharmElement.DARK, 3),
    DARK_5("dark_resonance_5", "冥府共鳴", "闇5個", "周囲の敵を弱体化", CharmElement.DARK, 5),
    DARK_7("dark_resonance_7", "黒月共鳴", "闇7個", "夜は超強化、昼は弱体化", CharmElement.DARK, 7),
    NATURE_2("nature_resonance_2", "自然の共鳴", "自然2個", "自然回復強化", CharmElement.NATURE, 2),
    NATURE_3("nature_resonance_3", "森林共鳴", "自然3個", "作物・木材ボーナス", CharmElement.NATURE, 3),
    NATURE_5("nature_resonance_5", "世界樹共鳴", "自然5個", "周囲の味方を回復", CharmElement.NATURE, 5),
    NATURE_7("nature_resonance_7", "原初共鳴", "自然7個", "回復・防御大幅上昇", CharmElement.NATURE, 7),
    THUNDER_2("thunder_resonance_2", "雷の共鳴", "雷2個", "攻撃速度上昇", CharmElement.THUNDER, 2),
    THUNDER_3("thunder_resonance_3", "紫電共鳴", "雷3個", "低確率で落雷", CharmElement.THUNDER, 3),
    THUNDER_5("thunder_resonance_5", "雷帝共鳴", "雷5個", "連続攻撃で攻撃上昇", CharmElement.THUNDER, 5),
    THUNDER_7("thunder_resonance_7", "神鳴共鳴", "雷7個", "攻撃速度・移動速度大幅上昇", CharmElement.THUNDER, 7),
    VOID_2("void_resonance_2", "虚無の共鳴", "虚無2個", "エンダー系特効", CharmElement.VOID, 2),
    VOID_3("void_resonance_3", "深淵共鳴", "虚無3個", "低確率でダメージ無効", CharmElement.VOID, 3),
    VOID_5("void_resonance_5", "奈落共鳴", "虚無5個", "体力が低いほど攻撃上昇", CharmElement.VOID, 5),
    VOID_7("void_resonance_7", "無限共鳴", "虚無7個", "短距離転移・防御貫通", CharmElement.VOID, 7),

    STEAM("steam_resonance", "蒸気共鳴", "火 + 水", "攻撃時に視界妨害・鈍足", null, 0),
    MAGMA("magma_resonance", "溶岩共鳴", "火 + 地", "炎上中の敵へのダメージ上昇、防御上昇", null, 0),
    LIGHTNING_STORM("lightning_storm", "雷嵐共鳴", "風 + 雷", "移動速度・攻撃速度上昇", null, 0),
    HOLY_THUNDER("holy_thunder", "聖雷共鳴", "光 + 雷", "アンデッドに雷追加ダメージ", null, 0),
    FROZEN_SEA("frozen_sea", "凍海共鳴", "氷 + 水", "水中・雨天時に鈍足付与強化", null, 0),
    FOREST_GUARDIAN("forest_guardian", "森守共鳴", "自然 + 地", "回復・防御上昇", null, 0),
    SHADOW_FLAME("shadow_flame", "黒炎共鳴", "闇 + 火", "炎上＋弱体化付与", null, 0),
    ECLIPSE("eclipse", "日蝕共鳴", "光 + 闇", "攻撃・防御上昇、回復低下", null, 0),
    ABYSSAL_NATURE("abyssal_nature", "腐森共鳴", "自然 + 虚無", "毒・弱体化付与、回復低下", null, 0),
    VOID_ICE("void_ice", "虚氷共鳴", "氷 + 虚無", "鈍足中の敵へ防御貫通", null, 0),
    STORM_OCEAN("storm_ocean", "嵐海共鳴", "水 + 風", "雨天時に大幅強化", null, 0),
    THUNDER_EARTH("thunder_earth", "雷岩共鳴", "雷 + 地", "攻撃時にスタン風の鈍足", null, 0),
    CELESTIAL_TREE("celestial_tree", "天樹共鳴", "光 + 自然", "周囲の味方を回復", null, 0),
    NETHER_SUN("nether_sun", "獄陽共鳴", "火 + 闇 + 光", "高火力、被ダメージ増加", null, 0),
    PRIMORDIAL("primordial", "原初共鳴", "火 + 水 + 風 + 地", "全基礎能力上昇", null, 0),
    CHAOS_GATE("chaos_gate", "混沌門", "光 + 闇 + 虚無", "ランダム強化・ランダム弱体化", null, 0),

    COMMON_SET("common_set", "原石共鳴", "Common 3個以上", "基礎ステータス少し上昇", null, 0),
    RARE_SET("rare_set", "希少共鳴", "Rare以上 3個以上", "攻撃・防御上昇", null, 0),
    EPIC_SET("epic_set", "英雄共鳴", "Epic以上 3個以上", "特殊効果発動率上昇", null, 0),
    LEGENDARY_SET("legendary_set", "伝説共鳴", "Legendary以上 2個以上", "強力な一時バフ", null, 0),
    MYTHIC_SET("mythic_set", "神話共鳴", "Mythic以上 2個以上", "攻撃・移動・防御上昇", null, 0),
    UNIQUE_SET("unique_set", "唯一共鳴", "Unique 2個以上", "固有能力強化", null, 0),
    CURSE_SET("curse_set", "呪具共鳴", "Curse 2個以上", "呪いプラス効果強化、マイナス効果も強化", null, 0),
    FULL_CURSE("full_curse", "大呪具共鳴", "Curse 5個以上", "超火力、常時大きな代償", null, 0);

    private final String id;
    private final String displayName;
    private final String condition;
    private final String description;
    private final CharmElement element;
    private final int sameElementCount;

    CharmResonanceType(String id, String displayName, String condition, String description, CharmElement element, int sameElementCount) {
        this.id = id;
        this.displayName = displayName;
        this.condition = condition;
        this.description = description;
        this.element = element;
        this.sameElementCount = sameElementCount;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String condition() { return condition; }
    public String description() { return description; }
    public CharmElement element() { return element; }
    public int sameElementCount() { return sameElementCount; }

    public static List<CharmResonanceType> sameElement(CharmElement element) {
        return Arrays.stream(values()).filter(type -> type.element == element).toList();
    }
}

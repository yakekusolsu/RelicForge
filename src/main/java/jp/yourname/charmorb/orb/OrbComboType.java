package jp.yourname.charmorb.orb;

public enum OrbComboType {
    FLAME_BLADE("flame_blade", "火炎刃", "fire_attack + sword_edge", "攻撃時に炎上＋追加ダメージ", OrbTargetType.SWORD),
    FROST_BLADE("frost_blade", "氷刃", "ice_attack + sword_edge", "攻撃時に鈍足＋追加ダメージ", OrbTargetType.SWORD),
    THUNDER_AXE("thunder_axe", "雷斧", "thunder_attack + axe_cleave", "斧攻撃時に落雷エフェクト", OrbTargetType.AXE),
    QUAKE_MACE("quake_mace", "震砕槌", "mace_damage_up + earth_attack", "メイス攻撃時に範囲衝撃", OrbTargetType.MACE),
    BLACK_FLAME("black_flame", "黒炎", "fire_attack + dark_attack", "炎上＋弱体化", OrbTargetType.WEAPON),
    HOLY_FLAME("holy_flame", "聖炎", "fire_attack + holy_attack", "炎上＋アンデッド特効", OrbTargetType.WEAPON),
    FROZEN_SEA("frozen_sea", "凍海", "ice_attack + water_attack", "鈍足強化、水中・雨で追加効果", OrbTargetType.WEAPON),
    STORM_ARROW("storm_arrow", "嵐矢", "wind_attack + ranged_damage_up", "矢にノックバック追加", OrbTargetType.BOW),
    VENOM_BLADE("venom_blade", "毒刃", "poison_attack + crit_rate_up", "会心時に毒付与", OrbTargetType.WEAPON),
    SOUL_DRAIN("soul_drain", "魂吸い", "life_steal + kill_heal", "攻撃・撃破時の回復強化", OrbTargetType.WEAPON),
    FORTRESS_GUARD("fortress_guard", "要塞防護", "defense_up + knockback_resist", "被ダメージ軽減＋ノックバック軽減", OrbTargetType.ARMOR),
    SKY_STEP("sky_step", "空駆け", "fall_cancel + sprint_speed", "落下無効＋空中速度上昇", OrbTargetType.BOOTS),
    MINER_LUCK("miner_luck", "幸運採掘", "ore_bonus + luck_up", "鉱石追加ドロップ強化", OrbTargetType.PICKAXE),
    ETERNAL_TOOL("eternal_tool", "永久工具", "durability_save + infinity_durability", "耐久消費を大きく軽減", OrbTargetType.TOOL),
    FOREST_HARVEST("forest_harvest", "森の恵み", "woodcutting_speed + crop_bonus", "木材・作物ボーナス", OrbTargetType.TOOL),
    DEEP_LIGHT("deep_light", "深海灯", "water_breathing + night_vision", "水中探索特化", OrbTargetType.HELMET),
    INFERNO_COMBO("inferno_combo", "獄炎連鎖", "fire_attack + burn_boost + crit_damage_up", "炎上中の敵に高火力", OrbTargetType.WEAPON),
    ABSOLUTE_ZERO("absolute_zero", "絶対零度", "ice_attack + execute_damage", "鈍足中の敵に処刑ダメージ", OrbTargetType.WEAPON),
    THUNDERSTORM("thunderstorm", "雷嵐", "thunder_attack + attack_speed_up", "攻撃速度＋落雷＋ノックバック", OrbTargetType.WEAPON),
    GRAVITY_BREAKER("gravity_breaker", "重力粉砕", "mace_damage_up + knockback_up", "メイス範囲衝撃", OrbTargetType.MACE),
    DRAGON_HUNTER("dragon_hunter", "竜狩り", "dragon_damage_up + boss_damage_up + crit_damage_up", "ドラゴン・ボス特効", OrbTargetType.WEAPON),
    IMMORTAL_GUARD("immortal_guard", "不滅守護", "revive_once + defense_up + emergency_barrier", "致命傷耐性＋バリア", OrbTargetType.ARMOR),
    CREATOR_TOOL("creator_tool", "創造具", "builder_speed + durability_save + mining_speed", "建築・採掘特化", OrbTargetType.TOOL),
    TREASURE_MINER("treasure_miner", "財宝掘り", "ore_bonus + treasure_find + exp_mining", "採掘報酬強化", OrbTargetType.PICKAXE),
    BLADE_OF_INFERNO("blade_of_inferno", "業火剣式", "火属性系5個", "攻撃時に炎上・範囲炎・高火力", OrbTargetType.SWORD),
    HAMMER_OF_QUAKE("hammer_of_quake", "地震槌式", "メイス/地属性系5個", "メイス攻撃で大範囲衝撃", OrbTargetType.MACE),
    ARMOR_OF_WORLD_TREE("armor_of_world_tree", "世界樹装甲", "自然/回復/防御系5個", "周囲回復＋自己再生", OrbTargetType.ARMOR),
    BOOTS_OF_HEAVEN("boots_of_heaven", "天穹脚式", "風/空/速度系5個", "落下無効・高速移動・ジャンプ強化", OrbTargetType.BOOTS),
    PICKAXE_OF_CREATION("pickaxe_of_creation", "創造採掘式", "採掘/耐久/幸運系5個", "採掘速度・追加ドロップ大幅強化", OrbTargetType.PICKAXE);

    private final String id;
    private final String displayName;
    private final String condition;
    private final String description;
    private final OrbTargetType target;

    OrbComboType(String id, String displayName, String condition, String description, OrbTargetType target) {
        this.id = id;
        this.displayName = displayName;
        this.condition = condition;
        this.description = description;
        this.target = target;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String condition() { return condition; }
    public String description() { return description; }
    public OrbTargetType target() { return target; }
}

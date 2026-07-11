package jp.yourname.charmorb.orb;

public enum OrbPositiveEffectType {
    DAMAGE_UP("damage_up", "攻撃強化", "与ダメージ上昇", OrbTargetType.WEAPON),
    MELEE_DAMAGE_UP("melee_damage_up", "近接強化", "近接ダメージ上昇", OrbTargetType.MELEE_WEAPON),
    RANGED_DAMAGE_UP("ranged_damage_up", "射撃強化", "遠距離ダメージ上昇", OrbTargetType.RANGED_WEAPON),
    MACE_DAMAGE_UP("mace_damage_up", "砕撃強化", "メイスダメージ上昇", OrbTargetType.MACE),
    AXE_CLEAVE("axe_cleave", "斧裂き", "斧攻撃時に追加ダメージ", OrbTargetType.AXE),
    SWORD_EDGE("sword_edge", "剣閃", "剣攻撃時に追加ダメージ", OrbTargetType.SWORD),
    CRIT_DAMAGE_UP("crit_damage_up", "会心強化", "クリティカルダメージ上昇", OrbTargetType.WEAPON),
    CRIT_RATE_UP("crit_rate_up", "会心率上昇", "低確率で追加ダメージ", OrbTargetType.WEAPON),
    ARMOR_PIERCE("armor_pierce", "防御貫通", "敵の防御を一部無視", OrbTargetType.WEAPON),
    EXECUTE_DAMAGE("execute_damage", "処刑", "体力が低い敵に追加ダメージ", OrbTargetType.WEAPON),
    BOSS_DAMAGE_UP("boss_damage_up", "王殺し", "ボスへのダメージ上昇", OrbTargetType.WEAPON),
    MOB_DAMAGE_UP("mob_damage_up", "魔物狩り", "通常の敵へのダメージ上昇", OrbTargetType.WEAPON),
    ENDER_DAMAGE_UP("ender_damage_up", "虚空特効", "エンダー系の敵に特効", OrbTargetType.WEAPON),
    UNDEAD_DAMAGE_UP("undead_damage_up", "聖光特効", "アンデッドに特効", OrbTargetType.WEAPON),
    DRAGON_DAMAGE_UP("dragon_damage_up", "竜殺し", "ドラゴン系に大ダメージ", OrbTargetType.WEAPON),
    KNOCKBACK_UP("knockback_up", "吹き飛ばし", "ノックバック上昇", OrbTargetType.WEAPON),
    ATTACK_SPEED_UP("attack_speed_up", "攻撃速度上昇", "攻撃速度上昇風の効果", OrbTargetType.WEAPON),
    COMBO_DAMAGE("combo_damage", "連撃", "連続攻撃で一時的に攻撃上昇", OrbTargetType.WEAPON),
    LOW_HP_DAMAGE("low_hp_damage", "奈落力", "自分の体力が低いほど攻撃上昇", OrbTargetType.WEAPON),
    FIRST_HIT("first_hit", "初撃", "戦闘開始時の一撃が強化", OrbTargetType.WEAPON),

    FIRE_ATTACK("fire_attack", "炎上撃", "攻撃時に炎上付与", OrbTargetType.WEAPON),
    ICE_ATTACK("ice_attack", "氷結撃", "攻撃時に鈍足付与", OrbTargetType.WEAPON),
    POISON_ATTACK("poison_attack", "毒撃", "攻撃時に毒付与", OrbTargetType.WEAPON),
    WITHER_ATTACK("wither_attack", "衰滅撃", "攻撃時にウィザー付与", OrbTargetType.WEAPON),
    THUNDER_ATTACK("thunder_attack", "雷撃", "低確率で落雷エフェクト", OrbTargetType.WEAPON),
    DARK_ATTACK("dark_attack", "闇撃", "攻撃時に弱体化付与", OrbTargetType.WEAPON),
    HOLY_ATTACK("holy_attack", "聖撃", "アンデッドに追加ダメージ", OrbTargetType.WEAPON),
    VOID_ATTACK("void_attack", "虚無撃", "エンダー系に追加ダメージ", OrbTargetType.WEAPON),
    WATER_ATTACK("water_attack", "水撃", "水中・雨天時に追加ダメージ", OrbTargetType.WEAPON),
    WIND_ATTACK("wind_attack", "風撃", "攻撃時にノックバック追加", OrbTargetType.WEAPON),
    EARTH_ATTACK("earth_attack", "地撃", "低確率で範囲小ダメージ", OrbTargetType.WEAPON),
    NATURE_ATTACK("nature_attack", "森撃", "攻撃時に鈍足・毒の低確率付与", OrbTargetType.WEAPON),

    DEFENSE_UP("defense_up", "防御強化", "被ダメージ軽減", OrbTargetType.ARMOR),
    PROJECTILE_GUARD("projectile_guard", "矢避け", "飛び道具ダメージ軽減", OrbTargetType.ARMOR),
    EXPLOSION_GUARD("explosion_guard", "爆風耐性", "爆発ダメージ軽減", OrbTargetType.ARMOR),
    FIRE_GUARD("fire_guard", "火耐性", "炎・溶岩ダメージ軽減", OrbTargetType.ARMOR),
    FALL_GUARD("fall_guard", "転落耐性", "落下ダメージ軽減", OrbTargetType.BOOTS),
    MAGIC_GUARD("magic_guard", "魔除け", "魔法・ポーション系ダメージ軽減", OrbTargetType.ARMOR),
    KNOCKBACK_RESIST("knockback_resist", "不動", "ノックバック軽減", OrbTargetType.ARMOR),
    SHIELD_CHANCE("shield_chance", "守護膜", "一定確率でダメージ軽減", OrbTargetType.ARMOR),
    THORNS_DAMAGE("thorns_damage", "反射", "被ダメージ時に反射", OrbTargetType.ARMOR),
    EMERGENCY_BARRIER("emergency_barrier", "緊急結界", "体力低下時に一時バリア", OrbTargetType.ARMOR),
    LAST_STAND_DEFENSE("last_stand_defense", "最後の抵抗", "体力低下時に防御上昇", OrbTargetType.ARMOR),
    BOSS_GUARD("boss_guard", "王盾", "ボスからのダメージ軽減", OrbTargetType.ARMOR),
    MOB_GUARD("mob_guard", "魔物防護", "敵からのダメージ軽減", OrbTargetType.ARMOR),
    ENDER_GUARD("ender_guard", "虚空防護", "エンダー系からのダメージ軽減", OrbTargetType.ARMOR),
    UNDEAD_GUARD("undead_guard", "不死防護", "アンデッドからのダメージ軽減", OrbTargetType.ARMOR),

    MAX_HEALTH_UP("max_health_up", "活力", "最大体力上昇", OrbTargetType.ARMOR),
    GIANT_HEALTH("giant_health", "巨人体", "最大体力大幅上昇", OrbTargetType.ARMOR),
    REGEN_OUT_COMBAT("regen_out_combat", "再生", "戦闘外で体力回復", OrbTargetType.ARMOR),
    HEAL_BOOST("heal_boost", "治癒強化", "回復効果上昇", OrbTargetType.ARMOR),
    LIFE_STEAL("life_steal", "吸命", "与ダメージの一部を回復", OrbTargetType.WEAPON),
    KILL_HEAL("kill_heal", "吸魂", "敵撃破時に回復", OrbTargetType.WEAPON),
    EMERGENCY_HEAL("emergency_heal", "緊急治癒", "体力低下時に自動回復", OrbTargetType.ARMOR),
    FOOD_HEAL("food_heal", "飽食回復", "満腹時に回復強化", OrbTargetType.ARMOR),
    POISON_CLEANSE("poison_cleanse", "解毒", "毒・ウィザーを解除", OrbTargetType.ARMOR),
    FIRE_CLEANSE("fire_cleanse", "消火", "炎上時間短縮", OrbTargetType.ARMOR),
    REVIVE_ONCE("revive_once", "蘇生", "致命傷を一度だけ耐える", OrbTargetType.ARMOR),
    PHOENIX_REBIRTH("phoenix_rebirth", "不死鳥", "死亡相当ダメージ時に復活", OrbTargetType.ARMOR),

    SPEED_UP("speed_up", "軽量", "移動速度上昇", OrbTargetType.BOOTS),
    SPRINT_SPEED("sprint_speed", "疾風", "ダッシュ時速度上昇", OrbTargetType.BOOTS),
    JUMP_UP("jump_up", "跳躍", "ジャンプ力上昇", OrbTargetType.BOOTS),
    DODGE_CHANCE("dodge_chance", "回避", "一定確率で被ダメージ回避", OrbTargetType.ARMOR),
    DASH_BOOST("dash_boost", "瞬足", "回避後速度上昇", OrbTargetType.BOOTS),
    WATER_MOVE("water_move", "水泳", "水中移動速度上昇", OrbTargetType.BOOTS),
    WATER_BREATHING("water_breathing", "深海", "水中呼吸", OrbTargetType.HELMET),
    NIGHT_VISION("night_vision", "灯火", "暗所で暗視", OrbTargetType.HELMET),
    SLOW_RESIST("slow_resist", "鈍足耐性", "鈍足効果を軽減", OrbTargetType.BOOTS),
    FALL_CANCEL("fall_cancel", "天穹", "落下ダメージ無効", OrbTargetType.BOOTS),
    AIR_CONTROL("air_control", "空中制御", "空中移動補助", OrbTargetType.BOOTS),
    BLINK_SHORT("blink_short", "時空歩", "短距離ブリンク", OrbTargetType.BOOTS),

    MINING_SPEED("mining_speed", "採掘強化", "採掘速度上昇", OrbTargetType.PICKAXE),
    WOODCUTTING_SPEED("woodcutting_speed", "森人", "木材採取速度上昇", OrbTargetType.AXE),
    DIGGING_SPEED("digging_speed", "掘削強化", "土・砂・砂利採掘速度上昇", OrbTargetType.SHOVEL),
    FARMING_SPEED("farming_speed", "農作業強化", "作物収穫・耕作補助", OrbTargetType.HOE),
    ORE_SENSE("ore_sense", "探鉱", "鉱石破壊時に反応", OrbTargetType.PICKAXE),
    ORE_BONUS("ore_bonus", "鉱石祝福", "鉱石追加ドロップ", OrbTargetType.PICKAXE),
    CROP_BONUS("crop_bonus", "豊穣", "作物収穫量増加", OrbTargetType.HOE),
    DOUBLE_DROP("double_drop", "追加採取", "低確率で追加ドロップ", OrbTargetType.TOOL),
    DURABILITY_SAVE("durability_save", "耐久節約", "耐久消費軽減", OrbTargetType.UNIVERSAL),
    INFINITY_DURABILITY("infinity_durability", "無限耐久", "低確率で耐久消費なし", OrbTargetType.UNIVERSAL),
    EXP_MINING("exp_mining", "経験採掘", "採掘時経験値増加", OrbTargetType.PICKAXE),
    TREASURE_FIND("treasure_find", "宝探し", "低確率で追加アイテム", OrbTargetType.TOOL),
    BUILDER_SPEED("builder_speed", "創造補助", "ブロック設置・破壊速度上昇", OrbTargetType.TOOL),
    REPAIR_CHANCE("repair_chance", "自己修復", "低確率で耐久回復", OrbTargetType.UNIVERSAL),

    HUNGER_SAVE("hunger_save", "満腹", "空腹消費軽減", OrbTargetType.ARMOR),
    EXP_UP("exp_up", "経験吸収", "獲得経験値上昇", OrbTargetType.UNIVERSAL),
    LUCK_UP("luck_up", "幸運", "ドロップ率上昇", OrbTargetType.UNIVERSAL),
    DANGER_SENSE("danger_sense", "危機感知", "近くの敵を感知", OrbTargetType.HELMET),
    MOB_AVOIDANCE("mob_avoidance", "気配消し", "敵に狙われにくくなる", OrbTargetType.ARMOR),
    WEATHER_POWER("weather_power", "天候適応", "雨・雷雨で強化", OrbTargetType.UNIVERSAL),
    NETHER_ADAPT("nether_adapt", "ネザー適応", "ネザーで強化", OrbTargetType.ARMOR),
    END_ADAPT("end_adapt", "エンド適応", "エンドで強化", OrbTargetType.ARMOR),
    SOUL_COLLECT("soul_collect", "魂収集", "敵撃破で魂ポイント獲得", OrbTargetType.WEAPON),
    COOLDOWN_REDUCE("cooldown_reduce", "時短", "スキル待機時間短縮", OrbTargetType.UNIVERSAL);

    private final String id;
    private final String displayName;
    private final String description;
    private final OrbTargetType target;

    OrbPositiveEffectType(String id, String displayName, String description, OrbTargetType target) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.target = target;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String description() { return description; }
    public OrbTargetType target() { return target; }
}

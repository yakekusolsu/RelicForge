package jp.yourname.charmorb.charm;

import java.util.Arrays;
import java.util.List;

public enum CharmPositiveEffectType {
    ATTACK_UP("attack_up", "攻撃強化", "与ダメージが上昇", "攻撃系", null),
    CRITICAL_UP("critical_up", "会心強化", "クリティカルダメージ上昇", "攻撃系", null),
    CRITICAL_RATE("critical_rate", "会心率上昇", "低確率で追加ダメージ", "攻撃系", null),
    EXECUTE("execute", "処刑", "体力が低い敵へのダメージ上昇", "攻撃系", null),
    MOB_SLAYER("mob_slayer", "魔物狩り", "通常の敵へのダメージ上昇", "攻撃系", null),
    BOSS_SLAYER("boss_slayer", "王殺し", "ボス系へのダメージ上昇", "攻撃系", null),
    BACK_ATTACK("back_attack", "背撃", "背後から攻撃時にダメージ上昇", "攻撃系", null),
    FIRST_STRIKE("first_strike", "初撃", "戦闘開始時の最初の一撃が強化", "攻撃系", null),
    COMBO_ATTACK("combo_attack", "連撃", "連続攻撃で一時的に攻撃上昇", "攻撃系", null),
    LOW_HP_ATTACK("low_hp_attack", "窮鼠", "自分の体力が低いほど攻撃上昇", "攻撃系", null),
    ARMOR_PIERCE("armor_pierce", "防御貫通", "敵の防御を一部無視", "攻撃系", null),
    KNOCKBACK_UP("knockback_up", "吹き飛ばし", "ノックバック上昇", "攻撃系", null),
    PROJECTILE_UP("projectile_up", "射撃強化", "弓・クロスボウ・トライデントのダメージ上昇", "攻撃系", null),
    MELEE_UP("melee_up", "近接強化", "剣・斧・メイスのダメージ上昇", "攻撃系", null),
    MACE_IMPACT("mace_impact", "砕撃", "メイス攻撃時に追加ダメージ", "攻撃系", null),

    DEFENSE_UP("defense_up", "防御強化", "被ダメージ軽減", "防御系", null),
    PROJECTILE_GUARD("projectile_guard", "矢避け", "飛び道具ダメージ軽減", "防御系", null),
    EXPLOSION_GUARD("explosion_guard", "爆風耐性", "爆発ダメージ軽減", "防御系", null),
    FIRE_GUARD("fire_guard", "火耐性", "炎・溶岩ダメージ軽減", "防御系", null),
    FALL_GUARD("fall_guard", "転落耐性", "落下ダメージ軽減", "防御系", null),
    MAGIC_GUARD("magic_guard", "魔除け", "魔法・ポーション系ダメージ軽減", "防御系", null),
    THORNS_AURA("thorns_aura", "反射", "被ダメージ時に一部反射", "防御系", null),
    SHIELD_AURA("shield_aura", "守護膜", "一定確率で被ダメージ軽減", "防御系", null),
    LAST_STAND("last_stand", "最後の抵抗", "体力低下時に防御上昇", "防御系", null),
    BARRIER("barrier", "結界", "一定時間ごとに小さなシールド", "防御系", null),
    KNOCKBACK_RESIST("knockback_resist", "不動", "ノックバック軽減", "防御系", null),
    ARMOR_BOOST("armor_boost", "装甲強化", "防具装備時に防御上昇", "防御系", null),

    MAX_HEALTH_UP("max_health_up", "生命力", "最大体力上昇", "体力・回復系", null),
    REGEN_UP("regen_up", "自然回復", "自然回復速度上昇", "体力・回復系", null),
    HEAL_BOOST("heal_boost", "治癒強化", "回復効果上昇", "体力・回復系", null),
    KILL_HEAL("kill_heal", "吸魂", "敵撃破時に回復", "体力・回復系", null),
    DAMAGE_HEAL("damage_heal", "吸命", "与ダメージの一部を回復", "体力・回復系", null),
    EMERGENCY_HEAL("emergency_heal", "緊急治癒", "体力低下時に自動回復", "体力・回復系", null),
    FOOD_HEAL("food_heal", "飽食回復", "満腹度が高い時に回復強化", "体力・回復系", null),
    REVIVE_ONCE("revive_once", "蘇生", "致命傷を一度だけ耐える", "体力・回復系", null),
    BLOOD_REGEN("blood_regen", "血流再生", "戦闘中に少しずつ回復", "体力・回復系", null),
    CLEANSE("cleanse", "浄化", "一定確率で悪い効果を解除", "体力・回復系", null),
    POISON_CLEANSE("poison_cleanse", "解毒", "毒・ウィザーを解除", "体力・回復系", null),
    FIRE_CLEANSE("fire_cleanse", "消火", "炎上時間を短縮", "体力・回復系", null),

    SPEED_UP("speed_up", "俊足", "移動速度上昇", "移動系", null),
    SPRINT_BOOST("sprint_boost", "疾走", "ダッシュ時に速度上昇", "移動系", null),
    JUMP_UP("jump_up", "跳躍", "ジャンプ力上昇", "移動系", null),
    DODGE("dodge", "回避", "一定確率でダメージ回避", "移動系", null),
    DASH("dash", "瞬歩", "一定間隔で短距離加速", "移動系", null),
    WATER_MOVE("water_move", "水流歩行", "水中移動速度上昇", "移動系", null),
    AIR_CONTROL("air_control", "空中制御", "空中移動を補助", "移動系", null),
    SLOW_RESIST("slow_resist", "鈍足耐性", "鈍足効果を軽減", "移動系", null),
    ESCAPE_BOOST("escape_boost", "逃走本能", "体力低下時に速度上昇", "移動系", null),
    FEATHER_STEP("feather_step", "羽歩き", "落下ダメージ軽減", "移動系", null),
    STEP_ASSIST("step_assist", "段差越え", "段差移動を補助", "移動系", null),
    BLINK_SHORT("blink_short", "短距離転移", "短距離ブリンク", "移動系", null),

    MINING_SPEED("mining_speed", "採掘強化", "採掘速度上昇", "採掘・作業系", null),
    WOODCUTTING("woodcutting", "伐採強化", "木材採取速度上昇", "採掘・作業系", null),
    EXCAVATION("excavation", "掘削強化", "土・砂・砂利系の採掘速度上昇", "採掘・作業系", null),
    ORE_SENSE("ore_sense", "鉱脈感知", "鉱石破壊時に周囲を発光", "採掘・作業系", null),
    DOUBLE_DROP("double_drop", "追加採取", "一定確率で追加ドロップ", "採掘・作業系", null),
    DURABILITY_SAVE("durability_save", "耐久節約", "道具の耐久消費を軽減", "採掘・作業系", null),
    CRAFT_FOCUS("craft_focus", "職人集中", "作業後に短時間強化", "採掘・作業系", null),
    HARVEST_BOOST("harvest_boost", "収穫強化", "作物収穫量上昇", "採掘・作業系", null),
    BUILDER("builder", "建築補助", "建築時の移動・設置補助", "採掘・作業系", null),
    TREASURE_FIND("treasure_find", "宝探し", "低確率で追加アイテム", "採掘・作業系", null),
    EXP_MINING("exp_mining", "経験採掘", "採掘時の経験値増加", "採掘・作業系", null),
    VEIN_HINT("vein_hint", "鉱脈反応", "近くの鉱石に反応", "採掘・作業系", null),

    NIGHT_VISION("night_vision", "夜目", "暗所で暗視", "生存・探索系", null),
    WATER_BREATHING("water_breathing", "水中呼吸", "水中呼吸時間延長", "生存・探索系", null),
    FIRE_RESIST("fire_resist", "火炎耐性", "炎上・溶岩に強くなる", "生存・探索系", null),
    POISON_RESIST("poison_resist", "毒耐性", "毒ダメージ軽減", "生存・探索系", null),
    HUNGER_SAVE("hunger_save", "省エネ", "空腹消費軽減", "生存・探索系", null),
    LUCK_UP("luck_up", "幸運", "ドロップ・報酬運上昇", "生存・探索系", null),
    EXP_UP("exp_up", "経験吸収", "獲得経験値上昇", "生存・探索系", null),
    SOUL_COLLECT("soul_collect", "魂収集", "敵撃破で魂ポイント獲得", "生存・探索系", null),
    DANGER_SENSE("danger_sense", "危機感知", "近くの敵を感知", "生存・探索系", null),
    MOB_AVOIDANCE("mob_avoidance", "気配消し", "敵に狙われにくくなる", "生存・探索系", null),
    WEATHER_ADAPT("weather_adapt", "天候適応", "雨・雷雨で能力上昇", "生存・探索系", null),
    DIMENSION_ADAPT("dimension_adapt", "次元適応", "ネザー・エンドで能力上昇", "生存・探索系", null),

    FIRE_ATTACK("fire_attack", "火炎撃", "攻撃時に炎上付与", "属性効果", CharmElement.FIRE),
    FLAME_AURA("flame_aura", "炎の衣", "周囲の敵に小炎ダメージ", "属性効果", CharmElement.FIRE),
    BURN_BOOST("burn_boost", "焼尽", "炎上中の敵へのダメージ上昇", "属性効果", CharmElement.FIRE),
    INFERNO_BURST("inferno_burst", "業火爆発", "低確率で範囲炎ダメージ", "属性効果", CharmElement.FIRE),
    LAVA_WALKER("lava_walker", "溶岩適応", "溶岩付近で強化", "属性効果", CharmElement.FIRE),
    EMBER_HEAL("ember_heal", "火種再生", "炎上中に少し回復", "属性効果", CharmElement.FIRE),
    BLAZE_SLAYER("blaze_slayer", "焔狩り", "ネザーの敵に特効", "属性効果", CharmElement.FIRE),

    ICE_ATTACK("ice_attack", "氷結撃", "攻撃時に鈍足付与", "属性効果", CharmElement.ICE),
    FREEZE_AURA("freeze_aura", "凍気", "周囲の敵を鈍足化", "属性効果", CharmElement.ICE),
    FROZEN_GUARD("frozen_guard", "氷盾", "被ダメージ時に一時防御上昇", "属性効果", CharmElement.ICE),
    SHATTER("shatter", "粉砕氷", "鈍足中の敵へのダメージ上昇", "属性効果", CharmElement.ICE),
    CHILL_STEP("chill_step", "冷歩", "氷上・水上で移動補助", "属性効果", CharmElement.ICE),
    FROST_RESIST("frost_resist", "凍結耐性", "凍結・鈍足を軽減", "属性効果", CharmElement.ICE),
    ICE_SPIKE("ice_spike", "氷槍", "低確率で追加ダメージ", "属性効果", CharmElement.ICE),
    SNOW_HIDE("snow_hide", "雪隠れ", "雪原で敵に狙われにくい", "属性効果", CharmElement.ICE),

    AQUA_SPEED("aqua_speed", "水流加速", "水中移動速度上昇", "属性効果", CharmElement.WATER),
    HEALING_WATER("healing_water", "癒しの水", "水中・雨天で回復", "属性効果", CharmElement.WATER),
    WAVE_GUARD("wave_guard", "波盾", "被ダメージ軽減", "属性効果", CharmElement.WATER),
    DROWN_RESIST("drown_resist", "溺れ耐性", "溺れダメージ軽減", "属性効果", CharmElement.WATER),
    RAIN_POWER("rain_power", "雨力", "雨天時に能力上昇", "属性効果", CharmElement.WATER),
    TIDE_PUSH("tide_push", "潮押し", "攻撃時にノックバック", "属性効果", CharmElement.WATER),
    WATER_CLEANSE("water_cleanse", "清流浄化", "悪い効果を解除", "属性効果", CharmElement.WATER),
    GUARDIAN_SLAYER("guardian_slayer", "海獣狩り", "水中の敵に特効", "属性効果", CharmElement.WATER),

    WIND_SPEED("wind_speed", "風脚", "移動速度上昇", "属性効果", CharmElement.WIND),
    DODGE_WIND("dodge_wind", "風避け", "回避率上昇", "属性効果", CharmElement.WIND),
    AIR_JUMP("air_jump", "空跳", "ジャンプ力上昇", "属性効果", CharmElement.WIND),
    GUST_KNOCKBACK("gust_knockback", "突風", "ノックバック強化", "属性効果", CharmElement.WIND),
    FEATHER_FALL("feather_fall", "羽落ち", "落下ダメージ軽減", "属性効果", CharmElement.WIND),
    GLIDE_CONTROL("glide_control", "滑空制御", "空中移動を補助", "属性効果", CharmElement.WIND),
    STORM_DASH("storm_dash", "嵐走", "ダッシュ時に一時強化", "属性効果", CharmElement.WIND),
    ARROW_WIND("arrow_wind", "風矢", "射撃速度・射撃威力上昇", "属性効果", CharmElement.WIND),

    EARTH_GUARD("earth_guard", "岩肌", "被ダメージ軽減", "属性効果", CharmElement.EARTH),
    HEAVY_BODY("heavy_body", "重体", "ノックバック軽減", "属性効果", CharmElement.EARTH),
    VEIN_MINING("vein_mining", "地脈掘り", "採掘速度上昇", "属性効果", CharmElement.EARTH),
    ROOT_REGEN("root_regen", "根の再生", "地上で回復", "属性効果", CharmElement.EARTH),
    STONE_SKIN("stone_skin", "石膚", "一定確率で大きく軽減", "属性効果", CharmElement.EARTH),
    QUAKE_HIT("quake_hit", "地鳴り", "攻撃時に周囲へ小ダメージ", "属性効果", CharmElement.EARTH),
    ORE_BLESSING("ore_blessing", "鉱石祝福", "鉱石系ドロップ強化", "属性効果", CharmElement.EARTH),
    FORTRESS_BODY("fortress_body", "要塞体", "立ち止まるほど防御上昇", "属性効果", CharmElement.EARTH),

    HOLY_ATTACK("holy_attack", "聖撃", "アンデッド特効", "属性効果", CharmElement.LIGHT),
    CLEANSE_LIGHT("cleanse_light", "浄化", "悪い効果を解除", "属性効果", CharmElement.LIGHT),
    LIGHT_HEAL("light_heal", "光癒", "回復力上昇", "属性効果", CharmElement.LIGHT),
    BLIND_RESIST("blind_resist", "暗闇無効", "暗闇・盲目無効", "属性効果", CharmElement.LIGHT),
    DIVINE_BARRIER("divine_barrier", "聖壁", "致命傷を軽減", "属性効果", CharmElement.LIGHT),
    RADIANT_AURA("radiant_aura", "光輪", "周囲の味方を少し回復", "属性効果", CharmElement.LIGHT),
    JUDGEMENT("judgement", "裁き", "闇・アンデッドに追加ダメージ", "属性効果", CharmElement.LIGHT),
    SUNRISE_POWER("sunrise_power", "旭光", "朝・昼に能力上昇", "属性効果", CharmElement.LIGHT),

    DARK_ATTACK("dark_attack", "闇撃", "攻撃時に弱体化付与", "属性効果", CharmElement.DARK),
    SHADOW_LIFE_STEAL("shadow_life_steal", "影吸い", "与ダメージの一部を回復", "属性効果", CharmElement.DARK),
    NIGHT_POWER("night_power", "夜力", "夜間に能力上昇", "属性効果", CharmElement.DARK),
    SHADOW_STEP("shadow_step", "影歩き", "暗所で速度上昇", "属性効果", CharmElement.DARK),
    CURSE_BLADE("curse_blade", "呪刃", "ダメージ上昇と軽い代償", "属性効果", CharmElement.DARK),
    FEAR_AURA("fear_aura", "恐怖", "周囲の敵の攻撃力低下", "属性効果", CharmElement.DARK),
    BLIND_HIT("blind_hit", "目潰し", "低確率で盲目付与", "属性効果", CharmElement.DARK),
    MOON_REGEN("moon_regen", "月下再生", "夜に回復", "属性効果", CharmElement.DARK),

    NATURE_REGEN("nature_regen", "自然再生", "草地・森で回復", "属性効果", CharmElement.NATURE),
    HARVEST_BLESSING("harvest_blessing", "豊穣", "作物収穫量上昇", "属性効果", CharmElement.NATURE),
    BEAST_GUARD("beast_guard", "獣親和", "動物・獣系の敵からの被ダメ軽減", "属性効果", CharmElement.NATURE),
    ROOT_BIND("root_bind", "根縛り", "攻撃時に鈍足付与", "属性効果", CharmElement.NATURE),
    FOREST_SPEED("forest_speed", "森駆け", "草・葉・木付近で速度上昇", "属性効果", CharmElement.NATURE),
    SEED_HEAL("seed_heal", "種癒", "食事時に追加回復", "属性効果", CharmElement.NATURE),
    THORN_BODY("thorn_body", "棘身", "被ダメージ時に反射", "属性効果", CharmElement.NATURE),

    THUNDER_ATTACK("thunder_attack", "雷撃", "低確率で落雷エフェクト", "属性効果", CharmElement.THUNDER),
    ATTACK_SPEED("attack_speed", "迅雷", "攻撃速度上昇", "属性効果", CharmElement.THUNDER),
    SHOCK("shock", "感電", "攻撃時に短時間弱体化", "属性効果", CharmElement.THUNDER),
    CHARGE("charge", "帯電", "連続攻撃で攻撃上昇", "属性効果", CharmElement.THUNDER),
    STORM_CALL("storm_call", "雷雲", "雨天時に能力上昇", "属性効果", CharmElement.THUNDER),
    STATIC_GUARD("static_guard", "静電防壁", "被ダメージ時に反撃", "属性効果", CharmElement.THUNDER),
    LIGHTNING_STEP("lightning_step", "雷歩", "短距離加速", "属性効果", CharmElement.THUNDER),
    OVERCHARGE("overcharge", "過充電", "低確率で高火力追加攻撃", "属性効果", CharmElement.THUNDER),

    VOID_ATTACK("void_attack", "虚無撃", "エンダー系特効", "属性効果", CharmElement.VOID),
    BLINK("blink", "瞬間移動", "短距離ブリンク", "属性効果", CharmElement.VOID),
    VOID_GUARD("void_guard", "虚無防壁", "一定確率でダメージ無効", "属性効果", CharmElement.VOID),
    ABYSS_POWER("abyss_power", "奈落力", "体力が低いほど攻撃上昇", "属性効果", CharmElement.VOID),
    ENDER_RESIST("ender_resist", "エンダー耐性", "エンダー系の敵からの被ダメ軽減", "属性効果", CharmElement.VOID),
    GRAVITY_CUT("gravity_cut", "重力断ち", "落下ダメージ軽減", "属性効果", CharmElement.VOID),
    SPACE_RIP("space_rip", "空間裂き", "低確率で防御貫通", "属性効果", CharmElement.VOID),
    VOID_SILENCE("void_silence", "虚無沈黙", "敵の特殊行動を弱体化", "属性効果", CharmElement.VOID);

    private final String id;
    private final String displayName;
    private final String description;
    private final String category;
    private final CharmElement element;

    CharmPositiveEffectType(String id, String displayName, String description, String category, CharmElement element) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.category = category;
        this.element = element;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String description() { return description; }
    public String category() { return category; }
    public CharmElement element() { return element; }

    public static List<CharmPositiveEffectType> global() {
        return Arrays.stream(values()).filter(effect -> effect.element == null).toList();
    }

    public static List<CharmPositiveEffectType> elemental(CharmElement element) {
        return Arrays.stream(values()).filter(effect -> effect.element == element).toList();
    }
}

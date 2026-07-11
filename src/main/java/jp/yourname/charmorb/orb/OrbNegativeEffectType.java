package jp.yourname.charmorb.orb;

public enum OrbNegativeEffectType {
    DAMAGE_DOWN("damage_down", "攻撃低下", "与ダメージ低下", OrbTargetType.WEAPON),
    DEFENSE_DOWN("defense_down", "防御低下", "被ダメージ増加", OrbTargetType.ARMOR),
    SPEED_DOWN("speed_down", "鈍重", "移動速度低下", OrbTargetType.ARMOR),
    MINING_DOWN("mining_down", "採掘低下", "採掘速度低下", OrbTargetType.TOOL),
    MAX_HEALTH_DOWN("max_health_down", "生命減少", "最大体力低下", OrbTargetType.ARMOR),
    HUNGER_UP("hunger_up", "空腹増加", "空腹消費増加", OrbTargetType.ARMOR),
    DURABILITY_LOSS("durability_loss", "耐久浪費", "耐久消費増加", OrbTargetType.UNIVERSAL),
    HEAL_DOWN("heal_down", "回復低下", "回復効果低下", OrbTargetType.ARMOR),
    LUCK_DOWN("luck_down", "不運", "ドロップ率低下", OrbTargetType.UNIVERSAL),
    EXP_DOWN("exp_down", "経験鈍化", "獲得経験値低下", OrbTargetType.UNIVERSAL),
    FIRE_WEAKNESS("fire_weakness", "火弱点", "炎・溶岩ダメージ増加", OrbTargetType.ARMOR),
    WATER_WEAKNESS("water_weakness", "水弱点", "水中で弱体化", OrbTargetType.ARMOR),
    UNDEAD_WEAKNESS("undead_weakness", "不浄", "アンデッドからのダメージ増加", OrbTargetType.ARMOR),
    ENDER_WEAKNESS("ender_weakness", "虚弱界", "エンダー系からのダメージ増加", OrbTargetType.ARMOR),
    DAYLIGHT_WEAKNESS("daylight_weakness", "日光弱体", "昼に弱体化", OrbTargetType.UNIVERSAL),
    NIGHT_WEAKNESS("night_weakness", "夜間弱体", "夜に弱体化", OrbTargetType.UNIVERSAL),
    ARMOR_WEIGHT("armor_weight", "装備重量", "防具装備中に速度低下", OrbTargetType.ARMOR),
    FRAGILE_TOOL("fragile_tool", "脆い道具", "ツール耐久が減りやすい", OrbTargetType.TOOL),
    UNSTABLE_POWER("unstable_power", "不安定", "低確率で効果が一時停止", OrbTargetType.UNIVERSAL),
    NOISY_AURA("noisy_aura", "騒霊", "敵に狙われやすくなる", OrbTargetType.ARMOR),
    COOLDOWN_UP("cooldown_up", "鈍い魔力", "スキル待機時間増加", OrbTargetType.UNIVERSAL),
    POTION_WEAK("potion_weak", "薬効低下", "ポーション効果時間低下", OrbTargetType.ARMOR),
    KNOCKBACK_WEAK("knockback_weak", "軽身", "ノックバックを受けやすい", OrbTargetType.ARMOR),
    BLEED("bleed", "出血", "被ダメージ後に追加小ダメージ", OrbTargetType.ARMOR),
    SELF_DAMAGE_CHANCE("self_damage_chance", "反動", "攻撃時に低確率で自傷", OrbTargetType.WEAPON),
    ACCURACY_DOWN("accuracy_down", "精度低下", "遠距離攻撃が弱くなる", OrbTargetType.RANGED_WEAPON),
    HEAVY_SWING("heavy_swing", "重振り", "攻撃速度低下", OrbTargetType.MELEE_WEAPON),
    CURSED_SOCKET("cursed_socket", "呪われた穴", "外す時に低確率で消滅", OrbTargetType.UNIVERSAL);

    private final String id;
    private final String displayName;
    private final String description;
    private final OrbTargetType target;

    OrbNegativeEffectType(String id, String displayName, String description, OrbTargetType target) {
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

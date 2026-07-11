package jp.yourname.charmorb.charm;

public enum CharmNegativeEffectType {
    ATTACK_DOWN("attack_down", "攻撃低下", "与ダメージ低下"),
    DEFENSE_DOWN("defense_down", "防御低下", "被ダメージ増加"),
    SPEED_DOWN("speed_down", "鈍足", "移動速度低下"),
    MINING_DOWN("mining_down", "採掘低下", "採掘速度低下"),
    MAX_HEALTH_DOWN("max_health_down", "生命減少", "最大体力低下"),
    HUNGER_UP("hunger_up", "空腹増加", "空腹消費増加"),
    DURABILITY_LOSS("durability_loss", "耐久浪費", "耐久消費増加"),
    HEAL_DOWN("heal_down", "回復低下", "回復効果低下"),
    LUCK_DOWN("luck_down", "不運", "ドロップ率低下"),
    EXP_DOWN("exp_down", "経験鈍化", "獲得経験値低下"),
    FIRE_WEAKNESS("fire_weakness", "火弱点", "炎・溶岩ダメージ増加"),
    WATER_WEAKNESS("water_weakness", "水弱点", "水中で弱体化"),
    UNDEAD_WEAKNESS("undead_weakness", "不浄", "アンデッドからのダメージ増加"),
    ENDER_WEAKNESS("ender_weakness", "虚弱界", "エンダー系からのダメージ増加"),
    DAYLIGHT_WEAKNESS("daylight_weakness", "日光弱体", "昼に能力低下"),
    NIGHT_WEAKNESS("night_weakness", "夜間弱体", "夜に能力低下"),
    ARMOR_WEIGHT("armor_weight", "装備重量", "防具装備中に速度低下"),
    FRAGILE_TOOL("fragile_tool", "脆い手元", "道具耐久が減りやすい"),
    UNSTABLE_POWER("unstable_power", "不安定", "低確率で能力が一時停止"),
    NOISY_AURA("noisy_aura", "騒霊", "敵に狙われやすくなる"),
    COOLDOWN_UP("cooldown_up", "鈍い魔力", "クールタイム増加"),
    POTION_WEAK("potion_weak", "薬効低下", "ポーション効果時間低下"),
    KNOCKBACK_WEAK("knockback_weak", "軽身", "ノックバックを受けやすい"),
    BLEED("bleed", "出血", "被ダメージ後に追加小ダメージ");

    private final String id;
    private final String displayName;
    private final String description;

    CharmNegativeEffectType(String id, String displayName, String description) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String description() { return description; }
}

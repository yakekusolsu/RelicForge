package jp.yourname.charmorb.orb;

public enum OrbCurseNegativeEffectType {
    CURSE_FRAGILE_BODY("curse_fragile_body", "呪脆弱", "被ダメージ大幅増加", OrbTargetType.ARMOR),
    CURSE_LIFE_DRAIN("curse_life_drain", "命蝕", "時間経過で体力が削られる", OrbTargetType.UNIVERSAL),
    CURSE_HUNGER_COLLAPSE("curse_hunger_collapse", "飢餓崩壊", "空腹消費が大幅増加", OrbTargetType.UNIVERSAL),
    CURSE_HEALING_BLOCK("curse_healing_block", "治癒封じ", "自然回復・回復効果が大幅低下", OrbTargetType.UNIVERSAL),
    CURSE_SOUL_PRICE("curse_soul_price", "魂の代価", "攻撃時に確率で自傷", OrbTargetType.WEAPON),
    CURSE_ARMOR_DECAY("curse_armor_decay", "防具腐食", "防具耐久消費が大幅増加", OrbTargetType.ARMOR),
    CURSE_TOOL_DECAY("curse_tool_decay", "道具腐食", "道具耐久消費が大幅増加", OrbTargetType.TOOL),
    CURSE_SLOW_BODY("curse_slow_body", "重呪", "移動速度大幅低下", OrbTargetType.ARMOR),
    CURSE_WEAK_ATTACK("curse_weak_attack", "衰撃", "与ダメージ低下", OrbTargetType.WEAPON),
    CURSE_DAY_BURN("curse_day_burn", "日蝕灼け", "昼間に弱体化・時々炎上", OrbTargetType.UNIVERSAL),
    CURSE_NIGHT_TERROR("curse_night_terror", "夜恐怖", "夜に敵に狙われやすくなる", OrbTargetType.UNIVERSAL),
    CURSE_WATER_FEAR("curse_water_fear", "水恐怖", "水中で弱体化・ダメージ", OrbTargetType.UNIVERSAL),
    CURSE_FIRE_FEAR("curse_fire_fear", "炎恐怖", "炎・溶岩ダメージ大幅増加", OrbTargetType.ARMOR),
    CURSE_LIGHT_REJECT("curse_light_reject", "光拒絶", "回復・光系効果を受けにくい", OrbTargetType.UNIVERSAL),
    CURSE_DARK_NOISE("curse_dark_noise", "闇鳴り", "敵を引き寄せる", OrbTargetType.ARMOR),
    CURSE_UNSTABLE("curse_unstable", "不安定呪力", "ランダムで弱体化が発生", OrbTargetType.UNIVERSAL),
    CURSE_MADNESS("curse_madness", "狂気", "低確率で鈍足・混乱風効果", OrbTargetType.UNIVERSAL),
    CURSE_BLOOD_LEAK("curse_blood_leak", "血漏れ", "被ダメージ後に継続ダメージ", OrbTargetType.ARMOR),
    CURSE_EXP_TAX("curse_exp_tax", "経験税", "獲得経験値低下", OrbTargetType.UNIVERSAL),
    CURSE_DEATH_MARK("curse_death_mark", "死印", "死亡時ペナルティ増加", OrbTargetType.UNIVERSAL),
    CURSE_SOCKET_LOCK("curse_socket_lock", "呪縛穴", "オーブを外す時にコストが必要", OrbTargetType.UNIVERSAL),
    CURSE_REPAIR_BLOCK("curse_repair_block", "修復拒絶", "修繕・耐久回復効果低下", OrbTargetType.UNIVERSAL);

    private final String id;
    private final String displayName;
    private final String description;
    private final OrbTargetType target;

    OrbCurseNegativeEffectType(String id, String displayName, String description, OrbTargetType target) {
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

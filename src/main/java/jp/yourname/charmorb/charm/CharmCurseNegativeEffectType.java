package jp.yourname.charmorb.charm;

public enum CharmCurseNegativeEffectType {
    CURSE_FRAGILE_BODY("curse_fragile_body", "呪脆弱", "被ダメージ大幅増加"),
    CURSE_LIFE_DRAIN("curse_life_drain", "命蝕", "時間経過で体力が削られる"),
    CURSE_HUNGER_COLLAPSE("curse_hunger_collapse", "飢餓崩壊", "空腹消費が大幅増加"),
    CURSE_HEALING_BLOCK("curse_healing_block", "治癒封じ", "自然回復・回復効果が大きく低下"),
    CURSE_SOUL_PRICE("curse_soul_price", "魂の代価", "攻撃時に確率で自傷"),
    CURSE_ARMOR_DECAY("curse_armor_decay", "防具腐食", "防具耐久消費が大幅増加"),
    CURSE_TOOL_DECAY("curse_tool_decay", "道具腐食", "道具耐久消費が大幅増加"),
    CURSE_SLOW_BODY("curse_slow_body", "重呪", "移動速度大幅低下"),
    CURSE_WEAK_ATTACK("curse_weak_attack", "衰撃", "与ダメージ低下"),
    CURSE_DAY_BURN("curse_day_burn", "日蝕灼け", "昼間に弱体化・時々炎上"),
    CURSE_NIGHT_TERROR("curse_night_terror", "夜恐怖", "夜に敵に狙われやすくなる"),
    CURSE_WATER_FEAR("curse_water_fear", "水恐怖", "水中で弱体化・ダメージ"),
    CURSE_FIRE_FEAR("curse_fire_fear", "炎恐怖", "炎・溶岩ダメージ大幅増加"),
    CURSE_LIGHT_REJECT("curse_light_reject", "光拒絶", "光属性効果を受けにくくなる"),
    CURSE_DARK_NOISE("curse_dark_noise", "闇鳴り", "敵を引き寄せる"),
    CURSE_UNSTABLE("curse_unstable", "不安定呪力", "ランダムで弱体化が発生"),
    CURSE_MADNESS("curse_madness", "狂気", "低確率で操作妨害風の鈍足・混乱"),
    CURSE_BLOOD_LEAK("curse_blood_leak", "血漏れ", "被ダメージ後に継続ダメージ"),
    CURSE_EXP_TAX("curse_exp_tax", "経験税", "獲得経験値低下"),
    CURSE_DEATH_MARK("curse_death_mark", "死印", "死亡時のペナルティ増加");

    private final String id;
    private final String displayName;
    private final String description;

    CharmCurseNegativeEffectType(String id, String displayName, String description) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String description() { return description; }
}

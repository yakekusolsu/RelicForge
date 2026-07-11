package jp.yourname.charmorb.orb;

public enum OrbCurseSkillType {
    BLOOD_BURST("curse_skill_blood_burst", "血爆", "周囲に大ダメージ", "自分も体力消費", 45, OrbTargetType.WEAPON),
    DARK_DASH("curse_skill_dark_dash", "闇走", "高速移動＋通過敵にダメージ", "空腹大消費", 35, OrbTargetType.BOOTS),
    BLACK_FLAME("curse_skill_black_flame", "黒炎解放", "広範囲炎上＋ウィザー", "自分も炎上", 60, OrbTargetType.WEAPON),
    SOUL_DEVOUR("curse_skill_soul_devour", "魂喰解放", "敵撃破時バフを即時獲得", "一定時間被ダメ増加", 75, OrbTargetType.WEAPON),
    FORBIDDEN_HEAL("curse_skill_forbidden_heal", "禁忌治癒", "大回復", "最大体力一時低下", 90, OrbTargetType.ARMOR),
    ABYSS_GATE("curse_skill_abyss_gate", "奈落門", "周囲の敵を引き寄せ弱体化", "自分も鈍足", 80, OrbTargetType.ARMOR),
    MADNESS("curse_skill_madness", "狂乱", "攻撃速度・火力大幅上昇", "防御大幅低下", 70, OrbTargetType.WEAPON),
    DEATH_MARK("curse_skill_death_mark", "死印", "敵に大ダメージ印を付与", "自分にも小さい死印", 60, OrbTargetType.WEAPON),
    GRAVITY_CRUSH("curse_skill_gravity_crush", "重力崩壊", "メイス範囲大衝撃", "自分にも反動", 80, OrbTargetType.MACE),
    VOID_ESCAPE("curse_skill_void_escape", "虚無逃走", "ダメージ無効＋短距離転移", "使用後弱体化", 120, OrbTargetType.ARMOR),
    PLAGUE_ZONE("curse_skill_plague_zone", "疫病領域", "周囲に毒・弱体化", "自分も毒", 90, OrbTargetType.ARMOR),
    CURSED_REPAIR("curse_skill_cursed_repair", "呪修復", "装備耐久を回復", "体力消費", 120, OrbTargetType.TOOL),
    OVERLOAD_MINE("curse_skill_overload_mine", "過負荷採掘", "一定時間超高速採掘", "ツール耐久大消費", 90, OrbTargetType.TOOL),
    CONTRACT("curse_skill_contract", "契約", "一定時間全能力大幅上昇", "終了後大弱体化", 180, OrbTargetType.UNIVERSAL);

    private final String id;
    private final String displayName;
    private final String description;
    private final String cost;
    private final int cooldownSeconds;
    private final OrbTargetType target;

    OrbCurseSkillType(String id, String displayName, String description, String cost, int cooldownSeconds, OrbTargetType target) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.cost = cost;
        this.cooldownSeconds = cooldownSeconds;
        this.target = target;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String description() { return description; }
    public String cost() { return cost; }
    public int cooldownSeconds() { return cooldownSeconds; }
    public OrbTargetType target() { return target; }
}

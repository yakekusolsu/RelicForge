package jp.yourname.charmorb.charm;

public enum CharmCursePositiveEffectType {
    CURSE_BERSERK("curse_berserk", "呪狂戦", "与ダメージ大幅上昇"),
    CURSE_BLOOD_POWER("curse_blood_power", "呪血力", "体力が低いほど攻撃が大幅上昇"),
    CURSE_LIFE_STEAL("curse_life_steal", "呪吸命", "与ダメージの一部を大きく回復"),
    CURSE_DARK_ARMOR("curse_dark_armor", "呪黒鎧", "被ダメージを大きく軽減"),
    CURSE_EXECUTIONER("curse_executioner", "呪処刑人", "体力が低い敵に大ダメージ"),
    CURSE_SOUL_EATER("curse_soul_eater", "呪魂喰い", "敵撃破ごとに一時的に攻撃・速度上昇"),
    CURSE_REVENGE("curse_revenge", "呪復讐", "被ダメージ後、短時間攻撃力上昇"),
    CURSE_FORBIDDEN_SPEED("curse_forbidden_speed", "禁忌加速", "移動速度・攻撃速度大幅上昇"),
    CURSE_VOID_STEP("curse_void_step", "呪虚歩", "低確率でダメージを無効化し短距離転移"),
    CURSE_LUCK("curse_luck", "呪運", "ドロップ率・レア入手率上昇"),
    CURSE_PREDATOR("curse_predator", "捕食者", "敵撃破時に体力・満腹度回復"),
    CURSE_OVERDRIVE("curse_overdrive", "暴走覚醒", "体力30%以下で全能力大幅上昇"),
    CURSE_DEMON_HAND("curse_demon_hand", "魔手", "近接攻撃時に防御貫通"),
    CURSE_GRAVITY_BREAK("curse_gravity_break", "重力破砕", "メイス・斧攻撃時に範囲衝撃"),
    CURSE_BLACK_FLAME("curse_black_flame", "黒炎", "攻撃時に炎上＋ウィザー付与"),
    CURSE_TIME_BREAK("curse_time_break", "時壊し", "クールタイム短縮、低確率で連続発動"),
    CURSE_IMMORTAL("curse_immortal", "偽不死", "致命傷を一度だけ耐える"),
    CURSE_PLAGUE_AURA("curse_plague_aura", "疫病の衣", "周囲の敵に毒・弱体化"),
    CURSE_ABYSSAL_CRIT("curse_abyssal_crit", "奈落会心", "低体力時に会心ダメージ大幅上昇"),
    CURSE_KING_SLAUGHTER("curse_king_slaughter", "王殺しの呪印", "ボスへのダメージ大幅上昇");

    private final String id;
    private final String displayName;
    private final String description;

    CharmCursePositiveEffectType(String id, String displayName, String description) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String description() { return description; }
}

package jp.yourname.charmorb.orb;

public enum OrbSkillType {
    FLAME_SLASH("skill_flame_slash", "フレイムスラッシュ", "前方に炎の斬撃", 20, OrbTargetType.MELEE_WEAPON),
    ICE_BIND("skill_ice_bind", "アイスバインド", "周囲の敵を鈍足化", 25, OrbTargetType.WEAPON),
    THUNDER_STRIKE("skill_thunder_strike", "サンダーストライク", "指定方向に雷撃", 30, OrbTargetType.WEAPON),
    MACE_QUAKE("skill_mace_quake", "メイスクエイク", "メイスで地面を叩き範囲衝撃", 35, OrbTargetType.MACE),
    VOID_CUT("skill_void_cut", "ヴォイドカット", "防御貫通の一撃", 40, OrbTargetType.MELEE_WEAPON),
    HOLY_JUDGEMENT("skill_holy_judgement", "ホーリージャッジ", "アンデッドへ大ダメージ", 45, OrbTargetType.WEAPON),
    DRAGON_BREAK("skill_dragon_break", "ドラゴンブレイク", "大型の敵へ強力な一撃", 60, OrbTargetType.WEAPON),
    ARROW_RAIN("skill_arrow_rain", "アローレイン", "前方範囲に矢の雨", 45, OrbTargetType.BOW),
    TRIDENT_STORM("skill_trident_storm", "トライデントストーム", "水・雷属性の範囲攻撃", 40, OrbTargetType.TRIDENT),
    BERSERK_DRIVE("skill_berserk_drive", "バーサークドライブ", "一時的に攻撃上昇、防御低下", 60, OrbTargetType.WEAPON),
    GUARD_BARRIER("skill_guard_barrier", "ガードバリア", "一定時間被ダメージ軽減", 45, OrbTargetType.ARMOR),
    PHOENIX_GUARD("skill_phoenix_guard", "フェニックスガード", "致命傷を一度だけ防ぐ状態を付与", 180, OrbTargetType.ARMOR),
    IRON_WALL("skill_iron_wall", "アイアンウォール", "ノックバック無効＋防御上昇", 60, OrbTargetType.ARMOR),
    REFLECT_SHELL("skill_reflect_shell", "リフレクトシェル", "被ダメージ時に反射", 70, OrbTargetType.ARMOR),
    CLEANSE_FIELD("skill_cleanse_field", "クレンズフィールド", "悪効果解除", 50, OrbTargetType.ARMOR),
    WORLD_TREE_BLESSING("skill_world_tree_blessing", "世界樹の祝福", "周囲の味方を回復", 90, OrbTargetType.ARMOR),
    VOID_BARRIER("skill_void_barrier", "ヴォイドバリア", "数秒間、一定確率でダメージ無効", 90, OrbTargetType.ARMOR),
    BLINK("skill_blink", "ブリンク", "視線方向へ短距離転移", 20, OrbTargetType.BOOTS),
    DASH("skill_dash", "ダッシュ", "前方へ高速移動", 15, OrbTargetType.BOOTS),
    SKY_LEAP("skill_sky_leap", "スカイリープ", "高く跳躍し落下軽減", 25, OrbTargetType.BOOTS),
    WIND_STEP("skill_wind_step", "ウィンドステップ", "一定時間速度上昇", 30, OrbTargetType.BOOTS),
    ESCAPE("skill_escape", "エスケープ", "体力低下時に即時後退", 45, OrbTargetType.BOOTS),
    WATER_DASH("skill_water_dash", "ウォーターダッシュ", "水中で高速移動", 20, OrbTargetType.BOOTS),
    VEIN_MINE("skill_vein_mine", "ヴェインマイン", "周囲の同種鉱石をまとめて破壊", 60, OrbTargetType.PICKAXE),
    TREE_FELLER("skill_tree_feller", "ツリーフェラー", "木をまとめて伐採", 60, OrbTargetType.AXE),
    EARTH_DIG("skill_earth_dig", "アースディグ", "土・砂・砂利を範囲破壊", 45, OrbTargetType.SHOVEL),
    HARVEST_WAVE("skill_harvest_wave", "ハーベストウェーブ", "周囲の作物を収穫", 60, OrbTargetType.HOE),
    REPAIR_BURST("skill_repair_burst", "リペアバースト", "装備耐久を少し回復", 120, OrbTargetType.TOOL),
    BUILDER_MODE("skill_builder_mode", "ビルダーモード", "一定時間建築補助", 90, OrbTargetType.TOOL),
    ORE_SCAN("skill_ore_scan", "オアスキャン", "周囲の鉱石を発光表示", 45, OrbTargetType.PICKAXE),
    TIME_SKIP("skill_time_skip", "タイムスキップ", "自分のスキル待機時間を少し短縮", 90, OrbTargetType.UNIVERSAL),
    FATE_SHUFFLE("skill_fate_shuffle", "フェイトシャッフル", "一時的なランダムバフ", 60, OrbTargetType.UNIVERSAL),
    CHAOS_BURST("skill_chaos_burst", "カオスバースト", "ランダムな強化・弱体化を周囲に発生", 70, OrbTargetType.UNIVERSAL),
    KING_ORDER("skill_king_order", "王命", "周囲の敵を弱体化", 80, OrbTargetType.UNIVERSAL),
    GENESIS_BOOST("skill_genesis_boost", "ジェネシスブースト", "全オーブ効果を一時強化", 120, OrbTargetType.UNIVERSAL),
    STARFALL("skill_starfall", "スターフォール", "周囲に星の欠片を落とす", 90, OrbTargetType.UNIVERSAL),
    DIMENSION_ANCHOR("skill_dimension_anchor", "次元固定", "一定時間ノックバック・転移妨害を軽減", 90, OrbTargetType.UNIVERSAL);

    private final String id;
    private final String displayName;
    private final String description;
    private final int cooldownSeconds;
    private final OrbTargetType target;

    OrbSkillType(String id, String displayName, String description, int cooldownSeconds, OrbTargetType target) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.cooldownSeconds = cooldownSeconds;
        this.target = target;
    }

    public String id() { return id; }
    public String displayName() { return displayName; }
    public String description() { return description; }
    public int cooldownSeconds() { return cooldownSeconds; }
    public OrbTargetType target() { return target; }
}

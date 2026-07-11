package jp.yourname.charmorb.charm;

public record CharmType(String id, CharmElement element, CharmRarity rarity, String displayName) {
    public static CharmType of(CharmElement element, CharmRarity rarity) {
        String id = element.name().toLowerCase() + "_" + rarity.name().toLowerCase();
        return new CharmType(id, element, rarity, name(element, rarity));
    }

    public static CharmType byId(String id) {
        if (id == null) return null;
        String[] parts = id.split("_", 2);
        if (parts.length != 2) return null;
        CharmElement element = CharmElement.parse(parts[0]);
        CharmRarity rarity = CharmRarity.parse(parts[1]);
        return element == null || rarity == null ? null : of(element, rarity);
    }

    private static String name(CharmElement element, CharmRarity rarity) {
        String[] names = switch (element) {
            case FIRE -> new String[]{"火花の石", "灯火の護符", "紅蓮の欠片", "焔竜の紋章", "業火の王冠", "太陽炉の核", "不滅焔の心臓", "死焔の宝珠"};
            case ICE -> new String[]{"霜粒の石", "氷花の護符", "蒼氷の欠片", "氷狼の紋章", "永久凍土の王冠", "絶零の核", "白銀世界の心臓", "凍哭の宝珠"};
            case WATER -> new String[]{"水滴の石", "清流の護符", "蒼海の欠片", "潮騎士の紋章", "海王の王冠", "深海炉の核", "大海嘯の心臓", "溺魂の宝珠"};
            case WIND -> new String[]{"そよ風の石", "風切りの護符", "疾風の欠片", "嵐鷹の紋章", "天空王の王冠", "暴風炉の核", "天翔ける心臓", "断風の宝珠"};
            case EARTH -> new String[]{"小石の護符", "土壁の護符", "大地の欠片", "岩巨人の紋章", "地帝の王冠", "地脈炉の核", "世界樹根の心臓", "埋葬の宝珠"};
            case LIGHT -> new String[]{"淡光の石", "祈光の護符", "聖光の欠片", "白翼の紋章", "聖王の王冠", "星光炉の核", "創世光の心臓", "堕光の宝珠"};
            case DARK -> new String[]{"影粒の石", "宵闇の護符", "黒月の欠片", "闇騎士の紋章", "夜王の王冠", "暗黒炉の核", "終夜の心臓", "冥影の宝珠"};
            case NATURE -> new String[]{"若葉の石", "芽吹きの護符", "森霊の欠片", "鹿王の紋章", "森王の王冠", "生命炉の核", "原初樹の心臓", "枯命の宝珠"};
            case THUNDER -> new String[]{"静電の石", "雷鳴の護符", "紫電の欠片", "雷獣の紋章", "雷帝の王冠", "天雷炉の核", "神鳴りの心臓", "呪雷の宝珠"};
            case VOID -> new String[]{"虚粒の石", "空白の護符", "虚空の欠片", "深淵の紋章", "虚王の王冠", "虚無炉の核", "無限空洞の心臓", "奈落の宝珠"};
        };
        return names[rarity.ordinal()];
    }
}

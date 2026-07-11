package jp.yourname.charmorb.charm;

import java.util.Locale;

public enum CharmRarity {
    COMMON(1, 2, 10, "&f"),
    UNCOMMON(1, 3, 25, "&a"),
    RARE(2, 4, 60, "&9"),
    EPIC(3, 5, 150, "&5"),
    LEGENDARY(4, 6, 400, "&6"),
    MYTHIC(5, 7, 900, "&d"),
    UNIQUE(6, 8, 1800, "&b"),
    CURSE(3, 8, 1200, "&4");

    private final int minLevel;
    private final int maxLevel;
    private final int price;
    private final String color;

    CharmRarity(int minLevel, int maxLevel, int price, String color) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.price = price;
        this.color = color;
    }

    public int minLevel() { return minLevel; }
    public int maxLevel() { return maxLevel; }
    public int price() { return price; }
    public String color() { return color; }

    public static CharmRarity parse(String raw) {
        try {
            return raw == null ? null : valueOf(raw.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}

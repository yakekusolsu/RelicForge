package jp.yourname.charmorb.charm;

import java.util.Locale;

public enum CharmElement {
    FIRE("火"),
    ICE("氷"),
    WATER("水"),
    WIND("風"),
    EARTH("地"),
    LIGHT("光"),
    DARK("闇"),
    NATURE("自然"),
    THUNDER("雷"),
    VOID("虚無");

    private final String jp;

    CharmElement(String jp) {
        this.jp = jp;
    }

    public String jp() {
        return jp;
    }

    public boolean conflicts(CharmElement other) {
        return (this == FIRE && other == ICE) || (this == ICE && other == FIRE)
            || (this == LIGHT && other == DARK) || (this == DARK && other == LIGHT)
            || (this == WATER && other == THUNDER) || (this == THUNDER && other == WATER)
            || (this == NATURE && other == VOID) || (this == VOID && other == NATURE);
    }

    public static CharmElement parse(String raw) {
        try {
            return raw == null ? null : valueOf(raw.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}

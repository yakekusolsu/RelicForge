package jp.yourname.charmorb.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Text {
    private static final LegacyComponentSerializer AMP = LegacyComponentSerializer.legacyAmpersand();

    private Text() {
    }

    public static Component c(String text) {
        return AMP.deserialize(text == null ? "" : text).decoration(TextDecoration.ITALIC, false);
    }

    public static String plain(String text) {
        return text == null ? "" : text.replace('&', '§');
    }
}

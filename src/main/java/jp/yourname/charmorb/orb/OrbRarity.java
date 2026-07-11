package jp.yourname.charmorb.orb;

import java.util.Locale;
import org.bukkit.Material;

public enum OrbRarity {
    COMMON("&f", 10, Material.WHITE_STAINED_GLASS_PANE),
    UNCOMMON("&a", 25, Material.LIME_STAINED_GLASS_PANE),
    RARE("&9", 60, Material.BLUE_STAINED_GLASS_PANE),
    EPIC("&5", 150, Material.PURPLE_STAINED_GLASS_PANE),
    LEGENDARY("&6", 400, Material.ORANGE_STAINED_GLASS_PANE),
    MYTHIC("&d", 900, Material.MAGENTA_STAINED_GLASS_PANE),
    UNIQUE("&b", 1800, Material.CYAN_STAINED_GLASS_PANE),
    CURSE("&4", 1200, Material.RED_STAINED_GLASS_PANE);

    private final String color;
    private final int price;
    private final Material guiMaterial;

    OrbRarity(String color, int price, Material guiMaterial) {
        this.color = color;
        this.price = price;
        this.guiMaterial = guiMaterial;
    }

    public String color() {
        return color;
    }

    public int price() {
        return price;
    }

    public Material guiMaterial() {
        return guiMaterial;
    }

    public static OrbRarity parse(String raw) {
        if (raw == null) {
            return null;
        }
        try {
            return valueOf(raw.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}

package jp.yourname.charmorb.orb;

import java.util.Locale;
import org.bukkit.inventory.ItemStack;

public enum OrbTargetType {
    UNIVERSAL("汎用"),
    WEAPON("武器"),
    MELEE_WEAPON("近接武器"),
    RANGED_WEAPON("遠距離武器"),
    SWORD("剣"),
    AXE("斧"),
    MACE("メイス"),
    BOW("弓"),
    CROSSBOW("クロスボウ"),
    TRIDENT("トライデント"),
    ARMOR("防具"),
    HELMET("頭防具"),
    CHESTPLATE("胴防具"),
    LEGGINGS("脚防具"),
    BOOTS("足防具"),
    TOOL("ツール"),
    PICKAXE("ツルハシ"),
    SHOVEL("シャベル"),
    HOE("クワ");

    private final String displayName;

    OrbTargetType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    public boolean matches(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            return false;
        }
        String name = item.getType().name().toLowerCase(Locale.ROOT);
        return switch (this) {
            case UNIVERSAL -> true;
            case WEAPON -> name.endsWith("_sword") || name.endsWith("_axe") || name.equals("mace") || name.equals("bow") || name.equals("crossbow") || name.equals("trident");
            case MELEE_WEAPON -> name.endsWith("_sword") || name.endsWith("_axe") || name.equals("mace");
            case RANGED_WEAPON -> name.equals("bow") || name.equals("crossbow") || name.equals("trident");
            case SWORD -> name.endsWith("_sword");
            case AXE -> name.endsWith("_axe");
            case MACE -> name.equals("mace");
            case BOW -> name.equals("bow");
            case CROSSBOW -> name.equals("crossbow");
            case TRIDENT -> name.equals("trident");
            case ARMOR -> name.endsWith("_helmet") || name.endsWith("_chestplate") || name.endsWith("_leggings") || name.endsWith("_boots");
            case HELMET -> name.endsWith("_helmet");
            case CHESTPLATE -> name.endsWith("_chestplate");
            case LEGGINGS -> name.endsWith("_leggings");
            case BOOTS -> name.endsWith("_boots");
            case TOOL -> name.endsWith("_pickaxe") || name.endsWith("_shovel") || name.endsWith("_hoe") || name.endsWith("_axe") || name.equals("shears") || name.equals("brush") || name.equals("fishing_rod");
            case PICKAXE -> name.endsWith("_pickaxe");
            case SHOVEL -> name.endsWith("_shovel");
            case HOE -> name.endsWith("_hoe");
        };
    }
}

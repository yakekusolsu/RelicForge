package jp.yourname.charmorb.util;

import java.util.Locale;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class ItemUtil {
    private ItemUtil() {
    }

    public static boolean isAir(ItemStack item) {
        return item == null || item.getType().isAir();
    }

    public static boolean isOrbTarget(ItemStack item) {
        if (isAir(item)) {
            return false;
        }
        String name = item.getType().name().toLowerCase(Locale.ROOT);
        return name.endsWith("_sword")
            || name.endsWith("_axe")
            || name.equals("mace")
            || name.equals("bow")
            || name.equals("crossbow")
            || name.equals("trident")
            || name.endsWith("_helmet")
            || name.endsWith("_chestplate")
            || name.endsWith("_leggings")
            || name.endsWith("_boots")
            || name.endsWith("_pickaxe")
            || name.endsWith("_shovel")
            || name.endsWith("_hoe")
            || name.equals("shears")
            || name.equals("brush")
            || name.equals("fishing_rod");
    }

    public static String equipmentType(ItemStack item) {
        if (isAir(item)) {
            return "なし";
        }
        String name = item.getType().name().toLowerCase(Locale.ROOT);
        if (name.endsWith("_helmet")) return "頭防具";
        if (name.endsWith("_chestplate")) return "胴防具";
        if (name.endsWith("_leggings")) return "脚防具";
        if (name.endsWith("_boots")) return "足防具";
        if (name.equals("bow") || name.equals("crossbow") || name.equals("trident")) return "遠距離武器";
        if (name.endsWith("_sword") || name.endsWith("_axe") || name.equals("mace")) return "近接武器";
        if (name.endsWith("_pickaxe") || name.endsWith("_shovel") || name.endsWith("_hoe")) return "ツール";
        if (name.equals("shears") || name.equals("brush") || name.equals("fishing_rod")) return "ツール";
        return item.getType().name();
    }

    public static Material safeMaterial(String preferred, Material fallback) {
        Material material = Material.matchMaterial(preferred);
        return material == null ? fallback : material;
    }
}

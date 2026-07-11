package jp.yourname.charmorb.charm;

import java.util.List;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.orb.OrbGui;
import jp.yourname.charmorb.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public final class CharmListGui {
    private final CharmOrbPlugin plugin;

    public CharmListGui(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public void openElements(Player player) {
        Inventory inv = Bukkit.createInventory(new CharmListHolder(CharmListHolder.View.ELEMENTS, null, null), 27, Text.c("チャーム一覧 属性"));
        int slot = 8;
        for (CharmElement element : CharmElement.values()) {
            inv.setItem(slot++, OrbGui.icon(material(element), CharmFlavor.elementColor(element) + element.jp() + "属性", List.of("&b傾向: &f" + CharmFlavor.elementTitle(element), "&aクリックで希少度を表示")));
        }
        player.openInventory(inv);
    }

    public void openRarities(Player player, CharmElement element) {
        Inventory inv = Bukkit.createInventory(new CharmListHolder(CharmListHolder.View.RARITIES, element, null), 27, Text.c("チャーム一覧 " + element.jp() + "属性"));
        int slot = 9;
        for (CharmRarity rarity : CharmRarity.values()) {
            CharmType type = CharmType.of(element, rarity);
            inv.setItem(slot++, OrbGui.icon(Material.PRISMARINE_CRYSTALS, CharmFlavor.rarityName(type), List.of(CharmFlavor.coloredName(type), "&aクリックで詳細")));
        }
        inv.setItem(26, OrbGui.icon(Material.ARROW, "&e戻る", List.of("&7属性一覧へ戻る")));
        player.openInventory(inv);
    }

    public void openDetail(Player player, CharmElement element, CharmRarity rarity) {
        CharmType type = CharmType.of(element, rarity);
        Inventory inv = Bukkit.createInventory(new CharmListHolder(CharmListHolder.View.DETAIL, element, rarity), 27, Text.c("チャーム詳細"));
        inv.setItem(13, OrbGui.icon(Material.ENCHANTED_BOOK, CharmFlavor.coloredName(type), List.of(
            "&a基本ステータス",
            "&7  属性: " + CharmFlavor.elementColor(element) + element.jp(),
            "&7  希少度: " + CharmFlavor.rarityName(type),
            "&7  効果Lv範囲: &a" + rarity.minLevel() + "&7-&a" + rarity.maxLevel(),
            "",
            "&bオプション効果",
            "&7  &a+効果傾向: &e" + CharmFlavor.elementTitle(element),
            "&7  &c-効果: &7呪いチャームのみ発生",
            "&7  入手: &fドロップまたは管理者コマンド"
        )));
        inv.setItem(26, OrbGui.icon(Material.ARROW, "&e戻る", List.of("&7希少度一覧へ戻る")));
        player.openInventory(inv);
    }

    private Material material(CharmElement element) {
        return switch (element) {
            case FIRE -> Material.BLAZE_POWDER;
            case ICE -> Material.BLUE_ICE;
            case WATER -> Material.HEART_OF_THE_SEA;
            case WIND -> Material.FEATHER;
            case EARTH -> Material.MOSS_BLOCK;
            case LIGHT -> Material.GLOWSTONE_DUST;
            case DARK -> Material.ECHO_SHARD;
            case NATURE -> Material.OAK_SAPLING;
            case THUNDER -> Material.LIGHTNING_ROD;
            case VOID -> Material.ENDER_PEARL;
        };
    }
}

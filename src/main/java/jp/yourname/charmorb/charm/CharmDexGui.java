package jp.yourname.charmorb.charm;

import java.util.List;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.orb.OrbGui;
import jp.yourname.charmorb.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public final class CharmDexGui {
    private final CharmOrbPlugin plugin;

    public CharmDexGui(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, Text.c("チャーム図鑑"));
        int cell = 0;
        for (CharmElement element : CharmElement.values()) {
            for (CharmRarity rarity : CharmRarity.values()) {
                CharmType type = CharmType.of(element, rarity);
                boolean found = plugin.charms().data(player).discovered().contains(type.id());
                inv.setItem(cell++, OrbGui.icon(found ? Material.ENCHANTED_BOOK : Material.GRAY_DYE, (found ? CharmFlavor.coloredName(type) : "&8未発見"), List.of(CharmFlavor.elementColor(element) + element.jp() + "属性 &7/ " + CharmFlavor.rarityName(type))));
                if (cell >= 54) break;
            }
            if (cell >= 54) break;
        }
        player.openInventory(inv);
    }
}

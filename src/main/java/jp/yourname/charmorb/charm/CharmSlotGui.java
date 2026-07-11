package jp.yourname.charmorb.charm;

import java.util.List;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.orb.OrbGui;
import jp.yourname.charmorb.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class CharmSlotGui {
    public static final int[] CELLS = {
        10, 11, 12, 13, 14, 15, 16,
        19, 20, 21, 22, 23, 24, 25,
        28, 29, 30, 31, 32, 33, 34,
        37, 38, 39, 40, 41, 42
    };
    private final CharmOrbPlugin plugin;

    public CharmSlotGui(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(new CharmSlotHolder(), 54, Text.c("チャームスロット"));
        CharmPlayerData data = plugin.charms().data(player);
        ItemStack[] slots = data.slots();
        int unlocked = data.unlockedSlots();
        inv.setItem(4, OrbGui.icon(Material.BOOK, "&bチャームスロット", List.of(
            "&7解放済み: &a" + unlocked + "&7/" + CharmPlayerData.MAX_SLOTS,
            "&7追加解放: &e" + data.extraSlots() + "&7/" + plugin.settings().charmUnlockExtraSlots(),
            "&8上限進化の巻き物で追加枠を解放"
        )));
        for (int i = 0; i < CELLS.length; i++) {
            if (i >= unlocked) {
                inv.setItem(CELLS[i], OrbGui.icon(Material.GRAY_STAINED_GLASS_PANE, "&8ロック枠 " + (i + 1), List.of("&7上限進化の巻き物で解放できます")));
                continue;
            }
            inv.setItem(CELLS[i], slots[i] == null ? OrbGui.icon(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bチャーム枠 " + (i + 1), List.of("&7カーソルのチャームをクリックで装備")) : slots[i]);
        }
        player.openInventory(inv);
    }
}

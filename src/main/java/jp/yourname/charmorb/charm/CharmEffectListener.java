package jp.yourname.charmorb.charm;

import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class CharmEffectListener implements Listener {
    private final CharmOrbPlugin plugin;

    public CharmEffectListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof CharmSlotHolder) || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        Inventory top = event.getView().getTopInventory();
        if (event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        }
        if (event.getClickedInventory() != top) {
            handlePlayerInventoryClick(event, player);
            return;
        }
        event.setCancelled(true);
        int index = -1;
        for (int i = 0; i < CharmSlotGui.CELLS.length; i++) {
            if (event.getSlot() == CharmSlotGui.CELLS[i]) index = i;
        }
        if (index < 0) return;
        CharmPlayerData data = plugin.charms().data(player);
        if (index >= data.unlockedSlots()) {
            player.sendMessage(Text.c("&cこのチャーム枠はまだ解放されていません。"));
            return;
        }
        ItemStack[] slots = data.slots();
        if (slots[index] != null) {
            player.getInventory().addItem(slots[index]);
            slots[index] = null;
            new CharmSlotGui(plugin).open(player);
            return;
        }
        ItemStack cursor = event.getCursor();
        CharmType type = plugin.charms().factory().read(cursor);
        if (type == null) {
            player.sendMessage(Text.c("&cカーソルにチャームを持ってください。空スロットクリックで装備できます。"));
            return;
        }
        if (!plugin.charms().canEquip(player, cursor)) {
            player.sendMessage(Text.c("&c装備制限により、そのチャームは装備できません。"));
            return;
        }
        ItemStack one = cursor.clone();
        one.setAmount(1);
        slots[index] = one;
        plugin.charms().data(player).discovered().add(type.id());
        cursor.setAmount(cursor.getAmount() - 1);
        event.setCursor(cursor.getAmount() <= 0 ? null : cursor);
        new CharmSlotGui(plugin).open(player);
    }

    private void handlePlayerInventoryClick(InventoryClickEvent event, Player player) {
        ItemStack clicked = event.getCurrentItem();
        CharmType type = plugin.charms().factory().read(clicked);
        if (type == null) {
            return;
        }
        event.setCancelled(true);
        CharmPlayerData data = plugin.charms().data(player);
        ItemStack[] slots = data.slots();
        int empty = -1;
        for (int i = 0; i < data.unlockedSlots(); i++) {
            if (slots[i] == null) {
                empty = i;
                break;
            }
        }
        if (empty < 0) {
            player.sendMessage(Text.c("&cチャームスロットが満杯です。"));
            return;
        }
        if (!plugin.charms().canEquip(player, clicked)) {
            player.sendMessage(Text.c("&c装備制限により、そのチャームは装備できません。"));
            return;
        }
        ItemStack one = clicked.clone();
        one.setAmount(1);
        slots[empty] = one;
        plugin.charms().data(player).discovered().add(type.id());
        if (clicked.getAmount() <= 1) {
            event.setCurrentItem(null);
        } else {
            clicked.setAmount(clicked.getAmount() - 1);
            event.setCurrentItem(clicked);
        }
        player.sendMessage(Text.c(CharmFlavor.coloredName(type) + " &aを装備しました。"));
        new CharmSlotGui(plugin).open(player);
    }
}

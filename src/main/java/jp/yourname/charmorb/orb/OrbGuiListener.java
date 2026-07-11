package jp.yourname.charmorb.orb;

import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.ItemUtil;
import jp.yourname.charmorb.util.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class OrbGuiListener implements Listener {
    private final CharmOrbPlugin plugin;
    private final OrbItemFactory itemFactory;

    public OrbGuiListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
        this.itemFactory = new OrbItemFactory(plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof OrbGuiHolder holder) || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        Inventory top = event.getView().getTopInventory();
        if (event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        }
        if (event.getClickedInventory() != top) {
            handlePlayerInventoryClick(event, player, holder);
            return;
        }
        event.setCancelled(true);
        int slotIndex = -1;
        for (int i = 0; i < OrbGui.SLOT_CELLS.length; i++) {
            if (event.getSlot() == OrbGui.SLOT_CELLS[i]) {
                slotIndex = i + 1;
                break;
            }
        }
        if (slotIndex == -1) {
            return;
        }
        ItemStack equipment = player.getInventory().getItemInMainHand();
        if (!plugin.orbEquipment().isOrbEquipment(equipment) || !holder.matches(equipment)) {
            player.closeInventory();
            player.sendMessage(Text.c("&cGUIを開いた時の装備とメインハンド装備が違います。もう一度 /orb gui を開いてください。"));
            return;
        }
        if (slotIndex > plugin.orbEquipment().slots(equipment)) {
            player.sendMessage(Text.c("&cこのスロットはロックされています。"));
            return;
        }
        OrbType existing = plugin.orbEquipment().orbAt(equipment, slotIndex);
        if (existing != null) {
            plugin.orbEquipment().remove(equipment, slotIndex);
            player.getInventory().addItem(itemFactory.create(existing)).values().forEach(drop -> player.getWorld().dropItemNaturally(player.getLocation(), drop));
            player.sendMessage(Text.c(OrbFlavor.coloredName(existing) + " &aを取り外しました。"));
            new OrbGui(plugin).open(player);
            return;
        }
        ItemStack cursor = event.getCursor();
        boolean fromCursor = itemFactory.read(cursor) != null;
        ItemStack source = fromCursor ? cursor : player.getInventory().getItemInOffHand();
        OrbType insert = itemFactory.read(source);
        if (insert == null) {
            player.sendMessage(Text.c("&cカーソルにオーブを持って、空きスロットをクリックしてください。"));
            return;
        }
        if (!plugin.orbEquipment().insert(equipment, slotIndex, insert)) {
            player.sendMessage(Text.c("&c装着できませんでした。"));
            return;
        }
        source.setAmount(source.getAmount() - 1);
        if (source.getAmount() <= 0) {
            if (fromCursor) {
                event.setCursor(null);
            } else {
                player.getInventory().setItemInOffHand(null);
            }
        } else if (fromCursor) {
            event.setCursor(source);
        }
        player.sendMessage(Text.c(OrbFlavor.coloredName(insert) + " &aを装着しました。"));
        new OrbGui(plugin).open(player);
    }

    private void handlePlayerInventoryClick(InventoryClickEvent event, Player player, OrbGuiHolder holder) {
        ItemStack clicked = event.getCurrentItem();
        OrbType insert = itemFactory.read(clicked);
        if (insert == null) {
            return;
        }
        event.setCancelled(true);
        ItemStack equipment = player.getInventory().getItemInMainHand();
        if (!plugin.orbEquipment().isOrbEquipment(equipment) || !holder.matches(equipment)) {
            player.closeInventory();
            player.sendMessage(Text.c("&cGUIを開いた時の装備とメインハンド装備が違います。もう一度 /orb を開いてください。"));
            return;
        }
        int empty = firstEmptySlot(equipment);
        if (empty < 0) {
            player.sendMessage(Text.c("&cオーブスロットが満杯です。"));
            return;
        }
        if (!plugin.orbEquipment().insert(equipment, empty, insert)) {
            player.sendMessage(Text.c("&c装着できませんでした。"));
            return;
        }
        if (clicked.getAmount() <= 1) {
            event.setCurrentItem(null);
        } else {
            clicked.setAmount(clicked.getAmount() - 1);
            event.setCurrentItem(clicked);
        }
        player.sendMessage(Text.c(OrbFlavor.coloredName(insert) + " &aを装着しました。"));
        new OrbGui(plugin).open(player);
    }

    private int firstEmptySlot(ItemStack equipment) {
        int slots = plugin.orbEquipment().slots(equipment);
        for (int i = 1; i <= slots; i++) {
            if (plugin.orbEquipment().orbAt(equipment, i) == null) {
                return i;
            }
        }
        return -1;
    }
}

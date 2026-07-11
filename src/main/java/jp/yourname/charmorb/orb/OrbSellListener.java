package jp.yourname.charmorb.orb;

import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class OrbSellListener implements Listener {
    private final CharmOrbPlugin plugin;

    public OrbSellListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof OrbSellHolder holder) || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        event.setCancelled(true);
        if (event.getSlot() == 15) {
            player.closeInventory();
            return;
        }
        if (event.getSlot() != 11) {
            return;
        }
        if (plugin.economy() == null) {
            player.closeInventory();
            player.sendMessage(Text.c("&cVault Economy が見つかりません。"));
            return;
        }
        OrbSellGui sell = new OrbSellGui(plugin);
        double value = sell.value(player);
        if (value <= 0.0) {
            player.sendMessage(Text.c("&c売却できるオーブがありません。"));
            return;
        }
        ItemStack[] contents = player.getInventory().getStorageContents();
        for (int i = 0; i < contents.length; i++) {
            if (sell.itemValue(contents[i]) > 0.0) {
                contents[i] = null;
            }
        }
        player.getInventory().setStorageContents(contents);
        plugin.economy().depositPlayer(player, value);
        player.closeInventory();
        player.sendMessage(Text.c("&a売却しました: &f" + value));
    }
}

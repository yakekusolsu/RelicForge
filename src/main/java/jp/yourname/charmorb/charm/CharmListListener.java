package jp.yourname.charmorb.charm;

import jp.yourname.charmorb.CharmOrbPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class CharmListListener implements Listener {
    private final CharmOrbPlugin plugin;

    public CharmListListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof CharmListHolder holder) || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        event.setCancelled(true);
        CharmListGui gui = new CharmListGui(plugin);
        if (holder.view() == CharmListHolder.View.ELEMENTS) {
            int index = event.getSlot() - 8;
            CharmElement[] elements = CharmElement.values();
            if (index >= 0 && index < elements.length) {
                gui.openRarities(player, elements[index]);
            }
            return;
        }
        if (event.getSlot() == 26) {
            if (holder.view() == CharmListHolder.View.RARITIES) {
                gui.openElements(player);
            } else if (holder.view() == CharmListHolder.View.DETAIL && holder.element() != null) {
                gui.openRarities(player, holder.element());
            }
            return;
        }
        if (holder.view() == CharmListHolder.View.RARITIES && holder.element() != null) {
            int index = event.getSlot() - 9;
            CharmRarity[] rarities = CharmRarity.values();
            if (index >= 0 && index < rarities.length) {
                gui.openDetail(player, holder.element(), rarities[index]);
            }
        }
    }
}

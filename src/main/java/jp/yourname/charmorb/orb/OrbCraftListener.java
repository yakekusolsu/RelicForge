package jp.yourname.charmorb.orb;

import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public final class OrbCraftListener implements Listener {
    private final CharmOrbPlugin plugin;

    public OrbCraftListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack result = event.getCurrentItem();
        if (!ItemUtil.isOrbTarget(result) || plugin.orbEquipment().isOrbEquipment(result)) {
            return;
        }
        plugin.orbEquipment().initialize(result, plugin.orbEquipment().rollSlots());
        event.setCurrentItem(result);
        // TODO: Add equivalent handling for smithing table and anvil result transfer if desired.
    }
}

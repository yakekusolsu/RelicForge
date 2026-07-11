package jp.yourname.charmorb.orb;

import java.util.List;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.charm.CharmType;
import jp.yourname.charmorb.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class OrbSellGui {
    private final CharmOrbPlugin plugin;
    private final OrbItemFactory factory;

    public OrbSellGui(CharmOrbPlugin plugin) {
        this.plugin = plugin;
        this.factory = new OrbItemFactory(plugin);
    }

    public void open(Player player) {
        if (plugin.economy() == null) {
            player.sendMessage(Text.c("&cVault経済が見つからないため、買取は無効です。"));
            return;
        }
        double value = value(player);
        Inventory inv = Bukkit.createInventory(new OrbSellHolder(value), 27, Text.c("買取確認"));
        inv.setItem(11, OrbGui.icon(Material.EMERALD_BLOCK, "&a一括売却", List.of("&7査定額: &f" + value, "&eクリックで売却")));
        inv.setItem(15, OrbGui.icon(Material.BARRIER, "&cキャンセル", List.of("&7閉じます")));
        player.openInventory(inv);
    }

    public double value(Player player) {
        double total = 0.0;
        for (ItemStack item : player.getInventory().getStorageContents()) {
            total += itemValue(item);
        }
        return total;
    }

    public double itemValue(ItemStack item) {
        OrbType orb = factory.read(item);
        if (orb != null) {
            return plugin.settings().sellPrice(orb.rarity()) * item.getAmount();
        }
        CharmType charm = plugin.charms().factory().read(item);
        if (charm != null) {
            return charm.rarity().price() * item.getAmount();
        }
        if (!plugin.orbEquipment().isOrbEquipment(item)) {
            return 0.0;
        }
        double value = 0.0;
        for (OrbType type : plugin.orbEquipment().installed(item)) {
            value += plugin.settings().sellPrice(type.rarity());
        }
        value += plugin.orbEquipment().soulLevel(item) * 3.0;
        return value;
    }
}

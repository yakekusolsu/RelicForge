package jp.yourname.charmorb.orb;

import java.util.ArrayList;
import java.util.List;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.ItemUtil;
import jp.yourname.charmorb.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class OrbGui {
    public static final int[] SLOT_CELLS = {9, 10, 11, 12, 13, 14, 15, 16, 17};
    private final CharmOrbPlugin plugin;

    public OrbGui(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!plugin.orbEquipment().isOrbEquipment(item)) {
            player.sendMessage(Text.c("&cメインハンドにオーブスロット付き装備を持ってください。"));
            return;
        }
        Inventory inv = Bukkit.createInventory(new OrbGuiHolder(player.getUniqueId(), item), 36, Text.c("オーブソケット"));
        int slots = plugin.orbEquipment().slots(item);
        for (int i = 1; i <= OrbEquipmentManager.MAX_SLOTS; i++) {
            inv.setItem(SLOT_CELLS[i - 1], slotIcon(item, i, slots));
        }
        inv.setItem(4, infoIcon(item, slots));
        player.openInventory(inv);
    }

    private ItemStack slotIcon(ItemStack equipment, int slot, int slots) {
        if (slot > slots) {
            return icon(Material.GRAY_STAINED_GLASS_PANE, "&8ロック枠 " + slot, List.of("&7上限解放の巻き物で解放できます"));
        }
        OrbType type = plugin.orbEquipment().orbAt(equipment, slot);
        if (type == null) {
            return icon(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bオーブ枠 " + slot, List.of("&7カーソルのオーブをクリックで装着"));
        }
        ItemStack orb = new OrbItemFactory(plugin).create(type);
        ItemMeta meta = orb.getItemMeta();
        List<net.kyori.adventure.text.Component> lore = meta.lore() == null ? new ArrayList<>() : new ArrayList<>(meta.lore());
        lore.add(Text.c(""));
        lore.add(Text.c("&eクリックで取り外します。"));
        meta.lore(lore);
        orb.setItemMeta(meta);
        return orb;
    }

    private ItemStack infoIcon(ItemStack equipment, int slots) {
        return icon(Material.BOOK, "&bオーブソケット", List.of(
            "&7装備種別: &f" + ItemUtil.equipmentType(equipment),
            "&7スロット: &a" + slots + "&7/" + plugin.settings().orbUnlockMaxSlots(),
            "&7品質: &f" + plugin.orbEquipment().quality(slots),
            "&7基本上限: &f" + plugin.settings().orbMaxSlots() + " &8/ &7解放後上限: &f" + plugin.settings().orbUnlockMaxSlots(),
            "&8カーソルのオーブと空きスロットで装着",
            "&8下のインベントリのオーブをクリックで自動装着",
            "&8装着済みスロットをクリックで取り外し"
        ));
    }

    public static ItemStack icon(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Text.c(name));
        meta.lore(lore.stream().map(Text::c).toList());
        item.setItemMeta(meta);
        return item;
    }
}

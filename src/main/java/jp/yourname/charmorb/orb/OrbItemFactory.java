package jp.yourname.charmorb.orb;

import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.Text;
import org.bukkit.Material;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class OrbItemFactory {
    private final CharmOrbPlugin plugin;

    public OrbItemFactory(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack create(OrbType type) {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Text.c(OrbFlavor.coloredName(type)));
        meta.lore(OrbFlavor.itemLore(type).stream().map(Text::c).toList());
        meta.getPersistentDataContainer().set(plugin.keys().orbItem, PersistentDataType.BYTE, (byte) 1);
        meta.getPersistentDataContainer().set(plugin.keys().orbId, PersistentDataType.STRING, type.id());
        meta.getPersistentDataContainer().set(plugin.keys().orbRarity, PersistentDataType.STRING, type.rarity().name());
        item.setItemMeta(meta);
        return item;
    }

    public OrbType read(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }
        String id = item.getItemMeta().getPersistentDataContainer().get(plugin.keys().orbId, PersistentDataType.STRING);
        return OrbType.byId(id);
    }
}

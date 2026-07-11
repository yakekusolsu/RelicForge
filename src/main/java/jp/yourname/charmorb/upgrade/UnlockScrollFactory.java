package jp.yourname.charmorb.upgrade;

import java.util.List;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.Text;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class UnlockScrollFactory {
    public static final String ORB = "orb";
    public static final String CHARM = "charm";

    private final CharmOrbPlugin plugin;

    public UnlockScrollFactory(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack orbScroll() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Text.c("&6上限解放の巻き物"));
        meta.lore(List.of(
            Text.c("&8オーブスロット解放素材"),
            Text.c(""),
            Text.c("&a基本ステータス"),
            Text.c("&7  対象: &f武器・防具・ツール"),
            Text.c("&7  解放上限: &a最大9スロット"),
            Text.c(""),
            Text.c("&b使用方法"),
            Text.c("&7  &a+効果: &fメインハンド装備を右クリック &7/ &fスロット上限を1つ解放"),
            Text.c("&7  &c-効果: &fオフハンドから1枚消費")
        ));
        meta.getPersistentDataContainer().set(plugin.keys().unlockScroll, PersistentDataType.STRING, ORB);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack charmScroll() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Text.c("&d上限進化の巻き物"));
        meta.lore(List.of(
            Text.c("&8チャームスロット進化素材"),
            Text.c(""),
            Text.c("&a基本ステータス"),
            Text.c("&7  対象: &fプレイヤーのチャーム枠"),
            Text.c("&7  追加上限: &a最大20スロット"),
            Text.c(""),
            Text.c("&b使用方法"),
            Text.c("&7  &a+効果: &f手に持って右クリック &7/ &fチャーム追加枠を1つ解放"),
            Text.c("&7  &c-効果: &f使用すると1枚消費")
        ));
        meta.getPersistentDataContainer().set(plugin.keys().unlockScroll, PersistentDataType.STRING, CHARM);
        item.setItemMeta(meta);
        return item;
    }

    public String read(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }
        return item.getItemMeta().getPersistentDataContainer().get(plugin.keys().unlockScroll, PersistentDataType.STRING);
    }
}

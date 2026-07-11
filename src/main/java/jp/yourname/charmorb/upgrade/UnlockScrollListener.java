package jp.yourname.charmorb.upgrade;

import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.charm.CharmPlayerData;
import jp.yourname.charmorb.util.ItemUtil;
import jp.yourname.charmorb.util.Text;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public final class UnlockScrollListener implements Listener {
    private final CharmOrbPlugin plugin;
    private final UnlockScrollFactory factory;

    public UnlockScrollListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
        this.factory = new UnlockScrollFactory(plugin);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND || !isRightClick(event.getAction())) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack main = player.getInventory().getItemInMainHand();
        String heldType = factory.read(main);
        if (UnlockScrollFactory.CHARM.equals(heldType)) {
            event.setCancelled(true);
            useCharmScroll(player, main);
            return;
        }
        if (UnlockScrollFactory.ORB.equals(heldType)) {
            event.setCancelled(true);
            player.sendMessage(Text.c("&c上限解放の巻き物は、メインハンドに解放したい装備を持って使ってください。"));
            return;
        }
        if (ItemUtil.isOrbTarget(main)) {
            ItemStack scroll = player.getInventory().getItemInOffHand();
            if (!UnlockScrollFactory.ORB.equals(factory.read(scroll))) {
                return;
            }
            event.setCancelled(true);
            useOrbScroll(player, scroll);
        }
    }

    private void useOrbScroll(Player player, ItemStack scroll) {
        ItemStack target = player.getInventory().getItemInMainHand();
        if (!ItemUtil.isOrbTarget(target)) {
            player.sendMessage(Text.c("&cメインハンドに解放したい武器・防具・ツールを持ってください。"));
            return;
        }
        if (!plugin.orbEquipment().isOrbEquipment(target)) {
            plugin.orbEquipment().initialize(target, 0);
        }
        if (!plugin.orbEquipment().unlockSlot(target)) {
            player.sendMessage(Text.c("&cこの装備はこれ以上オーブスロットを解放できません。"));
            return;
        }
        consume(player, scroll);
        player.sendMessage(Text.c("&6上限解放の巻き物&aを使用しました。 &7現在スロット: &a" + plugin.orbEquipment().slots(target)
            + "&7/" + plugin.settings().orbUnlockMaxSlots()));
    }

    private void useCharmScroll(Player player, ItemStack scroll) {
        CharmPlayerData data = plugin.charms().data(player);
        if (!plugin.charms().unlockExtraSlot(player)) {
            player.sendMessage(Text.c("&cチャーム追加枠はこれ以上解放できません。"));
            return;
        }
        consume(player, scroll);
        player.sendMessage(Text.c("&d上限進化の巻き物&aを使用しました。 &7チャーム枠: &a" + data.unlockedSlots()
            + "&7/" + CharmPlayerData.MAX_SLOTS));
    }

    private void consume(Player player, ItemStack scroll) {
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (scroll.getAmount() > 1) {
            scroll.setAmount(scroll.getAmount() - 1);
            return;
        }
        if (player.getInventory().getItemInMainHand() == scroll) {
            player.getInventory().setItemInMainHand(null);
        } else if (player.getInventory().getItemInOffHand() == scroll) {
            player.getInventory().setItemInOffHand(null);
        } else {
            scroll.setAmount(0);
        }
    }

    private boolean isRightClick(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }
}

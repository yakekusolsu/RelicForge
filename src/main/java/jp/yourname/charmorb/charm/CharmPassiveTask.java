package jp.yourname.charmorb.charm;

import jp.yourname.charmorb.CharmOrbPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public final class CharmPassiveTask extends BukkitRunnable {
    private final CharmOrbPlugin plugin;
    private static final String SOURCE = "charm";

    public CharmPassiveTask(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            plugin.passiveEffects().begin(player, SOURCE);
            for (ItemStack item : plugin.charms().equipped(player)) {
                CharmType type = plugin.charms().factory().read(item);
                int level = plugin.charms().factory().level(item);
                if (type == null) continue;
                switch (type.element()) {
                    case FIRE, THUNDER -> potion(player, PotionEffectType.STRENGTH, Math.max(0, level / 4));
                    case ICE, EARTH -> potion(player, PotionEffectType.RESISTANCE, Math.max(0, level / 4));
                    case WATER, NATURE -> potion(player, PotionEffectType.REGENERATION, 0);
                    case WIND -> potion(player, PotionEffectType.SPEED, Math.max(0, level / 4));
                    case LIGHT -> plugin.passiveEffects().apply(player, SOURCE, PotionEffectType.NIGHT_VISION, 220, 0);
                    case DARK, VOID -> potion(player, PotionEffectType.HASTE, 0);
                }
                if (type.rarity() == CharmRarity.CURSE) {
                    potion(player, PotionEffectType.WEAKNESS, 0);
                }
            }
            plugin.passiveEffects().end(player);
        }
    }

    private void potion(Player player, PotionEffectType type, int amplifier) {
        plugin.passiveEffects().apply(player, SOURCE, type, 80, amplifier);
    }
}

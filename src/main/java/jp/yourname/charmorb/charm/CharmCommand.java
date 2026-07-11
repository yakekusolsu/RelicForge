package jp.yourname.charmorb.charm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.upgrade.UnlockScrollFactory;
import jp.yourname.charmorb.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public final class CharmCommand implements CommandExecutor, TabCompleter {
    private final CharmOrbPlugin plugin;
    private final UnlockScrollFactory scrollFactory;

    public CharmCommand(CharmOrbPlugin plugin) {
        this.plugin = plugin;
        this.scrollFactory = new UnlockScrollFactory(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("プレイヤーのみ実行できます。");
            return true;
        }
        String sub = args.length == 0 ? "slot" : args[0].toLowerCase(Locale.ROOT);
        if (!allowed(player, sub)) {
            return true;
        }
        return switch (sub) {
            case "help" -> { help(player); yield true; }
            case "slot" -> { new CharmSlotGui(plugin).open(player); yield true; }
            case "give" -> give(player, args);
            case "give-scroll" -> giveScroll(player);
            case "give-test" -> giveTest(player);
            case "list" -> { new CharmListGui(plugin).openElements(player); yield true; }
            case "dex" -> { new CharmDexGui(plugin).open(player); yield true; }
            case "sell" -> { new jp.yourname.charmorb.orb.OrbSellGui(plugin).open(player); yield true; }
            default -> { help(player); yield true; }
        };
    }

    private void help(Player player) {
        player.sendMessage(Text.c("&b/charm slot &7- 7スロットGUI"));
        player.sendMessage(Text.c("&b/charm give <element_rarity> &7例 fire_common"));
        player.sendMessage(Text.c("&b/charm give-scroll|give-test|list|dex|sell"));
    }

    private boolean give(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Text.c("&c使い方: /charm give <element_rarity>"));
            return true;
        }
        CharmType type = CharmType.byId(args[1]);
        if (type == null) {
            player.sendMessage(Text.c("&c不明なチャームIDです。"));
            return true;
        }
        player.getInventory().addItem(plugin.charms().factory().create(type));
        plugin.charms().data(player).discovered().add(type.id());
        player.sendMessage(Text.c("&a付与しました: " + CharmFlavor.coloredName(type)));
        return true;
    }

    private boolean giveScroll(Player player) {
        player.getInventory().addItem(scrollFactory.charmScroll()).values().forEach(drop -> player.getWorld().dropItemNaturally(player.getLocation(), drop));
        player.sendMessage(Text.c("&a上限進化の巻き物を付与しました。"));
        return true;
    }

    private boolean giveTest(Player player) {
        CharmType type = CharmType.of(CharmElement.FIRE, CharmRarity.COMMON);
        player.getInventory().addItem(plugin.charms().factory().create(type));
        plugin.charms().data(player).discovered().add(type.id());
        player.sendMessage(Text.c("&aテスト用チャームを付与しました: " + CharmFlavor.coloredName(type)));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return filter(permitted(sender, List.of("help", "slot", "give", "give-scroll", "give-test", "list", "dex", "sell")), args[0]);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            List<String> ids = new ArrayList<>();
            for (CharmElement element : CharmElement.values()) {
                for (CharmRarity rarity : CharmRarity.values()) ids.add(element.name().toLowerCase() + "_" + rarity.name().toLowerCase());
            }
            return filter(ids, args[1]);
        }
        return List.of();
    }

    private List<String> filter(List<String> values, String prefix) {
        String p = prefix.toLowerCase(Locale.ROOT);
        return values.stream().filter(v -> v.startsWith(p)).toList();
    }

    private boolean allowed(Player player, String sub) {
        String permission = permission(sub);
        if (player.hasPermission(permission) || player.hasPermission("charmorb.admin")) {
            return true;
        }
        player.sendMessage(Text.c("&c権限がありません。"));
        return false;
    }

    private String permission(String sub) {
        return switch (sub) {
            case "help" -> "charmorb.command.charm.help";
            case "slot" -> "charmorb.command.charm.slot";
            case "give" -> "charmorb.command.charm.give";
            case "give-scroll" -> "charmorb.command.charm.give-scroll";
            case "give-test" -> "charmorb.command.charm.give-test";
            case "list" -> "charmorb.command.charm.list";
            case "dex" -> "charmorb.command.charm.dex";
            case "sell" -> "charmorb.command.charm.sell";
            default -> "charmorb.command.charm.help";
        };
    }

    private List<String> permitted(CommandSender sender, List<String> values) {
        if (!(sender instanceof Player player) || player.hasPermission("charmorb.admin")) {
            return values;
        }
        return values.stream().filter(sub -> player.hasPermission(permission(sub))).toList();
    }
}

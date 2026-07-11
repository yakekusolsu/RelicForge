package jp.yourname.charmorb.orb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.upgrade.UnlockScrollFactory;
import jp.yourname.charmorb.util.ItemUtil;
import jp.yourname.charmorb.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public final class OrbCommand implements CommandExecutor, TabCompleter {
    private final CharmOrbPlugin plugin;
    private final OrbItemFactory itemFactory;
    private final UnlockScrollFactory scrollFactory;
    private final Random random = new Random();

    public OrbCommand(CharmOrbPlugin plugin) {
        this.plugin = plugin;
        this.itemFactory = new OrbItemFactory(plugin);
        this.scrollFactory = new UnlockScrollFactory(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("sellorb")) {
            return sell(sender);
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage("プレイヤーのみ実行できます。");
            return true;
        }
        String sub = args.length == 0 ? "gui" : args[0].toLowerCase(Locale.ROOT);
        if (!allowed(player, sub)) {
            return true;
        }
        return switch (sub) {
            case "help" -> help(player);
            case "give" -> give(player, args);
            case "give-random" -> giveRandom(player);
            case "give-rarity" -> giveRarity(player, args);
            case "give-scroll" -> giveScroll(player);
            case "give-all" -> giveAll(player);
            case "list" -> list(player);
            case "roll" -> roll(player);
            case "setslots" -> setSlots(player, args);
            case "check" -> check(player);
            case "insert" -> insert(player, args);
            case "remove" -> remove(player, args);
            case "gui" -> { new OrbGui(plugin).open(player); yield true; }
            case "effects" -> effects(player);
            case "sell" -> sell(player);
            case "reload" -> reload(player);
            default -> { player.sendMessage(Text.c("&c不明なサブコマンドです。/orb help")); yield true; }
        };
    }

    private boolean help(Player player) {
        player.sendMessage(Text.c("&b/orb give <id> &7- オーブ入手"));
        player.sendMessage(Text.c("&b/orb roll|setslots <0-" + plugin.settings().orbUnlockMaxSlots() + ">|check|gui"));
        player.sendMessage(Text.c("&b/orb insert <slot> <id>|remove <slot>|sell"));
        player.sendMessage(Text.c("&b/orb list|give-random|give-rarity <rarity>|give-all|give-scroll"));
        return true;
    }

    private boolean give(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Text.c("&c使い方: /orb give <id>"));
            return true;
        }
        OrbType type = OrbType.byId(args[1]);
        if (type == null) {
            player.sendMessage(Text.c("&c不明なオーブIDです: " + args[1]));
            return true;
        }
        player.getInventory().addItem(itemFactory.create(type)).values().forEach(drop -> player.getWorld().dropItemNaturally(player.getLocation(), drop));
        player.sendMessage(Text.c("&a付与しました: " + OrbFlavor.coloredName(type)));
        return true;
    }

    private boolean giveRandom(Player player) {
        OrbType[] values = OrbType.values();
        player.getInventory().addItem(itemFactory.create(values[random.nextInt(values.length)]));
        return true;
    }

    private boolean giveRarity(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Text.c("&c使い方: /orb give-rarity <rarity>"));
            player.sendMessage(Text.c("&7希少度: common, uncommon, rare, epic, legendary, mythic, unique, curse"));
            return true;
        }
        OrbRarity rarity = OrbRarity.parse(args[1]);
        if (rarity == null) {
            player.sendMessage(Text.c("&c不明な希少度です。"));
            return true;
        }
        List<OrbType> list = OrbType.byRarity(rarity);
        player.getInventory().addItem(itemFactory.create(list.get(random.nextInt(list.size()))));
        return true;
    }

    private boolean giveAll(Player player) {
        for (OrbType type : OrbType.values()) {
            player.getInventory().addItem(itemFactory.create(type)).values().forEach(drop -> player.getWorld().dropItemNaturally(player.getLocation(), drop));
        }
        player.sendMessage(Text.c("&a全オーブを付与しました。"));
        return true;
    }

    private boolean giveScroll(Player player) {
        player.getInventory().addItem(scrollFactory.orbScroll()).values().forEach(drop -> player.getWorld().dropItemNaturally(player.getLocation(), drop));
        player.sendMessage(Text.c("&a上限解放の巻き物を付与しました。"));
        return true;
    }

    private boolean list(Player player) {
        for (OrbRarity rarity : OrbRarity.values()) {
            String ids = String.join(", ", OrbType.byRarity(rarity).stream().map(OrbType::id).toList());
            player.sendMessage(Text.c(rarity.color() + rarityName(rarity) + "&7: &f" + ids));
        }
        return true;
    }

    private boolean roll(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ItemUtil.isOrbTarget(item)) {
            player.sendMessage(Text.c("&cメインハンドに対象装備を持ってください。"));
            return true;
        }
        int slots = plugin.orbEquipment().rollSlots();
        plugin.orbEquipment().initialize(item, slots);
        player.sendMessage(Text.c("&aスロット数: &f" + slots));
        return true;
    }

    private boolean setSlots(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Text.c("&c使い方: /orb setslots <0-" + plugin.settings().orbUnlockMaxSlots() + ">"));
            return true;
        }
        int slots = parseInt(args[1], -1);
        if (slots < 0 || slots > plugin.settings().orbUnlockMaxSlots()) {
            player.sendMessage(Text.c("&c0〜" + plugin.settings().orbUnlockMaxSlots() + "を指定してください。"));
            return true;
        }
        if (!plugin.orbEquipment().initialize(player.getInventory().getItemInMainHand(), slots)) {
            player.sendMessage(Text.c("&c対象装備をメインハンドに持ってください。"));
            return true;
        }
        player.sendMessage(Text.c("&aスロットを " + slots + " に設定しました。"));
        return true;
    }

    private boolean check(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!plugin.orbEquipment().isOrbEquipment(item)) {
            player.sendMessage(Text.c("&cオーブ装備ではありません。"));
            return true;
        }
        player.sendMessage(Text.c("&bスロット: &f" + plugin.orbEquipment().slots(item)));
        player.sendMessage(Text.c("&b品質: &f" + plugin.orbEquipment().quality(plugin.orbEquipment().slots(item))));
        for (int i = 1; i <= plugin.orbEquipment().slots(item); i++) {
            OrbType type = plugin.orbEquipment().orbAt(item, i);
            player.sendMessage(Text.c("&7[" + i + "] &f" + (type == null ? "空き" : type.displayName())));
        }
        player.sendMessage(Text.c("&4魂レベル: &f" + plugin.orbEquipment().soulLevel(item) + " &7討伐数 " + plugin.orbEquipment().soulKills(item)));
        int version = item.getItemMeta().getPersistentDataContainer().getOrDefault(plugin.keys().orbVersion, PersistentDataType.INTEGER, 0);
        player.sendMessage(Text.c("&8内部データ: orb_system=true, orb_version=" + version));
        return true;
    }

    private boolean insert(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage(Text.c("&c使い方: /orb insert <slot> <id>"));
            return true;
        }
        int slot = parseInt(args[1], -1);
        OrbType type = OrbType.byId(args[2]);
        if (!plugin.orbEquipment().insert(player.getInventory().getItemInMainHand(), slot, type)) {
            player.sendMessage(Text.c("&c挿入できませんでした。スロット/ID/空き状態を確認してください。"));
            return true;
        }
        player.sendMessage(Text.c("&a挿入しました: " + OrbFlavor.coloredName(type)));
        return true;
    }

    private boolean remove(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Text.c("&c使い方: /orb remove <slot>"));
            return true;
        }
        OrbType type = plugin.orbEquipment().remove(player.getInventory().getItemInMainHand(), parseInt(args[1], -1));
        if (type == null) {
            player.sendMessage(Text.c("&cそのスロットにはオーブがありません。"));
            return true;
        }
        player.getInventory().addItem(itemFactory.create(type));
        player.sendMessage(Text.c("&a取り外しました: " + OrbFlavor.coloredName(type)));
        return true;
    }

    private boolean effects(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        player.sendMessage(Text.c("&bメインハンドで発動中の効果:"));
        for (OrbType type : plugin.orbEquipment().installed(item)) {
            player.sendMessage(Text.c("- " + OrbFlavor.coloredName(type) + "&7: &f" + type.description()));
        }
        return true;
    }

    private boolean sell(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("プレイヤーのみ実行できます。");
            return true;
        }
        new OrbSellGui(plugin).open(player);
        return true;
    }

    private boolean reload(Player player) {
        plugin.reloadPluginConfig();
        player.sendMessage(Text.c("&aconfig.yml と messages.yml をリロードしました。"));
        return true;
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
            case "help" -> "charmorb.command.orb.help";
            case "give" -> "charmorb.command.orb.give";
            case "give-random" -> "charmorb.command.orb.give-random";
            case "give-rarity" -> "charmorb.command.orb.give-rarity";
            case "give-scroll" -> "charmorb.command.orb.give-scroll";
            case "give-all" -> "charmorb.command.orb.give-all";
            case "list" -> "charmorb.command.orb.list";
            case "roll" -> "charmorb.command.orb.roll";
            case "setslots" -> "charmorb.command.orb.setslots";
            case "check" -> "charmorb.command.orb.check";
            case "insert" -> "charmorb.command.orb.insert";
            case "remove" -> "charmorb.command.orb.remove";
            case "effects" -> "charmorb.command.orb.effects";
            case "sell" -> "charmorb.command.orb.sell";
            case "reload" -> "charmorb.command.orb.reload";
            case "gui" -> "charmorb.command.orb.gui";
            default -> "charmorb.command.orb.help";
        };
    }

    private int parseInt(String raw, int fallback) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return filter(permitted(sender, List.of("help", "give", "give-random", "give-rarity", "give-scroll", "give-all", "list", "roll", "setslots", "check", "insert", "remove", "gui", "effects", "sell", "reload")), args[0]);
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("insert"))) {
            return filter(Arrays.stream(OrbType.values()).map(OrbType::id).toList(), args[1]);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give-rarity")) {
            return filter(Arrays.stream(OrbRarity.values()).map(r -> r.name().toLowerCase(Locale.ROOT)).toList(), args[1]);
        }
        return List.of();
    }

    private List<String> filter(List<String> values, String prefix) {
        String p = prefix.toLowerCase(Locale.ROOT);
        List<String> out = new ArrayList<>();
        for (String value : values) {
            if (value.toLowerCase(Locale.ROOT).startsWith(p)) {
                out.add(value);
            }
        }
        return out;
    }

    private List<String> permitted(CommandSender sender, List<String> values) {
        if (!(sender instanceof Player player) || player.hasPermission("charmorb.admin")) {
            return values;
        }
        return values.stream().filter(sub -> player.hasPermission(permission(sub))).toList();
    }

    private String rarityName(OrbRarity rarity) {
        return switch (rarity) {
            case COMMON -> "一般";
            case UNCOMMON -> "上等";
            case RARE -> "希少";
            case EPIC -> "秘宝";
            case LEGENDARY -> "伝説";
            case MYTHIC -> "神話";
            case UNIQUE -> "唯一";
            case CURSE -> "呪い";
        };
    }
}

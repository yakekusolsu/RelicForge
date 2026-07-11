package jp.yourname.charmorb;

import jp.yourname.charmorb.charm.CharmCommand;
import jp.yourname.charmorb.charm.CharmEffectListener;
import jp.yourname.charmorb.charm.CharmListListener;
import jp.yourname.charmorb.charm.CharmManager;
import jp.yourname.charmorb.charm.CharmPassiveTask;
import jp.yourname.charmorb.config.PluginConfig;
import jp.yourname.charmorb.drop.DropListener;
import jp.yourname.charmorb.orb.OrbCommand;
import jp.yourname.charmorb.orb.OrbCraftListener;
import jp.yourname.charmorb.orb.OrbEffectListener;
import jp.yourname.charmorb.orb.OrbEquipmentManager;
import jp.yourname.charmorb.orb.OrbGuiListener;
import jp.yourname.charmorb.orb.OrbPassiveTask;
import jp.yourname.charmorb.orb.OrbSellListener;
import jp.yourname.charmorb.orb.OrbSkillListener;
import jp.yourname.charmorb.upgrade.UnlockScrollListener;
import jp.yourname.charmorb.util.CooldownManager;
import jp.yourname.charmorb.util.PassiveEffectService;
import jp.yourname.charmorb.vault.VaultHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class CharmOrbPlugin extends JavaPlugin {
    private static CharmOrbPlugin instance;
    private Keys keys;
    private PluginConfig pluginConfig;
    private FileConfiguration messages;
    private VaultHook vaultHook;
    private OrbEquipmentManager orbEquipmentManager;
    private CharmManager charmManager;
    private final CooldownManager cooldowns = new CooldownManager();
    private final PassiveEffectService passiveEffects = new PassiveEffectService();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveResource("messages.yml", false);
        keys = new Keys(this);
        pluginConfig = new PluginConfig(this);
        loadMessages();
        vaultHook = new VaultHook(this);
        vaultHook.setup();
        orbEquipmentManager = new OrbEquipmentManager(this);
        charmManager = new CharmManager(this);

        OrbCommand orbCommand = new OrbCommand(this);
        register("orb", orbCommand);
        register("sellorb", orbCommand);
        register("charm", new CharmCommand(this));

        getServer().getPluginManager().registerEvents(new OrbGuiListener(this), this);
        getServer().getPluginManager().registerEvents(new OrbEffectListener(this), this);
        getServer().getPluginManager().registerEvents(new OrbCraftListener(this), this);
        getServer().getPluginManager().registerEvents(new OrbSellListener(this), this);
        getServer().getPluginManager().registerEvents(new OrbSkillListener(this), this);
        getServer().getPluginManager().registerEvents(new CharmEffectListener(this), this);
        getServer().getPluginManager().registerEvents(new CharmListListener(this), this);
        getServer().getPluginManager().registerEvents(new DropListener(this), this);
        getServer().getPluginManager().registerEvents(new UnlockScrollListener(this), this);
        new OrbPassiveTask(this).runTaskTimer(this, 20L, 100L);
        new CharmPassiveTask(this).runTaskTimer(this, 40L, 60L);
        getLogger().info("CharmOrbPlugin enabled. Vault economy: " + vaultHook.enabled());
    }

    private void register(String name, Object executor) {
        PluginCommand command = getCommand(name);
        if (command == null) {
            getLogger().warning("Command missing from plugin.yml: " + name);
            return;
        }
        command.setExecutor((org.bukkit.command.CommandExecutor) executor);
        if (executor instanceof org.bukkit.command.TabCompleter completer) {
            command.setTabCompleter(completer);
        }
    }

    public void reloadPluginConfig() {
        pluginConfig.reload();
        loadMessages();
    }

    private void loadMessages() {
        messages = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));
    }

    public static CharmOrbPlugin getInstance() {
        return instance;
    }

    public Keys keys() {
        return keys;
    }

    public Economy economy() {
        return vaultHook == null ? null : vaultHook.economy();
    }

    public PluginConfig settings() {
        return pluginConfig;
    }

    public FileConfiguration messages() {
        return messages;
    }

    public OrbEquipmentManager orbEquipment() {
        return orbEquipmentManager;
    }

    public CharmManager charms() {
        return charmManager;
    }

    public CooldownManager cooldowns() {
        return cooldowns;
    }

    public PassiveEffectService passiveEffects() {
        return passiveEffects;
    }
}

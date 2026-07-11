package jp.yourname.charmorb.vault;

import jp.yourname.charmorb.CharmOrbPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultHook {
    private final CharmOrbPlugin plugin;
    private Economy economy;

    public VaultHook(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().warning("Vault was not found. Selling is disabled, but the plugin will continue.");
            economy = null;
            return;
        }
        RegisteredServiceProvider<Economy> provider = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        economy = provider == null ? null : provider.getProvider();
        if (economy == null) {
            plugin.getLogger().warning("Vault was found, but no Economy provider was registered. Selling is disabled.");
        }
    }

    public boolean enabled() {
        return economy != null;
    }

    public Economy economy() {
        return economy;
    }
}

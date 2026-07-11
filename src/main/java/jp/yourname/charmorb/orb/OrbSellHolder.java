package jp.yourname.charmorb.orb;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class OrbSellHolder implements InventoryHolder {
    private final double value;

    public OrbSellHolder(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}

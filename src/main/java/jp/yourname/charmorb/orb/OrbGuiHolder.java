package jp.yourname.charmorb.orb;

import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class OrbGuiHolder implements InventoryHolder {
    private final UUID playerId;
    private final ItemStack equipmentSnapshot;

    public OrbGuiHolder(UUID playerId, ItemStack equipmentSnapshot) {
        this.playerId = playerId;
        this.equipmentSnapshot = equipmentSnapshot == null ? null : equipmentSnapshot.clone();
    }

    public UUID playerId() {
        return playerId;
    }

    public boolean matches(ItemStack current) {
        return equipmentSnapshot != null && current != null && current.isSimilar(equipmentSnapshot);
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}

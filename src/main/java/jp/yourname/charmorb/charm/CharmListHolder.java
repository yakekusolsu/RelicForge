package jp.yourname.charmorb.charm;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class CharmListHolder implements InventoryHolder {
    public enum View {
        ELEMENTS,
        RARITIES,
        DETAIL
    }

    private final View view;
    private final CharmElement element;
    private final CharmRarity rarity;

    public CharmListHolder(View view, CharmElement element, CharmRarity rarity) {
        this.view = view;
        this.element = element;
        this.rarity = rarity;
    }

    public View view() {
        return view;
    }

    public CharmElement element() {
        return element;
    }

    public CharmRarity rarity() {
        return rarity;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}

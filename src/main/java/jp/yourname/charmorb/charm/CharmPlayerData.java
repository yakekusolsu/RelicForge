package jp.yourname.charmorb.charm;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.inventory.ItemStack;

public final class CharmPlayerData {
    public static final int BASE_SLOTS = 7;
    public static final int MAX_SLOTS = 27;
    private final ItemStack[] slots = new ItemStack[MAX_SLOTS];
    private final Set<String> discovered = new HashSet<>();
    private int extraSlots;

    public ItemStack[] slots() {
        return slots;
    }

    public Set<String> discovered() {
        return discovered;
    }

    public int unlockedSlots() {
        return Math.min(MAX_SLOTS, BASE_SLOTS + extraSlots);
    }

    public int extraSlots() {
        return extraSlots;
    }

    public void setExtraSlots(int extraSlots) {
        this.extraSlots = Math.max(0, Math.min(MAX_SLOTS - BASE_SLOTS, extraSlots));
    }

    public boolean unlockExtraSlot(int maxExtraSlots) {
        int cappedMax = Math.min(MAX_SLOTS - BASE_SLOTS, Math.max(0, maxExtraSlots));
        if (extraSlots >= cappedMax) {
            return false;
        }
        extraSlots++;
        return true;
    }
}

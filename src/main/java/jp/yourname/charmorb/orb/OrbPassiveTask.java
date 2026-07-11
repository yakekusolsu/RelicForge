package jp.yourname.charmorb.orb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jp.yourname.charmorb.CharmOrbPlugin;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public final class OrbPassiveTask extends BukkitRunnable {
    private final CharmOrbPlugin plugin;
    private final Random random = new Random();
    private static final String SOURCE = "orb";

    public OrbPassiveTask(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            plugin.passiveEffects().begin(player, SOURCE);
            List<OrbType> orbs = activeOrbs(player);
            applyMaxHealth(player, orbs);
            effects(player, orbs);
            plugin.passiveEffects().end(player);
            for (ItemStack item : equippedItems(player)) {
                plugin.orbEquipment().decaySoul(item);
            }
        }
    }

    private void applyMaxHealth(Player player, List<OrbType> orbs) {
        AttributeInstance maxHealth = player.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealth == null) {
            return;
        }
        double max = 20.0;
        int maxHealthUp = countPlus(orbs, OrbPositiveEffectType.MAX_HEALTH_UP);
        int giantHealth = countPlus(orbs, OrbPositiveEffectType.GIANT_HEALTH);
        int maxHealthDown = countMinus(orbs, OrbNegativeEffectType.MAX_HEALTH_DOWN);
        if (orbs.contains(OrbType.VITALITY)) max += 2.0;
        if (orbs.contains(OrbType.GIANT)) max += 10.0;
        max += maxHealthUp * 2.0;
        max += giantHealth * 6.0;
        max -= maxHealthDown * 2.0;
        if (orbs.contains(OrbType.FRAILTY)) max *= 0.70;
        if (countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_FRAGILE_BODY) > 0) max *= 0.85;
        if (orbs.contains(OrbType.CROWN)) max *= 1.10;
        maxHealth.setBaseValue(Math.max(2.0, max));
        if (player.getHealth() > maxHealth.getValue()) {
            player.setHealth(maxHealth.getValue());
        }
    }

    private void effects(Player player, List<OrbType> orbs) {
        int speedAmp = -1;
        int hasteAmp = -1;
        if (orbs.contains(OrbType.LIGHTWEIGHT) || countPlus(orbs, OrbPositiveEffectType.SPEED_UP) > 0) speedAmp = Math.max(speedAmp, 0);
        if ((orbs.contains(OrbType.GALE) || countPlus(orbs, OrbPositiveEffectType.SPRINT_SPEED) > 0) && player.isSprinting()) speedAmp = Math.max(speedAmp, 1);
        if (countPlus(orbs, OrbPositiveEffectType.AIR_CONTROL) > 0 && !player.isOnGround()) speedAmp = Math.max(speedAmp, 0);
        if (countPlus(orbs, OrbPositiveEffectType.WEATHER_POWER) > 0 && player.getWorld().hasStorm()) speedAmp = Math.max(speedAmp, 0);
        if (orbs.contains(OrbType.MINING) || orbs.contains(OrbType.CREATION) || countPlus(orbs, OrbPositiveEffectType.MINING_SPEED) > 0 || countPlus(orbs, OrbPositiveEffectType.WOODCUTTING_SPEED) > 0 || countPlus(orbs, OrbPositiveEffectType.DIGGING_SPEED) > 0 || countPlus(orbs, OrbPositiveEffectType.BUILDER_SPEED) > 0) hasteAmp = Math.max(hasteAmp, 0);
        if (countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_TOOL_FRENZY) > 0) hasteAmp = Math.max(hasteAmp, 1);
        if (orbs.contains(OrbType.GODSPEED)) {
            speedAmp = Math.max(speedAmp, 1);
            hasteAmp = Math.max(hasteAmp, 1);
        }
        if (countMinus(orbs, OrbNegativeEffectType.SPEED_DOWN) > 0 || countMinus(orbs, OrbNegativeEffectType.ARMOR_WEIGHT) > 0 || countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_SLOW_BODY) > 0) potion(player, PotionEffectType.SLOWNESS, countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_SLOW_BODY) > 0 ? 1 : 0);
        if (speedAmp >= 0) potion(player, PotionEffectType.SPEED, speedAmp);
        if (hasteAmp >= 0) potion(player, PotionEffectType.HASTE, hasteAmp);
        if (countMinus(orbs, OrbNegativeEffectType.MINING_DOWN) > 0) potion(player, PotionEffectType.MINING_FATIGUE, 0);
        if (orbs.contains(OrbType.SWIMMING) || countPlus(orbs, OrbPositiveEffectType.WATER_MOVE) > 0) {
            if (player.isInWater()) potion(player, PotionEffectType.DOLPHINS_GRACE, 0);
        }
        if (orbs.contains(OrbType.DEEP_SEA) || orbs.contains(OrbType.SWIMMING) || countPlus(orbs, OrbPositiveEffectType.WATER_BREATHING) > 0) potion(player, PotionEffectType.WATER_BREATHING, 0);
        if (orbs.contains(OrbType.TORCHLIGHT) || orbs.contains(OrbType.HOLY_LIGHT) || countPlus(orbs, OrbPositiveEffectType.NIGHT_VISION) > 0) potion(player, PotionEffectType.NIGHT_VISION, 0);
        if (orbs.contains(OrbType.HOLY_LIGHT)) player.removePotionEffect(PotionEffectType.DARKNESS);
        if (orbs.contains(OrbType.SKY) || countPlus(orbs, OrbPositiveEffectType.JUMP_UP) > 0) potion(player, PotionEffectType.JUMP_BOOST, orbs.contains(OrbType.SKY) ? 1 : 0);
        if ((orbs.contains(OrbType.REGENERATION) || countPlus(orbs, OrbPositiveEffectType.REGEN_OUT_COMBAT) > 0) && player.getNoDamageTicks() <= 0) potion(player, PotionEffectType.REGENERATION, 0);
        if (countPlus(orbs, OrbPositiveEffectType.POISON_CLEANSE) > 0) {
            player.removePotionEffect(PotionEffectType.POISON);
            player.removePotionEffect(PotionEffectType.WITHER);
        }
        if (countPlus(orbs, OrbPositiveEffectType.FIRE_CLEANSE) > 0) {
            player.setFireTicks(Math.min(player.getFireTicks(), 20));
        }
        if (orbs.contains(OrbType.FRAILTY)) potion(player, PotionEffectType.SPEED, 1);
        if (orbs.contains(OrbType.BINDING)) potion(player, PotionEffectType.SLOWNESS, 0);
        if (orbs.contains(OrbType.RUNAWAY)) potion(player, PotionEffectType.HASTE, 1);
        AttributeInstance maxHealth = player.getAttribute(Attribute.MAX_HEALTH);
        if (orbs.contains(OrbType.AWAKENING) && maxHealth != null && player.getHealth() <= maxHealth.getValue() * 0.30) {
            potion(player, PotionEffectType.SPEED, 0);
            potion(player, PotionEffectType.RESISTANCE, 0);
        }
        if (countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_FORBIDDEN_SPEED) > 0) {
            potion(player, PotionEffectType.SPEED, 1);
            potion(player, PotionEffectType.HASTE, 1);
        }
        if (countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_OVERDRIVE) > 0 && maxHealth != null && player.getHealth() <= maxHealth.getValue() * 0.30) {
            potion(player, PotionEffectType.SPEED, 1);
            potion(player, PotionEffectType.STRENGTH, 1);
            potion(player, PotionEffectType.RESISTANCE, 0);
        }
        if (countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_LIFE_DRAIN) > 0 && player.getHealth() > 1.0) {
            player.damage(0.5);
        }
        if (countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_WATER_FEAR) > 0 && player.isInWater() && player.getHealth() > 1.0) {
            player.damage(0.5);
            potion(player, PotionEffectType.WEAKNESS, 0);
        }
        if (countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_DAY_BURN) > 0 && player.getWorld().getTime() < 12300) {
            potion(player, PotionEffectType.WEAKNESS, 0);
            if (random.nextDouble() < 0.20) player.setFireTicks(Math.max(player.getFireTicks(), 60));
        }
        if (countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_MADNESS) > 0 && random.nextDouble() < 0.10) {
            potion(player, PotionEffectType.NAUSEA, 0);
            potion(player, PotionEffectType.SLOWNESS, 0);
        }
        if (orbs.contains(OrbType.WORLD_TREE)) {
            for (Entity entity : player.getNearbyEntities(8, 8, 8)) {
                if (entity instanceof Player ally && ally.getAttribute(Attribute.MAX_HEALTH) != null && ally.getHealth() < ally.getAttribute(Attribute.MAX_HEALTH).getValue()) {
                    ally.addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.REGENERATION, 80, 0, true, false, true));
                }
            }
        }
        if (orbs.contains(OrbType.RULER)) {
            for (Entity entity : player.getNearbyEntities(6, 6, 6)) {
                if (entity instanceof Monster monster) {
                    monster.addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.WEAKNESS, 80, 0, true, false, true));
                    monster.addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.SLOWNESS, 80, 0, true, false, true));
                }
            }
        }
        // TODO: fate/chaos/star_core/space_time should receive active cooldown-driven abilities instead of passive placeholders.
        if ((orbs.contains(OrbType.FATE) || orbs.contains(OrbType.CHAOS)) && random.nextDouble() < 0.02) {
            potion(player, random.nextBoolean() ? PotionEffectType.LUCK : PotionEffectType.WEAKNESS, 0);
        }
    }

    private void potion(Player player, PotionEffectType type, int amplifier) {
        plugin.passiveEffects().apply(player, SOURCE, type, 100, amplifier);
    }

    private List<OrbType> activeOrbs(Player player) {
        List<OrbType> out = new ArrayList<>();
        for (ItemStack item : equippedItems(player)) {
            out.addAll(plugin.orbEquipment().installed(item));
        }
        return out;
    }

    private int countPlus(List<OrbType> orbs, OrbPositiveEffectType effect) {
        int count = 0;
        for (OrbType orb : orbs) {
            if (OrbFlavor.generatedPlusEffects(orb).contains(effect)) count++;
        }
        return count;
    }

    private int countMinus(List<OrbType> orbs, OrbNegativeEffectType effect) {
        int count = 0;
        for (OrbType orb : orbs) {
            if (OrbFlavor.generatedMinusEffects(orb).contains(effect)) count++;
        }
        return count;
    }

    private int countCursePlus(List<OrbType> orbs, OrbCursePositiveEffectType effect) {
        int count = 0;
        for (OrbType orb : orbs) {
            if (OrbFlavor.generatedCursePlusEffects(orb).contains(effect)) count++;
        }
        return count;
    }

    private int countCurseMinus(List<OrbType> orbs, OrbCurseNegativeEffectType effect) {
        int count = 0;
        for (OrbType orb : orbs) {
            if (OrbFlavor.generatedCurseMinusEffects(orb).contains(effect)) count++;
        }
        return count;
    }

    private List<ItemStack> equippedItems(Player player) {
        List<ItemStack> items = new ArrayList<>();
        items.add(player.getInventory().getItemInMainHand());
        items.add(player.getInventory().getItemInOffHand());
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            items.add(armor);
        }
        return items;
    }
}

package jp.yourname.charmorb.orb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jp.yourname.charmorb.CharmOrbPlugin;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class OrbEffectListener implements Listener {
    private final CharmOrbPlugin plugin;
    private final Random random = new Random();

    public OrbEffectListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Player attacker = attackingPlayer(event);
        if (attacker != null) {
            Player player = attacker;
            List<OrbType> orbs = activeOrbs(player);
            double multiplier = attackMultiplier(player, event.getEntity(), orbs, event.getDamager() instanceof Projectile);
            event.setDamage(event.getDamage() * multiplier);
            applyOnHit(player, event.getEntity(), event.getFinalDamage(), orbs);
        }
        if (event.getEntity() instanceof Player player) {
            List<OrbType> orbs = activeOrbs(player);
            double multiplier = defenseMultiplier(event, orbs);
            event.setDamage(event.getDamage() * multiplier);
            if (orbs.contains(OrbType.COUNTER) && event.getDamager() instanceof LivingEntity counterTarget && random.nextDouble() < plugin.settings().counterChance()) {
                counterTarget.damage(2.0, player);
            }
            if (countPlus(orbs, OrbPositiveEffectType.THORNS_DAMAGE) > 0 && event.getDamager() instanceof LivingEntity living) {
                living.damage(1.0 + countPlus(orbs, OrbPositiveEffectType.THORNS_DAMAGE), player);
            }
            if (countPlus(orbs, OrbPositiveEffectType.DODGE_CHANCE) > 0 && random.nextDouble() < 0.04 * countPlus(orbs, OrbPositiveEffectType.DODGE_CHANCE)) {
                event.setCancelled(true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, true, false, true));
                return;
            }
            if (countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_VOID_STEP) > 0 && random.nextDouble() < 0.05) {
                event.setCancelled(true);
                player.teleport(player.getLocation().add(player.getLocation().getDirection().normalize().multiply(-3)));
                return;
            }
            preventLethal(player, event, orbs);
        }
    }

    @EventHandler
    public void onAnyDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            List<OrbType> orbs = activeOrbs(player);
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && (orbs.contains(OrbType.SKY) || countPlus(orbs, OrbPositiveEffectType.FALL_CANCEL) > 0 || countPlus(orbs, OrbPositiveEffectType.FALL_GUARD) > 0)) {
                event.setCancelled(true);
            }
            if ((event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA) && countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_FIRE_FEAR) > 0) {
                event.setDamage(event.getDamage() * 1.50);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }
        int soulCount = count(killer, OrbType.SOUL_EATER);
        if (soulCount > 0) {
            for (ItemStack item : equippedItems(killer)) {
                if (plugin.orbEquipment().has(item, OrbType.SOUL_EATER)) {
                    plugin.orbEquipment().addSoulKill(item, soulCount);
                }
            }
        }
        if (count(killer, OrbType.FORTUNE) > 0 && random.nextDouble() < 0.05) {
            event.getDrops().add(new ItemStack(Material.EXPERIENCE_BOTTLE));
        }
        List<OrbType> orbs = activeOrbs(killer);
        if ((countPlus(orbs, OrbPositiveEffectType.KILL_HEAL) > 0 || countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_PREDATOR) > 0) && random.nextDouble() < 0.75) {
            heal(killer, countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_PREDATOR) > 0 ? 4.0 : 2.0);
            killer.setFoodLevel(Math.min(20, killer.getFoodLevel() + 1));
        }
        if (countPlus(orbs, OrbPositiveEffectType.LUCK_UP) > 0 && random.nextDouble() < 0.05 * countPlus(orbs, OrbPositiveEffectType.LUCK_UP)) {
            event.getDrops().add(new ItemStack(Material.EXPERIENCE_BOTTLE));
        }
    }

    @EventHandler
    public void onRegain(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player player && event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED && activeOrbs(player).contains(OrbType.CURSED_BLOOD)) {
            event.setCancelled(true);
            return;
        }
        if (event.getEntity() instanceof Player player) {
            List<OrbType> orbs = activeOrbs(player);
            if (countMinus(orbs, OrbNegativeEffectType.HEAL_DOWN) > 0 || countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_HEALING_BLOCK) > 0 || countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_LIGHT_REJECT) > 0) {
                event.setAmount(event.getAmount() * 0.60);
            } else if (countPlus(orbs, OrbPositiveEffectType.HEAL_BOOST) > 0) {
                event.setAmount(event.getAmount() * (1.0 + 0.15 * countPlus(orbs, OrbPositiveEffectType.HEAL_BOOST)));
            }
        }
    }

    @EventHandler
    public void onDurability(PlayerItemDamageEvent event) {
        List<OrbType> orbs = activeOrbs(event.getPlayer());
        if (orbs.contains(OrbType.INFINITY) && random.nextDouble() < 0.25) {
            event.setCancelled(true);
        } else if (orbs.contains(OrbType.DURABILITY) && random.nextDouble() < 0.10) {
            event.setCancelled(true);
        } else if (countPlus(orbs, OrbPositiveEffectType.INFINITY_DURABILITY) > 0 && random.nextDouble() < 0.18) {
            event.setCancelled(true);
        } else if (countPlus(orbs, OrbPositiveEffectType.DURABILITY_SAVE) > 0 && random.nextDouble() < 0.08 * countPlus(orbs, OrbPositiveEffectType.DURABILITY_SAVE)) {
            event.setCancelled(true);
        } else if (countMinus(orbs, OrbNegativeEffectType.DURABILITY_LOSS) > 0 || countMinus(orbs, OrbNegativeEffectType.FRAGILE_TOOL) > 0 || countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_TOOL_DECAY) > 0 || countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_ARMOR_DECAY) > 0) {
            event.setDamage(event.getDamage() + 1);
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        List<OrbType> orbs = activeOrbs(player);
        if (event.getFoodLevel() < player.getFoodLevel() && orbs.contains(OrbType.SATIETY) && random.nextDouble() < 0.10) {
            event.setCancelled(true);
        }
        if (event.getFoodLevel() < player.getFoodLevel() && countPlus(orbs, OrbPositiveEffectType.HUNGER_SAVE) > 0 && random.nextDouble() < 0.10 * countPlus(orbs, OrbPositiveEffectType.HUNGER_SAVE)) {
            event.setCancelled(true);
        }
        if (event.getFoodLevel() < player.getFoodLevel() && orbs.contains(OrbType.RUNAWAY)) {
            event.setFoodLevel(Math.max(0, event.getFoodLevel() - 1));
        }
        if (event.getFoodLevel() < player.getFoodLevel() && (countMinus(orbs, OrbNegativeEffectType.HUNGER_UP) > 0 || countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_HUNGER_COLLAPSE) > 0)) {
            event.setFoodLevel(Math.max(0, event.getFoodLevel() - 1));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        List<OrbType> orbs = activeOrbs(event.getPlayer());
        if (orbs.contains(OrbType.PROSPECTING) && event.getBlock().getType().name().endsWith("_ORE")) {
            event.getPlayer().sendActionBar(jp.yourname.charmorb.util.Text.c("&e鉱脈の気配を感じる..."));
        }
        if (countPlus(orbs, OrbPositiveEffectType.ORE_SENSE) > 0 && event.getBlock().getType().name().endsWith("_ORE")) {
            event.getPlayer().sendActionBar(jp.yourname.charmorb.util.Text.c("&e鉱脈が共鳴している..."));
        }
        if ((orbs.contains(OrbType.HARVEST) || countPlus(orbs, OrbPositiveEffectType.DOUBLE_DROP) > 0 || countPlus(orbs, OrbPositiveEffectType.ORE_BONUS) > 0) && random.nextDouble() < 0.08) {
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(event.getBlock().getType()));
        }
    }

    private double attackMultiplier(Player player, Entity target, List<OrbType> orbs, boolean ranged) {
        double m = 1.0;
        if (orbs.contains(OrbType.POWER)) m += plugin.settings().powerDamage();
        if (orbs.contains(OrbType.BERSERKER)) m += plugin.settings().berserkerDamage();
        if (orbs.contains(OrbType.SACRIFICE)) m += plugin.settings().sacrificeDamage();
        if (orbs.contains(OrbType.BLOOD_RAGE) && player.getHealth() <= maxHealth(player) / 2.0) m += 0.25;
        if (orbs.contains(OrbType.AWAKENING) && player.getHealth() <= maxHealth(player) * 0.30) m += 0.20;
        if (orbs.contains(OrbType.ABYSS)) m += Math.max(0.0, (1.0 - (player.getHealth() / maxHealth(player))) * 0.35);
        if (orbs.contains(OrbType.CROWN)) m += plugin.settings().crownBonus();
        if (orbs.contains(OrbType.HUNT_GOD) && target instanceof Monster) m += 0.20;
        if (orbs.contains(OrbType.KING) && (target instanceof Wither || target instanceof EnderDragon)) m += 0.15;
        if (orbs.contains(OrbType.DRAGON_SOUL) && target instanceof EnderDragon) m += 0.50;
        if (orbs.contains(OrbType.VOID) && (target instanceof Enderman || target instanceof Endermite || target instanceof Shulker)) m += 0.30;
        if (orbs.contains(OrbType.HOLY_LIGHT) && target instanceof LivingEntity living && isUndead(living)) m += 0.20;
        if (orbs.contains(OrbType.SOUL_EATER)) m += soulAttackBonus(player);
        m += countPlus(orbs, OrbPositiveEffectType.DAMAGE_UP) * 0.05;
        m += countPlus(orbs, OrbPositiveEffectType.CRIT_DAMAGE_UP) * 0.04;
        if (!ranged) m += countPlus(orbs, OrbPositiveEffectType.MELEE_DAMAGE_UP) * 0.06;
        if (ranged) m += countPlus(orbs, OrbPositiveEffectType.RANGED_DAMAGE_UP) * 0.06;
        if (target instanceof Monster) m += countPlus(orbs, OrbPositiveEffectType.MOB_DAMAGE_UP) * 0.08;
        if (target instanceof Wither || target instanceof EnderDragon) m += countPlus(orbs, OrbPositiveEffectType.BOSS_DAMAGE_UP) * 0.12;
        if (target instanceof Enderman || target instanceof Endermite || target instanceof Shulker) m += countPlus(orbs, OrbPositiveEffectType.ENDER_DAMAGE_UP) * 0.12;
        if (target instanceof LivingEntity living && isUndead(living)) m += countPlus(orbs, OrbPositiveEffectType.UNDEAD_DAMAGE_UP) * 0.12;
        if (target instanceof EnderDragon) m += countPlus(orbs, OrbPositiveEffectType.DRAGON_DAMAGE_UP) * 0.25;
        if (target instanceof LivingEntity living && living.getHealth() <= Math.max(4.0, maxHealth(living) * 0.30)) m += countPlus(orbs, OrbPositiveEffectType.EXECUTE_DAMAGE) * 0.15;
        if (countPlus(orbs, OrbPositiveEffectType.CRIT_RATE_UP) > 0 && random.nextDouble() < 0.06 * countPlus(orbs, OrbPositiveEffectType.CRIT_RATE_UP)) m += 0.35;
        if (countPlus(orbs, OrbPositiveEffectType.LOW_HP_DAMAGE) > 0) m += Math.max(0.0, (1.0 - (player.getHealth() / maxHealth(player))) * 0.20 * countPlus(orbs, OrbPositiveEffectType.LOW_HP_DAMAGE));
        m -= countMinus(orbs, OrbNegativeEffectType.DAMAGE_DOWN) * 0.08;
        if (ranged) m -= countMinus(orbs, OrbNegativeEffectType.ACCURACY_DOWN) * 0.08;
        m -= countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_WEAK_ATTACK) * 0.15;
        m += countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_BERSERK) * 0.25;
        m += countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_BLOOD_POWER) * Math.max(0.0, (1.0 - (player.getHealth() / maxHealth(player))) * 0.60);
        if (target instanceof Wither || target instanceof EnderDragon) m += countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_KING_SLAUGHTER) * 0.30;
        if (target instanceof LivingEntity living && living.getHealth() <= Math.max(4.0, maxHealth(living) * 0.30)) m += countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_EXECUTIONER) * 0.25;
        long time = player.getWorld().getTime();
        if (orbs.contains(OrbType.DEEP_ABYSS)) m += (time > 13000 && time < 23000) ? 0.35 : -0.15;
        return Math.max(0.1, m);
    }

    private void applyOnHit(Player player, Entity target, double damage, List<OrbType> orbs) {
        if (!(target instanceof LivingEntity living)) {
            return;
        }
        if (orbs.contains(OrbType.LIFE_STEAL)) heal(player, damage * plugin.settings().lifeStealRate());
        if (orbs.contains(OrbType.CURSED_BLOOD)) heal(player, damage * plugin.settings().cursedBloodRate());
        if (countPlus(orbs, OrbPositiveEffectType.LIFE_STEAL) > 0) heal(player, damage * 0.02 * countPlus(orbs, OrbPositiveEffectType.LIFE_STEAL));
        if (countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_LIFE_STEAL) > 0) heal(player, damage * 0.08 * countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_LIFE_STEAL));
        if (orbs.contains(OrbType.SACRIFICE)) player.damage(plugin.settings().sacrificeSelfDamage());
        if (orbs.contains(OrbType.SCORCHING)) living.setFireTicks(Math.max(living.getFireTicks(), 80));
        if (orbs.contains(OrbType.FREEZING)) living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 1));
        if (orbs.contains(OrbType.VENOM)) living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
        if (countPlus(orbs, OrbPositiveEffectType.FIRE_ATTACK) > 0) living.setFireTicks(Math.max(living.getFireTicks(), 80));
        if (countPlus(orbs, OrbPositiveEffectType.ICE_ATTACK) > 0) living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 0));
        if (countPlus(orbs, OrbPositiveEffectType.POISON_ATTACK) > 0 || countPlus(orbs, OrbPositiveEffectType.NATURE_ATTACK) > 0) living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0));
        if (countPlus(orbs, OrbPositiveEffectType.WITHER_ATTACK) > 0) living.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 0));
        if (countPlus(orbs, OrbPositiveEffectType.DARK_ATTACK) > 0) living.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 0));
        if (countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_BLACK_FLAME) > 0) {
            living.setFireTicks(Math.max(living.getFireTicks(), 120));
            living.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 0));
        }
        if ((countPlus(orbs, OrbPositiveEffectType.THUNDER_ATTACK) > 0 || countPlus(orbs, OrbPositiveEffectType.EARTH_ATTACK) > 0) && random.nextDouble() < 0.08) living.getWorld().strikeLightningEffect(living.getLocation());
        if (orbs.contains(OrbType.THUNDER_GOD) && random.nextDouble() < plugin.settings().thunderGodChance()) living.getWorld().strikeLightningEffect(living.getLocation());
        if (orbs.contains(OrbType.CRUSHING) && random.nextDouble() < plugin.settings().crushingChance()) {
            for (Entity nearby : living.getNearbyEntities(3, 3, 3)) {
                if (nearby instanceof LivingEntity other && other != player) other.damage(2.0, player);
            }
        }
        if (orbs.contains(OrbType.STORM)) living.setVelocity(living.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(0.8).setY(0.25));
        if (countPlus(orbs, OrbPositiveEffectType.KNOCKBACK_UP) > 0 || countPlus(orbs, OrbPositiveEffectType.WIND_ATTACK) > 0) living.setVelocity(living.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(0.55).setY(0.18));
        if (orbs.contains(OrbType.HEAVY_STRIKE) && player.getFallDistance() > 0.0F) living.damage(2.0, player);
        if (countMinus(orbs, OrbNegativeEffectType.SELF_DAMAGE_CHANCE) > 0 && random.nextDouble() < 0.08) player.damage(1.0);
        if (countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_SOUL_PRICE) > 0 && random.nextDouble() < 0.10) player.damage(1.5);
    }

    private double defenseMultiplier(EntityDamageEvent event, List<OrbType> orbs) {
        double m = 1.0;
        if (orbs.contains(OrbType.GUARDIAN)) m -= plugin.settings().guardianReduction();
        if (orbs.contains(OrbType.DIVINE_GUARDIAN)) m -= plugin.settings().divineGuardianReduction();
        if (orbs.contains(OrbType.BINDING)) m -= 0.30;
        if (orbs.contains(OrbType.CROWN)) m -= plugin.settings().crownBonus();
        if (orbs.contains(OrbType.BERSERKER)) m += plugin.settings().berserkerDefensePenalty();
        if (orbs.contains(OrbType.AWAKENING)) m -= 0.10;
        m -= countPlus(orbs, OrbPositiveEffectType.DEFENSE_UP) * 0.05;
        m -= countPlus(orbs, OrbPositiveEffectType.SHIELD_CHANCE) > 0 && random.nextDouble() < 0.08 * countPlus(orbs, OrbPositiveEffectType.SHIELD_CHANCE) ? 0.35 : 0.0;
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) m -= countPlus(orbs, OrbPositiveEffectType.PROJECTILE_GUARD) * 0.10;
        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) m -= countPlus(orbs, OrbPositiveEffectType.EXPLOSION_GUARD) * 0.12;
        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA) m -= countPlus(orbs, OrbPositiveEffectType.FIRE_GUARD) * 0.12;
        m += countMinus(orbs, OrbNegativeEffectType.DEFENSE_DOWN) * 0.10;
        m += countCurseMinus(orbs, OrbCurseNegativeEffectType.CURSE_FRAGILE_BODY) * 0.25;
        m -= countCursePlus(orbs, OrbCursePositiveEffectType.CURSE_DARK_ARMOR) * 0.18;
        return Math.max(0.1, m);
    }

    private boolean isUndead(LivingEntity entity) {
        return switch (entity.getType()) {
            case ZOMBIE, HUSK, DROWNED, ZOMBIE_VILLAGER, SKELETON, STRAY, WITHER_SKELETON, WITHER, PHANTOM, ZOMBIFIED_PIGLIN -> true;
            default -> false;
        };
    }

    private void preventLethal(Player player, EntityDamageEvent event, List<OrbType> orbs) {
        if (event.getFinalDamage() < player.getHealth()) {
            return;
        }
        if (orbs.contains(OrbType.PHOENIX) && plugin.cooldowns().ready(player.getUniqueId(), "phoenix")) {
            event.setCancelled(true);
            plugin.cooldowns().set(player.getUniqueId(), "phoenix", plugin.settings().phoenixCooldownMillis());
            player.setHealth(maxHealth(player));
            player.setFireTicks(0);
            return;
        }
        if (orbs.contains(OrbType.UNYIELDING) && plugin.cooldowns().ready(player.getUniqueId(), "unyielding")) {
            event.setCancelled(true);
            plugin.cooldowns().set(player.getUniqueId(), "unyielding", plugin.settings().unyieldingCooldownMillis());
            player.setHealth(1.0);
        }
    }

    public List<OrbType> activeOrbs(Player player) {
        List<OrbType> out = new ArrayList<>();
        for (ItemStack item : equippedItems(player)) {
            out.addAll(plugin.orbEquipment().installed(item));
        }
        return out;
    }

    private Player attackingPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            return player;
        }
        if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player player) {
            return player;
        }
        return null;
    }

    private int count(Player player, OrbType type) {
        int count = 0;
        for (OrbType orb : activeOrbs(player)) {
            if (orb == type) count++;
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

    private double soulAttackBonus(Player player) {
        double bonus = 0.0;
        for (ItemStack item : equippedItems(player)) {
            if (plugin.orbEquipment().has(item, OrbType.SOUL_EATER)) {
                bonus += plugin.orbEquipment().soulLevel(item) * plugin.settings().soulAttackPerLevel();
            }
        }
        return bonus;
    }

    private void heal(Player player, double amount) {
        player.setHealth(Math.min(maxHealth(player), player.getHealth() + amount));
    }

    private double maxHealth(Player player) {
        return player.getAttribute(Attribute.MAX_HEALTH) == null ? 20.0 : player.getAttribute(Attribute.MAX_HEALTH).getValue();
    }

    private double maxHealth(LivingEntity entity) {
        return entity.getAttribute(Attribute.MAX_HEALTH) == null ? 20.0 : entity.getAttribute(Attribute.MAX_HEALTH).getValue();
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
}

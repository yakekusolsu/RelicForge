package jp.yourname.charmorb.orb;

import java.util.ArrayList;
import java.util.List;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.util.Text;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class OrbSkillListener implements Listener {
    private final CharmOrbPlugin plugin;

    public OrbSkillListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking()) {
            return;
        }
        List<SkillView> skills = activeSkills(player);
        if (skills.isEmpty()) {
            return;
        }
        event.setCancelled(true);
        int current = player.getPersistentDataContainer().getOrDefault(plugin.keys().orbSkillIndex, PersistentDataType.INTEGER, 0);
        int direction = event.getNewSlot() > event.getPreviousSlot() ? 1 : -1;
        int next = Math.floorMod(current + direction, skills.size());
        player.getPersistentDataContainer().set(plugin.keys().orbSkillIndex, PersistentDataType.INTEGER, next);
        showActionbar(player, skills, next);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (!event.getPlayer().isSneaking() || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        Player player = event.getPlayer();
        List<SkillView> skills = activeSkills(player);
        if (skills.isEmpty()) {
            return;
        }
        event.setCancelled(true);
        int index = Math.floorMod(player.getPersistentDataContainer().getOrDefault(plugin.keys().orbSkillIndex, PersistentDataType.INTEGER, 0), skills.size());
        SkillView skill = skills.get(index);
        String cooldownKey = "orb_skill:" + skill.id();
        if (!plugin.cooldowns().ready(player.getUniqueId(), cooldownKey)) {
            player.sendActionBar(Text.c(skill.prefix() + " &f" + skill.name() + " &7| CT: &c待機中"));
            return;
        }
        plugin.cooldowns().set(player.getUniqueId(), cooldownKey, skill.cooldownSeconds() * 1000L);
        activate(player, skill);
        player.sendActionBar(Text.c(skill.prefix() + " &f" + skill.name() + " &7| CT: &aOK"));
        player.sendMessage(Text.c(skill.prefix() + " &f" + skill.name() + " &aを発動しました。 &7" + skill.description()));
        if (!skill.cost().isEmpty()) {
            player.sendMessage(Text.c("&c代償: &f" + skill.cost()));
        }
    }

    private List<SkillView> activeSkills(Player player) {
        List<SkillView> out = new ArrayList<>();
        for (ItemStack item : equippedItems(player)) {
            for (OrbType orb : plugin.orbEquipment().installed(item)) {
                for (OrbSkillType skill : OrbFlavor.generatedSkills(orb)) {
                    out.add(new SkillView(skill.id(), skill.displayName(), skill.description(), "", skill.cooldownSeconds(), "&b[Orb Skill]", skill, null));
                }
                for (OrbCurseSkillType skill : OrbFlavor.generatedCurseSkills(orb)) {
                    out.add(new SkillView(skill.id(), skill.displayName(), skill.description(), skill.cost(), skill.cooldownSeconds(), "&4[Curse Skill]", null, skill));
                }
            }
        }
        return out;
    }

    private void activate(Player player, SkillView skill) {
        if (skill.normal() != null) {
            activateNormal(player, skill.normal());
        }
        if (skill.curse() != null) {
            activateCurse(player, skill.curse());
        }
    }

    private void activateNormal(Player player, OrbSkillType skill) {
        switch (skill) {
            case FLAME_SLASH -> {
                damageNearby(player, 5.0, 6.0, true);
                player.getWorld().spawnParticle(org.bukkit.Particle.FLAME, player.getLocation().add(0, 1, 0), 40, 2, 0.5, 2, 0.02);
            }
            case ICE_BIND -> {
                for (LivingEntity living : nearbyEnemies(player, 5.0)) {
                    living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 120, 2, true, true, true));
                }
            }
            case THUNDER_STRIKE -> {
                for (LivingEntity living : nearbyEnemies(player, 6.0)) {
                    living.getWorld().strikeLightningEffect(living.getLocation());
                    living.damage(5.0, player);
                    break;
                }
            }
            case MACE_QUAKE -> damageNearby(player, 6.0, 4.0, false);
            case VOID_CUT -> damageNearby(player, 8.0, 3.0, false);
            case HOLY_JUDGEMENT -> damageNearby(player, 7.0, 6.0, false);
            case DRAGON_BREAK -> damageNearby(player, 10.0, 4.0, false);
            case ARROW_RAIN, TRIDENT_STORM, STARFALL -> damageNearby(player, 5.0, 8.0, false);
            case BERSERK_DRIVE -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 200, 1, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 0, true, false, true));
            }
            case GUARD_BARRIER, IRON_WALL, VOID_BARRIER, DIMENSION_ANCHOR -> player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 200, 1, true, true, true));
            case PHOENIX_GUARD -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 2, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 0, true, true, true));
            }
            case REFLECT_SHELL -> player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 200, 0, true, true, true));
            case CLEANSE_FIELD -> cleanse(player);
            case WORLD_TREE_BLESSING -> healAllies(player, 6.0, 6.0);
            case BLINK -> player.teleport(player.getLocation().add(player.getLocation().getDirection().normalize().multiply(6)));
            case DASH, ESCAPE -> player.setVelocity(player.getLocation().getDirection().normalize().multiply(skill == OrbSkillType.ESCAPE ? -1.4 : 1.8).setY(0.2));
            case SKY_LEAP -> player.setVelocity(player.getVelocity().setY(1.1));
            case WIND_STEP, WATER_DASH -> player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 240, 2, true, true, true));
            case VEIN_MINE, TREE_FELLER, EARTH_DIG, HARVEST_WAVE, BUILDER_MODE -> player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 240, 2, true, true, true));
            case REPAIR_BURST -> repairHeldItem(player, 40);
            case ORE_SCAN -> player.sendActionBar(Text.c("&e周囲の鉱石に意識を澄ませた。"));
            case TIME_SKIP -> player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 200, 0, true, true, true));
            case FATE_SHUFFLE, GENESIS_BOOST -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 200, 0, true, true, true));
            }
            case CHAOS_BURST -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 160, 1, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0, true, true, true));
                damageNearby(player, 4.0, 5.0, false);
            }
            case KING_ORDER -> {
                for (LivingEntity living : nearbyEnemies(player, 6.0)) {
                    living.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 160, 1, true, true, true));
                    living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 160, 0, true, true, true));
                }
            }
        }
    }

    private void activateCurse(Player player, OrbCurseSkillType skill) {
        switch (skill) {
            case BLOOD_BURST -> {
                damageNearby(player, 9.0, 5.0, false);
                player.damage(3.0);
            }
            case DARK_DASH -> {
                player.setFoodLevel(Math.max(0, player.getFoodLevel() - 4));
                player.setVelocity(player.getLocation().getDirection().normalize().multiply(2.2).setY(0.15));
                damageNearby(player, 4.0, 3.0, false);
            }
            case BLACK_FLAME -> {
                damageNearby(player, 6.0, 7.0, true);
                player.setFireTicks(Math.max(player.getFireTicks(), 80));
            }
            case SOUL_DEVOUR -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 260, 1, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 260, 1, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 160, 0, true, false, true));
            }
            case FORBIDDEN_HEAL -> {
                heal(player, 12.0);
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 200, 0, true, false, true));
            }
            case ABYSS_GATE -> {
                for (LivingEntity living : nearbyEnemies(player, 8.0)) {
                    living.teleport(player.getLocation());
                    living.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 140, 1, true, true, true));
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 0, true, false, true));
            }
            case MADNESS, CONTRACT -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 240, 2, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 240, 1, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 0, true, false, true));
            }
            case DEATH_MARK -> damageNearby(player, 12.0, 4.0, false);
            case GRAVITY_CRUSH -> {
                damageNearby(player, 10.0, 6.0, false);
                player.damage(2.0);
            }
            case VOID_ESCAPE -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 120, 3, true, true, true));
                player.teleport(player.getLocation().add(player.getLocation().getDirection().normalize().multiply(5)));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 0, true, false, true));
            }
            case PLAGUE_ZONE -> {
                for (LivingEntity living : nearbyEnemies(player, 7.0)) {
                    living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 160, 1, true, true, true));
                    living.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 160, 0, true, true, true));
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0, true, false, true));
            }
            case CURSED_REPAIR -> {
                repairHeldItem(player, 80);
                player.damage(2.0);
            }
            case OVERLOAD_MINE -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 300, 3, true, true, true));
                damageHeldItem(player, 8);
            }
        }
    }

    private List<LivingEntity> nearbyEnemies(Player player, double radius) {
        List<LivingEntity> result = new ArrayList<>();
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof Monster monster) {
                result.add(monster);
            }
        }
        return result;
    }

    private void damageNearby(Player player, double damage, double radius, boolean fire) {
        for (LivingEntity living : nearbyEnemies(player, radius)) {
            living.damage(damage, player);
            if (fire) {
                living.setFireTicks(Math.max(living.getFireTicks(), 100));
            }
        }
    }

    private void healAllies(Player player, double radius, double amount) {
        heal(player, amount);
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof Player ally) {
                heal(ally, amount);
            }
        }
    }

    private void heal(Player player, double amount) {
        double max = player.getAttribute(Attribute.MAX_HEALTH) == null ? 20.0 : player.getAttribute(Attribute.MAX_HEALTH).getValue();
        player.setHealth(Math.min(max, player.getHealth() + amount));
    }

    private void cleanse(Player player) {
        player.removePotionEffect(PotionEffectType.POISON);
        player.removePotionEffect(PotionEffectType.WITHER);
        player.removePotionEffect(PotionEffectType.WEAKNESS);
        player.removePotionEffect(PotionEffectType.SLOWNESS);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.setFireTicks(0);
    }

    private void repairHeldItem(Player player, int amount) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable damageable)) {
            return;
        }
        damageable.setDamage(Math.max(0, damageable.getDamage() - amount));
        item.setItemMeta(meta);
    }

    private void damageHeldItem(Player player, int amount) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable damageable)) {
            return;
        }
        damageable.setDamage(damageable.getDamage() + amount);
        item.setItemMeta(meta);
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

    private void showActionbar(Player player, List<SkillView> skills, int index) {
        SkillView skill = skills.get(index);
        player.sendActionBar(Text.c(skill.prefix() + " &7[" + (index + 1) + "/" + skills.size() + "] &f" + skill.name() + " &7| CT: &aOK"));
    }

    private record SkillView(String id, String name, String description, String cost, int cooldownSeconds, String prefix, OrbSkillType normal, OrbCurseSkillType curse) {
    }
}

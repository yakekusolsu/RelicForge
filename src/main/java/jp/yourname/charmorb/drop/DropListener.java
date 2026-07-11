package jp.yourname.charmorb.drop;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import jp.yourname.charmorb.CharmOrbPlugin;
import jp.yourname.charmorb.charm.CharmElement;
import jp.yourname.charmorb.charm.CharmItemFactory;
import jp.yourname.charmorb.charm.CharmRarity;
import jp.yourname.charmorb.charm.CharmType;
import jp.yourname.charmorb.orb.OrbItemFactory;
import jp.yourname.charmorb.orb.OrbType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public final class DropListener implements Listener {
    private static final String[] EQUIPMENT_MATERIALS = {
        "IRON_SWORD", "IRON_AXE", "MACE", "BOW", "CROSSBOW", "TRIDENT",
        "IRON_HELMET", "IRON_CHESTPLATE", "IRON_LEGGINGS", "IRON_BOOTS",
        "IRON_PICKAXE", "IRON_SHOVEL", "IRON_HOE", "SHEARS", "BRUSH", "FISHING_ROD"
    };
    private final CharmOrbPlugin plugin;
    private final OrbItemFactory orbFactory;
    private final CharmItemFactory charmFactory;
    private final Random random = new Random();

    public DropListener(CharmOrbPlugin plugin) {
        this.plugin = plugin;
        this.orbFactory = new OrbItemFactory(plugin);
        this.charmFactory = new CharmItemFactory(plugin);
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null || !plugin.getConfig().getBoolean("drops.mob.enabled", true)) {
            return;
        }
        DropRates rates = mobRates(event);
        if (random.nextDouble() < rates.orbChance()) {
            event.getDrops().add(orbFactory.create(randomOrb()));
        }
        if (random.nextDouble() < rates.charmChance()) {
            event.getDrops().add(charmFactory.create(randomCharm()));
        }
        if (random.nextDouble() < rates.equipmentChance()) {
            event.getDrops().add(randomSlottedEquipment());
        }
    }

    @EventHandler
    public void onOreBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!plugin.getConfig().getBoolean("drops.ore.enabled", true) || !isOre(block.getType())) {
            return;
        }
        if (random.nextDouble() < plugin.getConfig().getDouble("drops.ore.orb-chance", 0.01)) {
            block.getWorld().dropItemNaturally(block.getLocation(), orbFactory.create(randomOrb()));
        }
        if (random.nextDouble() < plugin.getConfig().getDouble("drops.ore.charm-chance", 0.003)) {
            block.getWorld().dropItemNaturally(block.getLocation(), charmFactory.create(randomCharm()));
        }
    }

    private DropRates mobRates(EntityDeathEvent event) {
        String mythicId = mythicMobId(event);
        if (mythicId != null && plugin.getConfig().getBoolean("drops.mob.mythicmobs.enabled", true)) {
            String path = "drops.mob.mythicmobs.mobs." + mythicId;
            ConfigurationSection section = plugin.getConfig().getConfigurationSection(path);
            if (section != null) {
                return new DropRates(section.getDouble("orb-chance", plugin.getConfig().getDouble("drops.mob.mythicmobs.default-orb-chance", 0.08)),
                    section.getDouble("charm-chance", plugin.getConfig().getDouble("drops.mob.mythicmobs.default-charm-chance", 0.03)),
                    section.getDouble("equipment-chance", plugin.getConfig().getDouble("drops.mob.mythicmobs.default-equipment-chance", 0.06)));
            }
            return new DropRates(plugin.getConfig().getDouble("drops.mob.mythicmobs.default-orb-chance", 0.08),
                plugin.getConfig().getDouble("drops.mob.mythicmobs.default-charm-chance", 0.03),
                plugin.getConfig().getDouble("drops.mob.mythicmobs.default-equipment-chance", 0.06));
        }
        String entityPath = "drops.mob.entities." + event.getEntityType().name().toLowerCase(Locale.ROOT);
        ConfigurationSection section = plugin.getConfig().getConfigurationSection(entityPath);
        if (section != null) {
            return new DropRates(section.getDouble("orb-chance", plugin.getConfig().getDouble("drops.mob.orb-chance", 0.03)),
                section.getDouble("charm-chance", plugin.getConfig().getDouble("drops.mob.charm-chance", 0.01)),
                section.getDouble("equipment-chance", plugin.getConfig().getDouble("drops.mob.equipment-chance", 0.015)));
        }
        return new DropRates(plugin.getConfig().getDouble("drops.mob.orb-chance", 0.03),
            plugin.getConfig().getDouble("drops.mob.charm-chance", 0.01),
            plugin.getConfig().getDouble("drops.mob.equipment-chance", 0.015));
    }

    private String mythicMobId(EntityDeathEvent event) {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("MythicMobs")) {
            return null;
        }
        try {
            Class<?> mythicBukkit = Class.forName("io.lumine.mythic.bukkit.MythicBukkit");
            Object inst = mythicBukkit.getMethod("inst").invoke(null);
            Object mobManager = inst.getClass().getMethod("getMobManager").invoke(inst);
            Method getActiveMob = mobManager.getClass().getMethod("getActiveMob", java.util.UUID.class);
            Object activeMob = getActiveMob.invoke(mobManager, event.getEntity().getUniqueId());
            if (activeMob instanceof Optional<?> optional) {
                if (optional.isEmpty()) {
                    return null;
                }
                activeMob = optional.get();
            }
            Object type = activeMob.getClass().getMethod("getType").invoke(activeMob);
            Object internalName = type.getClass().getMethod("getInternalName").invoke(type);
            return internalName == null ? null : internalName.toString();
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return null;
        }
    }

    private OrbType randomOrb() {
        OrbType[] values = OrbType.values();
        return values[random.nextInt(values.length)];
    }

    private CharmType randomCharm() {
        CharmElement[] elements = CharmElement.values();
        CharmRarity[] rarities = CharmRarity.values();
        return CharmType.of(elements[random.nextInt(elements.length)], rarities[random.nextInt(rarities.length)]);
    }

    private ItemStack randomSlottedEquipment() {
        for (int attempt = 0; attempt < EQUIPMENT_MATERIALS.length; attempt++) {
            Material material = Material.matchMaterial(EQUIPMENT_MATERIALS[random.nextInt(EQUIPMENT_MATERIALS.length)]);
            if (material == null) {
                continue;
            }
            ItemStack item = new ItemStack(material);
            plugin.orbEquipment().initialize(item, plugin.orbEquipment().rollSlots());
            return item;
        }
        ItemStack fallback = new ItemStack(Material.IRON_SWORD);
        plugin.orbEquipment().initialize(fallback, plugin.orbEquipment().rollSlots());
        return fallback;
    }

    private boolean isOre(Material material) {
        String name = material.name();
        return name.endsWith("_ORE") || name.equals("ANCIENT_DEBRIS");
    }

    private record DropRates(double orbChance, double charmChance, double equipmentChance) {
    }
}

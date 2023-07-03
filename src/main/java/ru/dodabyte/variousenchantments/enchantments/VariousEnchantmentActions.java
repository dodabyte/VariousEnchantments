package ru.dodabyte.variousenchantments.enchantments;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.actions.CustomArrows;
import ru.dodabyte.variousenchantments.actions.FreezingArrow;
import ru.dodabyte.variousenchantments.actions.HookArrow;
import ru.dodabyte.variousenchantments.actions.TeleportArrow;
import ru.dodabyte.variousenchantments.scheduler.Timer;
import ru.dodabyte.variousenchantments.tasks.*;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class VariousEnchantmentActions {

    private static final long ticksInSecond = 20L;
    public static Map<UUID, BukkitTask> bleedOutEntities = new HashMap<>();
    public static Map<UUID, BukkitTask> jumpingBoostEntities = new HashMap<>();
    public static Map<UUID, BukkitTask> speedBoostEntities = new HashMap<>();
    public static Map<UUID, BukkitTask> advanceHealEntities = new HashMap<>();
    public static Map<UUID, BukkitTask> magnetismEntities = new HashMap<>();
    public static Map<UUID, Boolean> teleportedEntitiesMap = new HashMap<>();
    public static Map<UUID, Timer> thunderclapTimerMap = new HashMap<>();
    public static Map<UUID, Arrow> arrowMap = new HashMap<>();

    public static void createWeaponHitActions(LivingEntity victim, ItemStack item) {
        // Bleeding enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.BLEEDING)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.BLEEDING);
            int period = Configurations.getConfig().getPeriod(VariousEnchantment.BLEEDING.getKey().getKey(), level);
            int damage = Configurations.getConfig().getDamage(VariousEnchantment.BLEEDING.getKey().getKey(), level);
            BukkitTask bleedOutTask = new BleedOut(victim, damage).runTaskTimer(VariousEnchantmentsMain.getPlugin(),
                    0L, ticksInSecond * period);
            if (bleedOutEntities.containsKey(victim.getUniqueId())) {
                bleedOutEntities.get(victim.getUniqueId()).cancel();
            }
            bleedOutEntities.put(victim.getUniqueId(), bleedOutTask);
        }
        // Spectral enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.SPECTRAL)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.SPECTRAL);
            int seconds = Configurations.getConfig().getTime(VariousEnchantment.SPECTRAL.getKey().getKey(), level);
            int amplifier = Configurations.getConfig().getAmplifier(VariousEnchantment.SPECTRAL.getKey().getKey(), level);
            victim.addPotionEffect(PotionEffectType.GLOWING.createEffect((int) (ticksInSecond * seconds), amplifier));
        }
        // Daze
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.DAZE)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.DAZE);
            double chance = (double) Configurations.getConfig().getChance(VariousEnchantment.DAZE.getKey().getKey(), level) / 100;
            double random = Math.random();
            if (chance > random) {
                Location location = victim.getLocation();
                location.setDirection(location.getDirection().multiply(-1));
                victim.teleport(location);
            }
        }
    }

    public static void createWeaponKillActions(LivingEntity victim, ItemStack item, List<ItemStack> drop) {
        // Beheading
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.BEHEADING)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.BEHEADING);
            double chance = (double) Configurations.getConfig().getChance(VariousEnchantment.BEHEADING.getKey().getKey(), level) / 100;
            double random = Math.random();
            if (chance > random) {
                if (Configurations.getConfig().isEnableWhat(VariousEnchantment.BEHEADING.getKey().getKey(),
                        Material.SKELETON_SKULL.toString().toLowerCase()) && (victim instanceof Skeleton)) {
                    ItemStack skull = new ItemStack(Material.SKELETON_SKULL);
                    drop.add(skull);
                }
                else if (Configurations.getConfig().isEnableWhat(VariousEnchantment.BEHEADING.getKey().getKey(),
                        Material.WITHER_SKELETON_SKULL.toString().toLowerCase()) && (victim instanceof WitherSkeleton)) {
                    ItemStack skull = new ItemStack(Material.WITHER_SKELETON_SKULL);
                    drop.add(skull);
                }
                else if (Configurations.getConfig().isEnableWhat(VariousEnchantment.BEHEADING.getKey().getKey(),
                        Material.ZOMBIE_HEAD.toString().toLowerCase()) && (victim instanceof Zombie)) {
                    ItemStack skull = new ItemStack(Material.ZOMBIE_HEAD);
                    drop.add(skull);
                }
                else if (Configurations.getConfig().isEnableWhat(VariousEnchantment.BEHEADING.getKey().getKey(),
                        Material.CREEPER_HEAD.toString().toLowerCase()) && (victim instanceof Creeper)) {
                    ItemStack skull = new ItemStack(Material.CREEPER_HEAD);
                    drop.add(skull);
                }
                else if (Configurations.getConfig().isEnableWhat(VariousEnchantment.BEHEADING.getKey().getKey(),
                        Material.PLAYER_HEAD.toString().toLowerCase()) && (victim instanceof Player)) {
                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    skullMeta.setOwningPlayer(((Player) victim));
                    skullMeta.setDisplayName(((Player) victim).getName() + "'s skull");
                    skull.setItemMeta(skullMeta);
                    drop.add(skull);
                }
                else if (Configurations.getConfig().isEnableWhat(VariousEnchantment.BEHEADING.getKey().getKey(),
                        Material.DRAGON_HEAD.toString().toLowerCase()) && (victim instanceof EnderDragon)) {
                    ItemStack skull = new ItemStack(Material.DRAGON_HEAD);
                    drop.add(skull);
                }
            }
        }
    }

    public static void createToolAction(LivingEntity livingEntity, ItemStack item) {
        // Magnetism enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.MAGNETISM)) {
            if (livingEntity instanceof Player player) {
                int level = EnchantmentUtils.getLevel(item, VariousEnchantment.MAGNETISM);
                float radius = Configurations.getConfig().getRadius(VariousEnchantment.MAGNETISM.getKey().getKey(), level);
                if (!magnetismEntities.containsKey(player.getUniqueId())) {
                    BukkitTask magnetismTask = new Magnetism(player, radius).
                            runTaskTimer(VariousEnchantmentsMain.getPlugin(),
                            0L, ticksInSecond);
                    magnetismEntities.put(livingEntity.getUniqueId(), magnetismTask);
                }
            }
        }
    }

    public static void createWearableAction(LivingEntity livingEntity, ItemStack item) {
        // Thunderclap enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.THUNDERCLAP)) {
            if (livingEntity instanceof Player player) {
                int level = EnchantmentUtils.getLevel(item, VariousEnchantment.THUNDERCLAP);
                int damage = Configurations.getConfig().getDamage(VariousEnchantment.THUNDERCLAP.getKey().getKey(), level);
                int radius = (int) Configurations.getConfig().getRadius(VariousEnchantment.THUNDERCLAP.getKey().getKey(), level);

                double chance = 1;
                int cooldown = 0;
                if (Configurations.getConfig().isEnableWhat(VariousEnchantment.THUNDERCLAP.getKey().getKey(),
                        "chance")) {
                    chance = (double) Configurations.getConfig().getChance(VariousEnchantment.THUNDERCLAP.getKey().getKey(), level) / 100;
                }
                if (Configurations.getConfig().isEnableWhat(VariousEnchantment.THUNDERCLAP.getKey().getKey(),
                        "cooldown")) {
                    cooldown = Configurations.getConfig().getCooldown(VariousEnchantment.THUNDERCLAP.getKey().getKey(), level);
                    if (!thunderclapTimerMap.containsKey(player.getUniqueId())) {
                        thunderclapTimerMap.put(player.getUniqueId(), new Timer(cooldown));
                    }
                }
                double random = Math.random();

                if (chance > random && (cooldown == 0 || (thunderclapTimerMap != null &&
                        thunderclapTimerMap.get(player.getUniqueId()) != null &&
                        thunderclapTimerMap.get(player.getUniqueId()).getCurrentTimer() == 0))) {
                    if (cooldown > 0) {
                        thunderclapTimerMap.get(player.getUniqueId()).start();
                    }

                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);

                    for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                        if (entity instanceof LivingEntity otherLivingEntity && !otherLivingEntity.equals(player)) {
                            otherLivingEntity.getWorld().strikeLightningEffect(otherLivingEntity.getLocation());
                            otherLivingEntity.damage(damage);
                        }
                    }
                }
            }
        }
        // Shield bash enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.SHIELD_BASH)) {
            if (livingEntity instanceof Player player) {
                int level = EnchantmentUtils.getLevel(item, VariousEnchantment.SHIELD_BASH);
                int damage = Configurations.getConfig().getDamage(VariousEnchantment.SHIELD_BASH.getKey().getKey(), level);
                int radius = (int) Configurations.getConfig().getRadius(VariousEnchantment.SHIELD_BASH.getKey().getKey(), level);
                float velocity = Configurations.getConfig().getVelocity(VariousEnchantment.SHIELD_BASH.getKey().getKey(), level);

                double chance = (double) Configurations.getConfig().getChance(VariousEnchantment.SHIELD_BASH.getKey().getKey(), level) / 100;
                double random = Math.random();

                if (chance > random) {
                    for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                        if (entity instanceof LivingEntity otherLivingEntity && !otherLivingEntity.equals(player)) {
                            otherLivingEntity.damage(damage);
                            otherLivingEntity.setVelocity(livingEntity.getLocation().getDirection().setY(velocity));
                        }
                    }
                }
            }
        }
    }

    public static void createHoeActionsOnShoot(LivingEntity livingEntity, ItemStack item) {
        // Hook enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.HOOK)) {
            if (livingEntity instanceof Player player) {
                int level = EnchantmentUtils.getLevel(item, VariousEnchantment.HOOK);
                int distance = Configurations.getConfig().getDistance(VariousEnchantment.HOOK.getKey().getKey(), level);
                if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.ADVENTURE)) {
                    item.setDurability((short) (item.getDurability() + 1));
                }
                Arrow arrow = player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(3));
                arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                arrow.setDamage(0);
                arrow.setMetadata(VariousEnchantment.HOOK.getKey().getKey(),
                        new FixedMetadataValue(VariousEnchantmentsMain.getPlugin(), level));
                CustomArrows.getArrowMap().put(arrow.getUniqueId(), new HookArrow(arrow, player.getLocation(), distance));
            }
        }
    }

    public static void createHoeActionsOnArrowHit(LivingEntity livingEntity, Entity arrow, Entity hitEntity) {
        // Hook enchantment
        if (CustomArrows.getArrowMap().containsKey(arrow.getUniqueId())) {
            if (CustomArrows.getArrowMap().get(arrow.getUniqueId()) != null &&
                    CustomArrows.getArrowMap().get(arrow.getUniqueId()).getMinecraftArrow().hasMetadata(
                    VariousEnchantment.HOOK.getKey().getKey())) {
                if (hitEntity != null && hitEntity.getLocation() != livingEntity.getLocation() &&
                        livingEntity instanceof Player player && hitEntity instanceof LivingEntity && hitEntity != livingEntity) {
                    int level = CustomArrows.getArrowMap().get(arrow.getUniqueId()).getMinecraftArrow().
                            getMetadata(VariousEnchantment.HOOK.getKey().getKey()).get(0).asInt();
                    float radius = Configurations.getConfig().getRadius(VariousEnchantment.HOOK.getKey().getKey(), level);
                    Bukkit.getScheduler().runTaskTimer(VariousEnchantmentsMain.getProvidingPlugin(VariousEnchantmentsMain.class),
                            (task) -> {
                        if (player.getNearbyEntities(radius, radius, radius).contains(hitEntity)) task.cancel();
                        Location entityLocation = hitEntity.getLocation();
                        if (hitEntity.getLocation().getX() < player.getLocation().getX()) entityLocation.setX(entityLocation.getX() + 1);
                        else if (hitEntity.getLocation().getX() > player.getLocation().getX()) entityLocation.setX(entityLocation.getX() - 1);
                        if (hitEntity.getLocation().getY() < player.getLocation().getY()) entityLocation.setY(entityLocation.getY() + 1);
                        else if (hitEntity.getLocation().getY() > player.getLocation().getY()) entityLocation.setY(entityLocation.getY() - 1);
                        if (hitEntity.getLocation().getZ() < player.getLocation().getZ()) entityLocation.setZ(entityLocation.getZ() + 1);
                        else if (hitEntity.getLocation().getZ() > player.getLocation().getZ()) entityLocation.setZ(entityLocation.getZ() - 1);
                        hitEntity.teleport(entityLocation);
                    }, 0L, 1L);
                }
                arrow.removeMetadata(VariousEnchantment.HOOK.getKey().getKey(), VariousEnchantmentsMain.getPlugin());
                if (CustomArrows.getArrowMap().get(arrow.getUniqueId()).getMinecraftArrow() != null) {
                    CustomArrows.getArrowMap().get(arrow.getUniqueId()).getMinecraftArrow().remove();
                }
                CustomArrows.getArrowMap().remove(arrow.getUniqueId());
            }
        }
    }

    public static void createBowActionsOnShoot(LivingEntity livingEntity, Arrow arrow, ItemStack item) {
        // Ender enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.ENDER)) {
            livingEntity.setGliding(true);
            teleportedEntitiesMap.put(livingEntity.getUniqueId(), true);
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.ENDER);
            arrow.setMetadata(VariousEnchantment.ENDER.getKey().getKey(),
                    new FixedMetadataValue(VariousEnchantmentsMain.getPlugin(), level));
            CustomArrows.getArrowMap().put(arrow.getUniqueId(), new TeleportArrow(arrow, livingEntity));
        }
        // Freezing enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.FREEZING)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.FREEZING);
            float radius = Configurations.getConfig().getRadius(VariousEnchantment.FREEZING.getKey().getKey(), level);
            int time = Configurations.getConfig().getTime(VariousEnchantment.FREEZING.getKey().getKey(), level);
            arrow.setMetadata(VariousEnchantment.FREEZING.getKey().getKey(),
                    new FixedMetadataValue(VariousEnchantmentsMain.getPlugin(), level));
            CustomArrows.getArrowMap().put(arrow.getUniqueId(), new FreezingArrow(arrow, radius, time));
        }
        // Explosion, Lightning, Poison cloud enchantments
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.EXPLOSION) ||
                EnchantmentUtils.hasEnchantment(item, VariousEnchantment.LIGHTNING) ||
                EnchantmentUtils.hasEnchantment(item, VariousEnchantment.POISON_CLOUD)) {
            for (Map.Entry<Enchantment, Integer> enchantmentEntry : item.getEnchantments().entrySet()) {
                if (VariousEnchantment.isRegisteredEnchantment(enchantmentEntry.getKey())) {
                    int level = enchantmentEntry.getValue();
                    arrow.setMetadata(enchantmentEntry.getKey().getKey().getKey(),
                            new FixedMetadataValue(VariousEnchantmentsMain.getPlugin(), level));
                }
            }
            arrowMap.put(arrow.getUniqueId(), arrow);
        }
    }

    public static void createBowActionsOnArrowHit(LivingEntity livingEntityShooter, Arrow arrow, Block hitBlock) {
        if (CustomArrows.getArrowMap().containsKey(arrow.getUniqueId())) {
            // Ender enchantment
            if (CustomArrows.getArrowMap().get(arrow.getUniqueId()) != null &&
                    CustomArrows.getArrowMap().get(arrow.getUniqueId()).getMinecraftArrow().hasMetadata(
                    VariousEnchantment.ENDER.getKey().getKey())) {
                int level = CustomArrows.getArrowMap().get(arrow.getUniqueId()).getMinecraftArrow().
                        getMetadata(VariousEnchantment.ENDER.getKey().getKey()).get(0).asInt();
                int damage = Configurations.getConfig().getDamage(VariousEnchantment.ENDER.getKey().getKey(), level);
                livingEntityShooter.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,
                        10, 10);
                arrow.removeMetadata(VariousEnchantment.ENDER.getKey().getKey(), VariousEnchantmentsMain.getPlugin());
                livingEntityShooter.damage(damage);
                livingEntityShooter.setFallDistance(1);
                teleportedEntitiesMap.remove(livingEntityShooter.getUniqueId());
            }
            // Freezing enchantment
            if (CustomArrows.getArrowMap().get(arrow.getUniqueId()) != null &&
                    CustomArrows.getArrowMap().get(arrow.getUniqueId()).getMinecraftArrow().hasMetadata(
                            VariousEnchantment.FREEZING.getKey().getKey()))  {
                int level = CustomArrows.getArrowMap().get(arrow.getUniqueId()).getMinecraftArrow().
                        getMetadata(VariousEnchantment.FREEZING.getKey().getKey()).get(0).asInt();
                int time = Configurations.getConfig().getTime(VariousEnchantment.FREEZING.getKey().getKey(), level);
                float radius = Configurations.getConfig().getRadius(VariousEnchantment.FREEZING.getKey().getKey(), level);
                int amplifier = Configurations.getConfig().getAmplifier(VariousEnchantment.FREEZING.getKey().getKey(), level);
                for (Entity entity : arrow.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                                (int)(ticksInSecond * time), amplifier));
                    }
                }
                arrow.removeMetadata(VariousEnchantment.FREEZING.getKey().getKey(), VariousEnchantmentsMain.getPlugin());
            }
            CustomArrows.getArrowMap().remove(arrow.getUniqueId());
        }
        if (arrowMap.containsKey(arrow.getUniqueId())) {
            // Explosion enchantment
            if (arrowMap.get(arrow.getUniqueId()) != null &&
                    arrowMap.get(arrow.getUniqueId()).hasMetadata(VariousEnchantment.EXPLOSION.getKey().getKey())) {
                int level = arrowMap.get(arrow.getUniqueId()).
                        getMetadata(VariousEnchantment.EXPLOSION.getKey().getKey()).get(0).asInt();
                float power = Configurations.getConfig().getPower(VariousEnchantment.EXPLOSION.getKey().getKey(), level);
                arrowMap.get(arrow.getUniqueId()).getWorld().createExplosion(arrow.getLocation(), power);
                arrowMap.get(arrow.getUniqueId()).removeMetadata(VariousEnchantment.EXPLOSION.getKey().getKey(), VariousEnchantmentsMain.getPlugin());
                if (arrowMap.get(arrow.getUniqueId()) != null) {
                    arrowMap.get(arrow.getUniqueId()).remove();
                }
            }
            // Lightning enchantment
            if (arrowMap.get(arrow.getUniqueId()) != null &&
                    arrowMap.get(arrow.getUniqueId()).hasMetadata(VariousEnchantment.LIGHTNING.getKey().getKey())) {
                int level = arrowMap.get(arrow.getUniqueId()).
                        getMetadata(VariousEnchantment.LIGHTNING.getKey().getKey()).get(0).asInt();
                int damage = Configurations.getConfig().getDamage(VariousEnchantment.LIGHTNING.getKey().getKey(), level);
                int radius = (int) Configurations.getConfig().getRadius(VariousEnchantment.LIGHTNING.getKey().getKey(), level);
                double first_chance = Configurations.getConfig().getParameter(VariousEnchantment.LIGHTNING.getKey().getKey(),
                        "first_fire_chance", level) / 100;
                double second_chance = Configurations.getConfig().getParameter(VariousEnchantment.LIGHTNING.getKey().getKey(),
                        "second_fire_chance", level) / 100;
                double thirst_chance = Configurations.getConfig().getParameter(VariousEnchantment.LIGHTNING.getKey().getKey(),
                        "thirst_fire_chance", level) / 100;
                double random = Math.random();

                for (int i = 0; i < level; i++) {
                    arrow.getWorld().playSound(hitBlock.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER,
                                1, 1);
                    arrow.getWorld().strikeLightningEffect(hitBlock.getLocation());
                }

                if (first_chance > random) {
                    arrow.getLocation().getBlock().setType(Material.FIRE);
                }
                if (second_chance > random) {
                    Location loc = arrow.getLocation().getBlock().getLocation();
                    if (Math.random() > 0.5) {
                        loc.setX(loc.getX() + ThreadLocalRandom.current().nextInt(-2, 2 + 1));
                    }
                    else {
                        loc.setZ(loc.getZ() + ThreadLocalRandom.current().nextInt(-2, 2 + 1));
                    }
                    loc.getBlock().setType(Material.FIRE);
                }
                if (thirst_chance > random) {
                    Location loc = arrow.getLocation().getBlock().getLocation();
                    if (Math.random() > 0.5) {
                        loc.setX(loc.getX() + ThreadLocalRandom.current().nextInt(-2, 2 + 1));
                    }
                    else {
                        loc.setZ(loc.getZ() + ThreadLocalRandom.current().nextInt(-2, 2 + 1));
                    }
                    loc.getBlock().setType(Material.FIRE);
                }

                for (Entity entity : arrow.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.damage(damage);
                    }
                }

                arrowMap.get(arrow.getUniqueId()).removeMetadata(VariousEnchantment.LIGHTNING.getKey().getKey(), VariousEnchantmentsMain.getPlugin());
                if (arrowMap.get(arrow.getUniqueId()) != null) {
                    arrowMap.get(arrow.getUniqueId()).remove();
                }
            }
            // Poison cloud enchantment
            if (arrowMap.get(arrow.getUniqueId()) != null &&
                    arrowMap.get(arrow.getUniqueId()).hasMetadata(VariousEnchantment.POISON_CLOUD.getKey().getKey())) {
                int level = arrowMap.get(arrow.getUniqueId()).
                        getMetadata(VariousEnchantment.POISON_CLOUD.getKey().getKey()).get(0).asInt();
                int cloudSeconds = (int) Math.round(Configurations.getConfig().getParameter(VariousEnchantment.POISON_CLOUD.getKey().getKey(),
                        "cloud_time", level));
                int potionSeconds = (int) Math.round(Configurations.getConfig().getParameter(VariousEnchantment.POISON_CLOUD.getKey().getKey(),
                        "potion_time", level));
                float radius = Configurations.getConfig().getRadius(VariousEnchantment.POISON_CLOUD.getKey().getKey(),
                        level);
                int amplifier = Configurations.getConfig().getAmplifier(VariousEnchantment.POISON_CLOUD.getKey().getKey(), level);
                AreaEffectCloud cloud = arrow.getWorld().spawn(hitBlock.getLocation(), AreaEffectCloud.class);
                cloud.setRadius(radius);
                cloud.setDuration((int) (ticksInSecond * cloudSeconds));
                cloud.addCustomEffect(new PotionEffect(PotionEffectType.POISON, (int)(ticksInSecond * potionSeconds),
                        amplifier), false);
                arrow.removeMetadata(VariousEnchantment.POISON_CLOUD.getKey().getKey(), VariousEnchantmentsMain.getPlugin());
            }
            arrowMap.remove(arrow.getUniqueId());
        }
    }

    public static void createBootsActions(LivingEntity livingEntity, ItemStack item) {
        // Jumping enchantments
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.JUMPING)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.JUMPING);
            int amplifier = Configurations.getConfig().getAmplifier(VariousEnchantment.JUMPING.getKey().getKey(), level);
            if (!jumpingBoostEntities.containsKey(livingEntity.getUniqueId())) {
                BukkitTask jumpingBoostTask = new JumpingBoost(livingEntity, amplifier).runTaskTimer(VariousEnchantmentsMain.getPlugin(),
                        0L, ticksInSecond);
                jumpingBoostEntities.put(livingEntity.getUniqueId(), jumpingBoostTask);
            }
        }
        // Speed enchantments
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.SPEED)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.SPEED);
            int amplifier = Configurations.getConfig().getAmplifier(VariousEnchantment.SPEED.getKey().getKey(), level);
            if (!speedBoostEntities.containsKey(livingEntity.getUniqueId())) {
                BukkitTask speedBoostTask = new SpeedBoost(livingEntity, amplifier).runTaskTimer(VariousEnchantmentsMain.getPlugin(),
                        0L, ticksInSecond);
                speedBoostEntities.put(livingEntity.getUniqueId(), speedBoostTask);
            }
        }
    }

    public static void createChestplateActions(LivingEntity livingEntity, ItemStack item) {
        // Advance Heal enchantments
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.HEAL)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.HEAL);
            int period = Configurations.getConfig().getPeriod(VariousEnchantment.HEAL.getKey().getKey(), level);
            int healthCount = (int) Math.round(Configurations.getConfig().getParameter(VariousEnchantment.HEAL.getKey().getKey(),
                    "health_count", level));
            int foodLevelCount = (int) Math.round(Configurations.getConfig().getParameter(VariousEnchantment.HEAL.getKey().getKey(),
                    "food_level_count", level));
            if (!advanceHealEntities.containsKey(livingEntity.getUniqueId())) {
                BukkitTask advanceHealTask = new AdvanceHeal(livingEntity, healthCount, foodLevelCount).runTaskTimer(VariousEnchantmentsMain.getPlugin(),
                        0L, ticksInSecond * period);
                advanceHealEntities.put(livingEntity.getUniqueId(), advanceHealTask);
            }
        }
    }
}

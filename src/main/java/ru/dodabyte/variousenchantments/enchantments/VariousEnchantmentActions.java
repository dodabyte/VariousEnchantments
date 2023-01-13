package ru.dodabyte.variousenchantments.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.actions.Arrows;
import ru.dodabyte.variousenchantments.actions.TeleportArrow;
import ru.dodabyte.variousenchantments.tasks.BleedOut;
import ru.dodabyte.variousenchantments.tasks.JumpingBoost;
import ru.dodabyte.variousenchantments.tasks.SpeedBoost;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VariousEnchantmentActions {

    private static final long ticksInSecond = 20L;
    public static Map<UUID, BukkitTask> bleedOutPlayers = new HashMap<>();
    public static Map<UUID, BukkitTask> jumpingBoostEntities = new HashMap<>();
    public static Map<UUID, BukkitTask> speedBoostEntities = new HashMap<>();
    public static boolean isTeleported = false;

    public static void createWeaponHitActions(LivingEntity victim, ItemStack item) {
        // Bleeding enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.BLEEDING)) {
            // Once every 5, 4 or 3 seconds
            long timeTask;
            int damage;
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.BLEEDING);
            switch (level) {
                case 2 -> {
                    timeTask = 4L;
                    damage = 3;
                }
                case 3 -> {
                    timeTask = 3L;
                    damage = 4;
                }
                default -> {
                    timeTask = 5L;
                    damage = 2;
                }
            }
            BukkitTask bleedOutTask = new BleedOut(victim, damage).runTaskTimer(VariousEnchantmentsMain.getPlugin(),
                    0L, timeTask * ticksInSecond);
            if (victim instanceof Player) {
                bleedOutPlayers.put(victim.getUniqueId(), bleedOutTask);
            }
        }
        // Spectral enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.SPECTRAL)) {
            victim.addPotionEffect(PotionEffectType.GLOWING.createEffect(200, 1));
        }
        // Daze
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.DAZE)) {
            double chance = EnchantmentUtils.getLevel(item, VariousEnchantment.DAZE) * .05;
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
            double chance = EnchantmentUtils.getLevel(item, VariousEnchantment.BEHEADING) * .05;
            double random = Math.random();
            if (chance > random) {
                if (victim instanceof Skeleton) {
                    ItemStack skull = new ItemStack(Material.SKELETON_SKULL);
                    drop.add(skull);
                }
                else if (victim instanceof WitherSkeleton) {
                    ItemStack skull = new ItemStack(Material.WITHER_SKELETON_SKULL);
                    drop.add(skull);
                }
                else if (victim instanceof Zombie) {
                    ItemStack skull = new ItemStack(Material.ZOMBIE_HEAD);
                    drop.add(skull);
                }
                else if (victim instanceof Creeper) {
                    ItemStack skull = new ItemStack(Material.CREEPER_HEAD);
                    drop.add(skull);
                }
                else if (victim instanceof Player) {
                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    skullMeta.setOwningPlayer(((Player) victim));
                    skullMeta.setDisplayName(ChatColor.DARK_RED + ((Player) victim).getName() + "'s Skull");
                    skull.setItemMeta(skullMeta);
                    drop.add(skull);
                }
                else if (victim instanceof EnderDragon) {
                    ItemStack skull = new ItemStack(Material.DRAGON_HEAD);
                    drop.add(skull);
                }
            }
        }
    }

    public static void createBowActionsOnShoot(LivingEntity livingEntity, Entity arrow, ItemStack item) {
        try {
            // Ender enchantment
            if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.ENDER)) {
                livingEntity.setGliding(true);
                isTeleported = true;
                Arrows.getEnderArrowMap().put(arrow.getUniqueId(), new TeleportArrow(arrow, livingEntity));
            }
        }
        catch (NullPointerException ignored) {}
    }

    public static void createBowActionsOnArrowHit(LivingEntity livingEntityShooter, Entity arrow, ItemStack item) {
        // Ender enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.ENDER)) {
            Arrows.getEnderArrowMap().remove(arrow.getUniqueId());
            livingEntityShooter.damage(5);
            livingEntityShooter.setFallDistance(1);
            isTeleported = false;
        }
        // Explosion enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.EXPLOSION)) {
            float power;
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.EXPLOSION);
            power = switch (level) {
                case 2 -> 2.5f;
                case 3 -> 3.3f;
                default -> 1.5f;
            };
            arrow.getWorld().createExplosion(arrow.getLocation(), power);
            arrow.remove();
        }
        // Lightning enchantment
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.LIGHTNING)) {
            arrow.getWorld().strikeLightning(arrow.getLocation());
            arrow.remove();
        }
    }

    public static void createBootsActions(LivingEntity livingEntity, ItemStack item) {
        // Jumping enchantments
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.JUMPING)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.JUMPING);
            BukkitTask jumpingBoostTask = new JumpingBoost(livingEntity, level).runTaskTimer(VariousEnchantmentsMain.getPlugin(),
                    0L, 20L);
            jumpingBoostEntities.put(livingEntity.getUniqueId(), jumpingBoostTask);
        }
        if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.SPEED)) {
            int level = EnchantmentUtils.getLevel(item, VariousEnchantment.SPEED);
            BukkitTask speedBoostTask = new SpeedBoost(livingEntity, level).runTaskTimer(VariousEnchantmentsMain.getPlugin(),
                    0L, 20L);
            speedBoostEntities.put(livingEntity.getUniqueId(), speedBoostTask);
        }
    }
}

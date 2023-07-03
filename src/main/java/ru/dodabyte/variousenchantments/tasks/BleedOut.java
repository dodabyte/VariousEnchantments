package ru.dodabyte.variousenchantments.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BleedOut extends BukkitRunnable {

    LivingEntity victim;
    int damage;

    final ScheduledExecutorService scheduler;
    int countdownStarter;

    public BleedOut(LivingEntity victim, int damage) {
        this.victim = victim;
        this.damage = damage;

        countdownStarter = 12; // seconds
        scheduler = Executors.newScheduledThreadPool(1);
        if (!(victim instanceof Player)) startTimer();
    }

    @Override
    public void run() {
        Location location = victim.getLocation();
        location.setY(location.getY() + 1);

        if ((victim instanceof Player player) && !player.isOnline()) return;

        if (victim.getHealth() > 0) {
            victim.damage(damage);
            if (!(victim instanceof Player) && victim.getHurtSound() != null)
                location.getWorld().playSound(location, victim.getHurtSound(), 1, 1);
            if (victim instanceof Player player) player.isOnline();
            location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location,
                    15, Material.RED_TERRACOTTA.createBlockData());
        }

        if (countdownStarter < 0 || victim.isDead() || victim.getHealth() <= 0) {
            System.out.println("Ya tut");
            VariousEnchantmentActions.bleedOutEntities.remove(victim.getUniqueId());
            this.cancel();
        }
    }

    public void startTimer() {
        final Runnable timerForMobs = new Runnable() {
            public void run() {
                countdownStarter--;

                if (countdownStarter < 0) {
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(timerForMobs, 0, 1, SECONDS);
    }
}

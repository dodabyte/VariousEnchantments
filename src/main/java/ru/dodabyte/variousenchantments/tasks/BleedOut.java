package ru.dodabyte.variousenchantments.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
        Location particleSpawnLocation = victim.getLocation();
        particleSpawnLocation.setY(particleSpawnLocation.getY() + 1);

        if (victim.getHealth() > 1) {
            victim.damage(damage);
            particleSpawnLocation.getWorld().spawnParticle(Particle.BLOCK_CRACK, particleSpawnLocation,
                    15, Material.RED_TERRACOTTA.createBlockData());
        }
        else if (victim.getHealth() == 1) {
            victim.damage(damage);
            particleSpawnLocation.getWorld().spawnParticle(Particle.BLOCK_CRACK, particleSpawnLocation,
                    15, Material.RED_TERRACOTTA.createBlockData());
            this.cancel();
        }

        if (!(victim instanceof Player) && countdownStarter < 0 || victim.isDead()) {
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

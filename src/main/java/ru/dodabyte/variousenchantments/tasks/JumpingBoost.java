package ru.dodabyte.variousenchantments.tasks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;

public class JumpingBoost extends BukkitRunnable {

    LivingEntity livingEntity;
    int power;

    public JumpingBoost(LivingEntity livingEntity, int power) {
        this.livingEntity = livingEntity;
        this.power = power;
    }

    @Override
    public void run() {
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2, power - 1, false, false, false));
        if (livingEntity.getEquipment().getBoots() == null) {
            VariousEnchantmentActions.jumpingBoostEntities.remove(livingEntity.getUniqueId());
            this.cancel();
        }
    }
}

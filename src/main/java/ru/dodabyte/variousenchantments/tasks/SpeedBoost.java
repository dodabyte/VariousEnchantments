package ru.dodabyte.variousenchantments.tasks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;

public class SpeedBoost extends BukkitRunnable {
    LivingEntity livingEntity;
    int amplifier;

    public SpeedBoost(LivingEntity livingEntity, int amplifier) {
        this.livingEntity = livingEntity;
        this.amplifier = amplifier;
    }

    @Override
    public void run() {
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, amplifier,
                false, false, false));
        if (livingEntity.getEquipment().getBoots() == null ||
                !EnchantmentUtils.hasEnchantment(livingEntity.getEquipment().getBoots(), VariousEnchantment.SPEED)) {
            VariousEnchantmentActions.speedBoostEntities.remove(livingEntity.getUniqueId());
            this.cancel();
        }
    }
}

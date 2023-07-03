package ru.dodabyte.variousenchantments.tasks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;

public class AdvanceHeal extends BukkitRunnable {
    LivingEntity livingEntity;
    int healthCount;
    int foodLevelCount;

    public AdvanceHeal(LivingEntity livingEntity, int healthCount, int foodLevelCount) {
        this.livingEntity = livingEntity;
        this.healthCount = healthCount;
        this.foodLevelCount = foodLevelCount;
    }

    @Override
    public void run() {
        if (livingEntity.getHealth() > 0 && livingEntity.getHealth() < 20) {
            if (livingEntity instanceof Player player) {
                if (player.getFoodLevel() > 0) {
                    player.setHealth(player.getHealth() + healthCount);
                    player.setFoodLevel(player.getFoodLevel() - foodLevelCount);
                }
            }
            else {
                livingEntity.setHealth(livingEntity.getHealth() + healthCount);
            }
        }
        if (livingEntity.getEquipment().getChestplate() == null ||
                !EnchantmentUtils.hasEnchantment(livingEntity.getEquipment().getChestplate(), VariousEnchantment.HEAL)) {
            VariousEnchantmentActions.advanceHealEntities.remove(livingEntity.getUniqueId());
            this.cancel();
        }
    }
}

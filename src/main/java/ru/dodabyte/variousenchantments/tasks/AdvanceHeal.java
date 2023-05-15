package ru.dodabyte.variousenchantments.tasks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;

public class AdvanceHeal extends BukkitRunnable {

    Player player;
    int power;

    public AdvanceHeal(Player player, int power) {
        this.player = player;
        this.power = power;
    }

    @Override
    public void run() {
        if (player.getHealth() > 0 && player.getHealth() < 20 && player.getFoodLevel() > 0) {
            player.setHealth(player.getHealth() + 1);
            player.setFoodLevel(player.getFoodLevel() - 1);
        }
        if (player.getEquipment().getChestplate() == null) {
            VariousEnchantmentActions.advanceHealEntities.remove(player.getUniqueId());
            this.cancel();
        }
    }
}

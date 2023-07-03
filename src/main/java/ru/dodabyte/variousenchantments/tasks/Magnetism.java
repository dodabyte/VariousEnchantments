package ru.dodabyte.variousenchantments.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;

public class Magnetism extends BukkitRunnable {
    private final Player player;
    private final float radius;

    public Magnetism(Player player, float radius) {
        this.player = player;
        this.radius = radius;
    }

    @Override
    public void run() {
        if (EnchantmentUtils.hasEnchantment(player.getEquipment().getItemInMainHand(), VariousEnchantment.MAGNETISM)) {
            for (Entity nearbyEntities : player.getNearbyEntities(radius, radius, radius)) {
                if (nearbyEntities instanceof Item item) {
                    player.getInventory().addItem(item.getItemStack());
                    nearbyEntities.remove();
                }
            }
        }
        else {
            VariousEnchantmentActions.magnetismEntities.remove(player.getUniqueId());
            this.cancel();
        }
    }
}

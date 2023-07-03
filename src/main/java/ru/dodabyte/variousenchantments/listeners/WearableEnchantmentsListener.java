package ru.dodabyte.variousenchantments.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;

public class WearableEnchantmentsListener implements Listener {
    @EventHandler
    public void onEntityHit(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.isBlocking()) {
                ItemStack shield;
                if (player.getEquipment().getItemInMainHand().getType().equals(Material.SHIELD)) {
                    shield = player.getEquipment().getItemInMainHand();
                }
                else {
                    shield = player.getEquipment().getItemInOffHand();
                }
                VariousEnchantmentActions.createWearableAction(player, shield);
            }
        }
    }
}

package ru.dodabyte.variousenchantments.listeners;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;

public class ToolEnchantmentsListener implements Listener {

    @EventHandler
    public void onHoeInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (player.getEquipment().getItemInMainHand().getType().name().contains("HOE")) {
                VariousEnchantmentActions.createHoeActionsOnShoot(player,
                        player.getEquipment().getItemInMainHand());
            }
        }
    }

    @EventHandler
    public void onHoeProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (event.getEntity().getShooter() instanceof LivingEntity livingEntityShooter) {
                VariousEnchantmentActions.createHoeActionsOnArrowHit(livingEntityShooter, arrow,
                        livingEntityShooter.getEquipment().getItemInMainHand(), event.getHitEntity());
            }
        }
    }
}

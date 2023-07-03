package ru.dodabyte.variousenchantments.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;

public class ToolEnchantmentsListener implements Listener {
    @EventHandler
    public void onToolInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (player.getEquipment().getItemInMainHand().getType().name().contains("HOE")) {
                VariousEnchantmentActions.createHoeActionsOnShoot(player, player.getEquipment().getItemInMainHand());
            }
        }
    }

    @EventHandler
    public void onToolProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (event.getEntity().getShooter() instanceof LivingEntity livingEntityShooter) {
                VariousEnchantmentActions.createHoeActionsOnArrowHit(livingEntityShooter, arrow, event.getHitEntity());
            }
        }
    }

    @EventHandler
    public void onChangeItemInMainHand(PlayerItemHeldEvent event) {
        if (EnchantmentUtils.hasEnchantment(event.getPlayer().getInventory().getItem(event.getNewSlot()), VariousEnchantment.MAGNETISM)) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItem(event.getNewSlot());
            VariousEnchantmentActions.createToolAction(player, item);
        }
    }
}

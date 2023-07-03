package ru.dodabyte.variousenchantments.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;

public class BowEnchantmentsListener implements Listener {

    // Launching actions from enchantments when shooting a bow
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getProjectile() instanceof Arrow arrow) {
            LivingEntity livingEntity = event.getEntity();
            ItemStack item = livingEntity.getEquipment().getItemInMainHand();
            if (!item.getType().equals(Material.BOW)) {
                item = livingEntity.getEquipment().getItemInOffHand();
            }
            VariousEnchantmentActions.createBowActionsOnShoot(livingEntity, arrow, item);
        }
    }

    // Launching actions from enchantments when an arrow hits somewhere
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (event.getEntity().getShooter() instanceof LivingEntity livingEntityShooter) {
                VariousEnchantmentActions.createBowActionsOnArrowHit(livingEntityShooter, arrow, event.getHitBlock());
            }
        }
    }

    // Prohibition on the use of objects (bow) in the main hand
    @EventHandler
    public void onUsageItemsLock(PlayerInteractEvent event) {
        if (!event.hasItem()) return;
        if ((event.getAction() != Action.RIGHT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

        if (VariousEnchantmentActions.teleportedEntitiesMap != null &&
                VariousEnchantmentActions.teleportedEntitiesMap.containsKey(event.getPlayer().getUniqueId()) &&
                VariousEnchantmentActions.teleportedEntitiesMap.get(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    // Prohibition on changing the item in the main hand
    @EventHandler
    public void onChangeItemInMainHandLock(PlayerItemHeldEvent event) {
        if (VariousEnchantmentActions.teleportedEntitiesMap != null &&
                VariousEnchantmentActions.teleportedEntitiesMap.containsKey(event.getPlayer().getUniqueId()) &&
                VariousEnchantmentActions.teleportedEntitiesMap.get(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    // Prohibition on replacing the bow with another item in the inventory
    @EventHandler
    public void onChangeItemInInventory(InventoryClickEvent event) {
        if (VariousEnchantmentActions.teleportedEntitiesMap != null &&
                VariousEnchantmentActions.teleportedEntitiesMap.containsKey(event.getWhoClicked().getUniqueId()) &&
                VariousEnchantmentActions.teleportedEntitiesMap.get(event.getWhoClicked().getUniqueId())) {
            Player player = (Player) event.getWhoClicked();
            if (event.getHotbarButton() != -1) {
                ItemStack item = player.getInventory().getContents()[event.getHotbarButton()];
                if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.ENDER)) {
                    event.setCancelled(true);
                }
            }

            ItemStack item = event.getCurrentItem();
            ItemStack cursorItem = event.getCursor();
            if (EnchantmentUtils.hasEnchantment(item, VariousEnchantment.ENDER) ||
                    EnchantmentUtils.hasEnchantment(cursorItem, VariousEnchantment.ENDER)) {
                event.setCancelled(true);
            }
        }
    }

    // Prohibition on changing items in the main and secondary hands
    @EventHandler
    public void onSwapItemInHandsLock(PlayerSwapHandItemsEvent event) {
        if (VariousEnchantmentActions.teleportedEntitiesMap != null &&
                VariousEnchantmentActions.teleportedEntitiesMap.containsKey(event.getPlayer().getUniqueId()) &&
                VariousEnchantmentActions.teleportedEntitiesMap.get(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}

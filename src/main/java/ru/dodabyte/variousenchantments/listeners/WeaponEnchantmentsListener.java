package ru.dodabyte.variousenchantments.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;
import ru.dodabyte.variousenchantments.tasks.BleedOut;

public class WeaponEnchantmentsListener implements Listener {

    // Launching actions from enchantments when attack with a weapon
    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity livingEntityDamager) {
            ItemStack item = livingEntityDamager.getEquipment().getItemInMainHand();
            LivingEntity victim = (LivingEntity) event.getEntity();
            VariousEnchantmentActions.createWeaponHitActions(victim, item);
        }
    }

    // Launching actions using enchantments after killing
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null){
            LivingEntity victim = event.getEntity();
            ItemStack item = killer.getEquipment().getItemInMainHand();
            VariousEnchantmentActions.createWeaponKillActions(victim, item, event.getDrops());
        }
    }

    // Cancellation of bleeding when snacking on food.
    @EventHandler
    public void onPlayerEating(PlayerItemConsumeEvent event) {
        if (VariousEnchantmentActions.bleedOutEntities.size() > 0 &&
                VariousEnchantmentActions.bleedOutEntities.containsKey(event.getPlayer().getUniqueId()) &&
                !VariousEnchantmentActions.bleedOutEntities.get(event.getPlayer().getUniqueId()).isCancelled()) {
            VariousEnchantmentActions.bleedOutEntities.get(event.getPlayer().getUniqueId()).cancel();
            VariousEnchantmentActions.bleedOutEntities.remove(event.getPlayer().getUniqueId());
        }
    }
}

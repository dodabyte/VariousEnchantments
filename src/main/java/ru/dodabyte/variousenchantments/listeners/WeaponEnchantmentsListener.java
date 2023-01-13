package ru.dodabyte.variousenchantments.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;

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
        if (VariousEnchantmentActions.bleedOutPlayers.size() > 0 &&
                !VariousEnchantmentActions.bleedOutPlayers.get(event.getPlayer().getUniqueId()).isCancelled()) {
            VariousEnchantmentActions.bleedOutPlayers.get(event.getPlayer().getUniqueId()).cancel();
            VariousEnchantmentActions.bleedOutPlayers.remove(event.getPlayer().getUniqueId());
        }
    }
}

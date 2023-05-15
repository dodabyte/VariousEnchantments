package ru.dodabyte.variousenchantments.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;

import java.util.ArrayList;
import java.util.List;

public class ArmorEnchantmentsListener implements Listener {

    public static List<Player> playersList = new ArrayList<>();
    public static List<LivingEntity> mobsList = new ArrayList<>();

    public static void activatingPassiveEnchantments() {
        for (LivingEntity player : playersList) {
            if (player.getEquipment().getChestplate() != null) {
                VariousEnchantmentActions.createChestplateActions(player, player.getEquipment().getChestplate());
            }
            if (player.getEquipment().getBoots() != null) {
                VariousEnchantmentActions.createBootsActions(player, player.getEquipment().getBoots());
            }
        }
        for (LivingEntity mob : mobsList) {
            if (mob.getEquipment().getBoots() != null) {
                VariousEnchantmentActions.createBootsActions(mob, mob.getEquipment().getBoots());
            }
        }
    }

    public static void schedulerCheckArmor() {
        Bukkit.getScheduler().runTaskTimer(VariousEnchantmentsMain.getProvidingPlugin(VariousEnchantmentsMain.class), (task) -> {
            activatingPassiveEnchantments();
        }, 0L, 1L);
    }

    @EventHandler
    public void onJoinPlayer(PlayerJoinEvent event) {
        playersList.add(event.getPlayer());
    }

    @EventHandler
    public void onSpawnEntity(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            mobsList.add((LivingEntity) event.getEntity());
        }
    }

    @EventHandler
    public void onDisconnectPlayer(PlayerQuitEvent event) {
        playersList.remove(event.getPlayer());
    }

    @EventHandler
    public void onDeathEntity(EntityDeathEvent event) {
        mobsList.remove(event.getEntity());
    }
}

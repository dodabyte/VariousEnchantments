package ru.dodabyte.variousenchantments.gui;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuManagerSystem {

    private static final Map<Player, List<ItemStack>> playerItems = new HashMap<>();

    public static void addItems(Player player) {
        List<ItemStack> itemsInInventory = new ArrayList<>();
        for (ItemStack itemInInventory : player.getInventory().getContents()) {
            if (itemInInventory != null && (EnchantmentTarget.ARMOR.includes(itemInInventory) ||
                    EnchantmentTarget.WEAPON.includes(itemInInventory) ||
                    EnchantmentTarget.BOW.includes(itemInInventory) ||
                    EnchantmentTarget.TOOL.includes(itemInInventory))) {
                itemsInInventory.add(itemInInventory);
            }
        }
        playerItems.put(player, itemsInInventory);
    }

    public static List<ItemStack> getPlayerItems(Player player) {
        if (!playerItems.containsKey(player)) {
            addItems(player);
        }
        return playerItems.get(player);
    }

    public static void removeItems(Player player) {
        try {
            playerItems.remove(player);
        }
        catch (NullPointerException ignored) {}
    }
}

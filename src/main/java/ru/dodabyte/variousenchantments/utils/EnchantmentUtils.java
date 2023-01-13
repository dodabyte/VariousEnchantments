package ru.dodabyte.variousenchantments.utils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EnchantmentUtils {

    public static boolean hasEnchantment(ItemStack item, Enchantment enchantment){
            return item != null && item.getItemMeta() != null && item.getItemMeta().getEnchants().size() > 0 &&
                    item.getEnchantments().containsKey(Enchantment.getByKey(enchantment.getKey()));
    }

    public static int getLevel(ItemStack item, Enchantment enchantment){
        if(item.getItemMeta() != null && item.getItemMeta().getEnchants().size() > 0 &&
                item.getEnchantments().containsKey(Enchantment.getByKey(enchantment.getKey()))) {
            ItemMeta meta = item.getItemMeta();
            Map<Enchantment, Integer> enchantments = meta.getEnchants();
//            if (item.getType() == Material.ENCHANTED_BOOK) {
//                enchantments = ((EnchantmentStorageMeta) meta).getStoredEnchants();
//            }
            for (Entry<Enchantment, Integer> currentEnchantment : enchantments.entrySet()) {
                if (currentEnchantment.getKey().equals(enchantment)) return currentEnchantment.getValue();
            }
        }
        return 0;
    }

    public static boolean possibleEnchant(ItemStack item, Enchantment enchantment) {
        return enchantment.getItemTarget().includes(item);
    }

    public static void addUnsafeVariousEnchantment(ItemStack item, Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);

        setEnchantmentLore(item, enchantment, level);
    }

    public static void updateUnsafeVariousEnchantment(ItemStack item, Enchantment enchantment, int level) {
        removeEnchantmentLore(item, enchantment, getLevel(item, enchantment));
        item.removeEnchantment(enchantment);

        item.addUnsafeEnchantment(enchantment, level);
        setEnchantmentLore(item, enchantment, level);
    }

    private static void setEnchantmentLore(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (itemMeta != null && itemMeta.getLore() != null) {
            lore = itemMeta.getLore();
        }

        lore.add(ChatColor.LIGHT_PURPLE + VariousEnchantment.getEnchantmentFullName(enchantment, level));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    private static void removeEnchantmentLore(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();

        for (String currentLoreEnchantment : lore) {
            if (currentLoreEnchantment.equals(ChatColor.LIGHT_PURPLE + VariousEnchantment.getEnchantmentFullName(enchantment, level))) {
                lore.remove(currentLoreEnchantment);
                break;
            }
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
}

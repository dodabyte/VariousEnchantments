package ru.dodabyte.variousenchantments.utils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentWrapper;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EnchantmentUtils {
    private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

    public static boolean hasEnchantment(ItemStack item, Enchantment enchantment) {
            return item != null && item.getItemMeta() != null && item.getItemMeta().getEnchants().size() > 0 &&
                    item.getEnchantments().containsKey(Enchantment.getByKey(enchantment.getKey()));
    }

    public static int getLevel(ItemStack item, Enchantment enchantment) {
        if (hasEnchantment(item, enchantment)) {
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

    public static void addUnsafeVariousEnchantment(ItemStack item, Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        setEnchantmentLore(item, enchantment, level);
    }

    public static void addUnsafeVariousEnchantmentInMenu(ItemStack item, Enchantment enchantment, int level, boolean isConflict) {
        item.addUnsafeEnchantment(enchantment, level);
        setEnchantmentLore(item, enchantment, level, isConflict);
    }

    public static void updateUnsafeVariousEnchantment(ItemStack item, Enchantment enchantment, int level) {
        removeEnchantmentLore(item, enchantment, getLevel(item, enchantment));
        item.removeEnchantment(enchantment);

        item.addUnsafeEnchantment(enchantment, level);
        setEnchantmentLore(item, enchantment, level);
    }

    public static void removeVariousEnchantment(ItemStack item, Enchantment enchantment) {
        removeEnchantmentLore(item, enchantment, getLevel(item, enchantment));
        item.removeEnchantment(enchantment);
    }

    private static void setEnchantmentLore(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (itemMeta != null && itemMeta.getLore() != null) {
            lore = itemMeta.getLore();
        }

        lore.add(ChatColor.LIGHT_PURPLE + getEnchantmentFullName(enchantment, level));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    private static void setEnchantmentLore(ItemStack item, Enchantment enchantment, int level, boolean isConflict) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (itemMeta != null && itemMeta.getLore() != null) {
            lore = itemMeta.getLore();
        }

        ChatColor color;
        if (isConflict) {
            color = ChatColor.GRAY;
            String[] string = Configurations.getLanguage().translate("error.conflict_enchant").split(" ");
            lore.add(color + "" + ChatColor.ITALIC + string[0] + " " + string[1] + " " + string[2] + " " +
                    string[3] + " " + string[4]);
            lore.add(color + "" + ChatColor.ITALIC + string[5] + " " + string[6] + " " + string[7]);
            lore.add("");
        }
        else color = ChatColor.LIGHT_PURPLE;

        lore.add(color + getEnchantmentFullName(enchantment, level));

        if (Configurations.getConfig().isEnableDescription()) {
            lore.add("");
            lore.add(color + "" + ChatColor.ITALIC + ((VariousEnchantmentWrapper) enchantment).getDescription());
        }

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    private static void removeEnchantmentLore(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();

        if (lore != null) {
            for (String currentLoreEnchantment : lore) {
                if (currentLoreEnchantment.contains(getEnchantmentFullName(enchantment, level))) {
                    lore.remove(currentLoreEnchantment);
                    break;
                }
            }
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static String getEnchantmentFullName(Enchantment enchantment, int enchantmentLevel){
        if (enchantmentLevel == 1 && enchantment.getMaxLevel() == 1){
            return enchantment.getName();
        }
        if (enchantmentLevel > 10 || enchantmentLevel <= 0){
            return enchantment.getName() + " " + enchantmentLevel;
        }
        return enchantment.getName() + " " + NUMERALS[enchantmentLevel- 1];
    }
}
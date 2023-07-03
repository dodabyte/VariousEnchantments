package ru.dodabyte.variousenchantments.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LanguageConfiguration {
    private FileConfiguration fileLanguageConfiguration;

    public LanguageConfiguration(String locale){
        if (locale.contains(File.separator)) throw new RuntimeException("Locale name is not valid");
        if (locale.equals("system")) locale = System.getProperty("user.language");

        InputStream inputStream = null;
        inputStream = VariousEnchantmentsMain.getPlugin().getResource(locale + ".yml");
        if (inputStream == null) {
            inputStream = VariousEnchantmentsMain.getPlugin().getResource("en_us.yml");
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            fileLanguageConfiguration = YamlConfiguration.loadConfiguration(reader);
        }
        catch (NullPointerException e) { System.out.println("Error in InputStreamReader: " + e); }
    }

    public String translate(String key) {
        return getFileLanguageConfiguration().getString(key);
    }

    public void checkItemsLocalization(Player player) {
        for (Enchantment enchantment : VariousEnchantment.getEnchantments()) {
            for (ItemStack itemStack : player.getInventory()) {
                if (EnchantmentUtils.hasEnchantment(itemStack, enchantment) && !isTrueLanguage(itemStack, enchantment)) {
                    if (VariousEnchantment.isRegisteredEnchantment(enchantment)) {
                        EnchantmentUtils.updateUnsafeVariousEnchantment(itemStack, enchantment,
                                EnchantmentUtils.getLevel(itemStack, enchantment));
                    }
                    else {
                        EnchantmentUtils.removeVariousEnchantment(itemStack, enchantment);
                    }
                }
            }
        }
    }

    public boolean isTrueLanguage(ItemStack itemStack, Enchantment enchantment) {
        return itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null &&
                itemStack.getItemMeta().getLore().contains(getEnchantmentName(enchantment.getKey().getKey()));
    }

    public String getEnchantmentName(String enchantmentKey) {
        return translate("enchantment.various_enchantments." + enchantmentKey + ".name");
    }

    public String getEnchantmentDescription(String enchantmentKey) {
        return translate("enchantment.various_enchantments." + enchantmentKey + ".description");
    }

    public String getType(String enchantmentKey) {
        return translate("enchantment.types.various_enchantments." + enchantmentKey);
    }

    public FileConfiguration getFileLanguageConfiguration() {
        return fileLanguageConfiguration;
    }
}

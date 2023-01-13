package ru.dodabyte.variousenchantments.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VariousEnchantment {

    private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

    private static final List<Enchantment> enchantments = new ArrayList<>();

    private static final List<Enchantment> registeredEnchantments = new ArrayList<>();

    // TODO сделать конфиг в ресурсах с таргетами и зачарами, а также уровни

    // Weapon enchantments
    public static Enchantment BLEEDING = new VariousEnchantmentWrapper("bleeding", 3, EnchantmentTarget.WEAPON);
    public static Enchantment SPECTRAL = new VariousEnchantmentWrapper("spectral", 1, EnchantmentTarget.WEAPON);
    public static Enchantment BEHEADING = new VariousEnchantmentWrapper("beheading", 3, EnchantmentTarget.WEAPON);
    public static Enchantment DAZE = new VariousEnchantmentWrapper("daze", 3, EnchantmentTarget.WEAPON);

    // Bow enchantments
    public static Enchantment ENDER = new VariousEnchantmentWrapper("ender", 1, EnchantmentTarget.BOW);
    public static Enchantment EXPLOSION = new VariousEnchantmentWrapper("explosion", 3, EnchantmentTarget.BOW);
    public static Enchantment LIGHTNING = new VariousEnchantmentWrapper("lightning", 1, EnchantmentTarget.BOW);

    // Boots enchantments
    public static Enchantment JUMPING = new VariousEnchantmentWrapper("jumping", 3, EnchantmentTarget.ARMOR_FEET);
    public static Enchantment SPEED = new VariousEnchantmentWrapper("speed", 3, EnchantmentTarget.ARMOR_FEET);

    public static void addEnchantments() {
        if (getEnchantments().size() > 0) return;
        getEnchantments().addAll(Arrays.asList(
                BLEEDING,
                SPECTRAL,
                BEHEADING,
                DAZE,
                ENDER,
                EXPLOSION,
                LIGHTNING,
                JUMPING,
                SPEED
        ));
    }

    public static void registerEnchantments() {
        for (Enchantment enchantment : getEnchantments()) {
            try {
                Field field = Enchantment.class.getDeclaredField("acceptingNew");
                field.setAccessible(true);
                field.set(null, true);
                Enchantment.registerEnchantment(enchantment);
                getRegisteredEnchantments().add(enchantment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public static List<Enchantment> getRegisteredEnchantments() {
        return registeredEnchantments;
    }

    public static Enchantment getVariousEnchantment(Enchantment enchant) {
        for(Enchantment enchantment: enchantments)
            if (enchant == enchantment) return enchantment;
        return null;
    }

    public static void unregisterEnchantments() {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            for (Enchantment enchantment : getRegisteredEnchantments()){
                if(byKey.containsKey(enchantment.getKey())) {
                    byKey.remove(enchantment.getKey());
                }
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            for (Enchantment enchantment : getRegisteredEnchantments()){
                if(byName.containsKey(enchantment.getName())) {
                    byName.remove(enchantment.getName());
                }
            }
        } catch (Exception ignored) { }
    }

    public static String getEnchantmentFullName(Enchantment enchantment, int enchantmentLevel){
        if(enchantmentLevel == 1 && enchantment.getMaxLevel() == 1){
            return enchantment.getName();
        }
        if(enchantmentLevel > 10 || enchantmentLevel <= 0){
            return enchantment.getName() + " enchantment.level." + enchantmentLevel;
        }

        return enchantment.getName() + " " + NUMERALS[enchantmentLevel- 1];
    }
}

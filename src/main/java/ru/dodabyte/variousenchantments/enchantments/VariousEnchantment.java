package ru.dodabyte.variousenchantments.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.lang.reflect.Field;
import java.util.*;

public class VariousEnchantment {
    private static final List<Enchantment> enchantments = new ArrayList<>();
    private static final List<Enchantment> registeredEnchantments = new ArrayList<>();

    // Weapon enchantments
    public static Enchantment BLEEDING = new VariousEnchantmentWrapper("bleeding", EnchantmentTarget.WEAPON);
    public static Enchantment SPECTRAL = new VariousEnchantmentWrapper("spectral", EnchantmentTarget.WEAPON);
    public static Enchantment BEHEADING = new VariousEnchantmentWrapper("beheading", EnchantmentTarget.WEAPON);
    public static Enchantment DAZE = new VariousEnchantmentWrapper("daze", EnchantmentTarget.WEAPON);

    // Tool enchantments
    public static Enchantment HOOK = new VariousEnchantmentWrapper("hook", EnchantmentTarget.TOOL, "HOE");
    public static Enchantment MAGNETISM = new VariousEnchantmentWrapper("magnetism", EnchantmentTarget.TOOL);

    // Bow enchantments
    public static Enchantment ENDER = new VariousEnchantmentWrapper("ender", EnchantmentTarget.BOW);
    public static Enchantment EXPLOSION = new VariousEnchantmentWrapper("explosion", EnchantmentTarget.BOW);
    public static Enchantment LIGHTNING = new VariousEnchantmentWrapper("lightning", EnchantmentTarget.BOW);
    public static Enchantment POISON_CLOUD = new VariousEnchantmentWrapper("poison_cloud", EnchantmentTarget.BOW);
    public static Enchantment FREEZING = new VariousEnchantmentWrapper("freezing", EnchantmentTarget.BOW);

    // Boots enchantments
    public static Enchantment JUMPING = new VariousEnchantmentWrapper("jumping", EnchantmentTarget.ARMOR_FEET);
    public static Enchantment SPEED = new VariousEnchantmentWrapper("speed", EnchantmentTarget.ARMOR_FEET);

    // Chestplate enchantments
    public static Enchantment HEAL = new VariousEnchantmentWrapper("heal", EnchantmentTarget.ARMOR_TORSO);

    // Shield enchantments
    public static Enchantment THUNDERCLAP = new VariousEnchantmentWrapper("thunderclap", EnchantmentTarget.WEARABLE,
            "SHIELD");
    public static Enchantment SHIELD_BASH = new VariousEnchantmentWrapper("shield_bash", EnchantmentTarget.WEARABLE,
            "SHIELD");

    public static void addEnchantments() {
        if (getEnchantments().size() > 0) return;
        getEnchantments().addAll(Arrays.asList(
                BLEEDING,
                SPECTRAL,
                BEHEADING,
                DAZE,
                HOOK,
                MAGNETISM,
                ENDER,
                EXPLOSION,
                LIGHTNING,
                POISON_CLOUD,
                FREEZING,
                HEAL,
                JUMPING,
                SPEED,
                THUNDERCLAP,
                SHIELD_BASH
        ));
    }

    public static void registerEnchantments() {
        for (Enchantment enchantment : getEnchantments()) {
            try {
                if (Configurations.getConfig().isEnableEnchantment(enchantment.getKey().getKey())) {
                    Field field = Enchantment.class.getDeclaredField("acceptingNew");
                    field.setAccessible(true);
                    field.set(null, true);
                    Enchantment.registerEnchantment(enchantment);
                    getRegisteredEnchantments().add(enchantment);
                }
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

    public static boolean isRegisteredEnchantment(Enchantment enchantment) {
        return getRegisteredEnchantments().contains(enchantment);
    }

    public static Enchantment getVariousEnchantment(Enchantment enchant) {
        for (Enchantment enchantment: getRegisteredEnchantments())
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
}

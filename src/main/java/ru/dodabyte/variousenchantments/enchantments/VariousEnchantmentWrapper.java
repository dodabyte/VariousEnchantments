package ru.dodabyte.variousenchantments.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.List;

public class VariousEnchantmentWrapper extends Enchantment {
    private final String name;
    private final String description;
    private int maxLevel = 1;
    private final EnchantmentTarget itemTarget;
    private String itemType;
    private boolean isTreasure = false, isCursed = false;

    public VariousEnchantmentWrapper(String namespace, int maxLevel, EnchantmentTarget itemTarget,
                                     boolean isTreasure, boolean isCursed) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().getEnchantmentName(namespace);
        this.description = Configurations.getLanguage().getEnchantmentDescription(namespace);
        this.maxLevel = maxLevel;
        this.itemTarget = itemTarget;
        this.isTreasure = isTreasure;
        this.isCursed = isCursed;
    }

    public VariousEnchantmentWrapper(String namespace, int maxLevel, EnchantmentTarget itemTarget, String itemType) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().getEnchantmentName(namespace);
        this.description = Configurations.getLanguage().getEnchantmentDescription(namespace);
        this.maxLevel = maxLevel;
        this.itemTarget = itemTarget;
        this.itemType = itemType;
    }

    public VariousEnchantmentWrapper(String namespace, int maxLevel, EnchantmentTarget itemTarget) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().getEnchantmentName(namespace);
        this.description = Configurations.getLanguage().getEnchantmentDescription(namespace);
        this.maxLevel = maxLevel;
        this.itemTarget = itemTarget;
    }

    public VariousEnchantmentWrapper(String namespace, EnchantmentTarget itemTarget) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().getEnchantmentName(namespace);
        this.description = Configurations.getLanguage().getEnchantmentDescription(namespace);
        this.itemTarget = itemTarget;
        this.maxLevel = Configurations.getConfig().getMaxLevel(namespace);
    }

    public VariousEnchantmentWrapper(String namespace, EnchantmentTarget itemTarget, String itemType) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().getEnchantmentName(namespace);
        this.description = Configurations.getLanguage().getEnchantmentDescription(namespace);
        this.itemTarget = itemTarget;
        this.maxLevel = Configurations.getConfig().getMaxLevel(namespace);
        this.itemType = itemType;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @NotNull
    @Override
    public EnchantmentTarget getItemTarget() {
        return itemTarget;
    }

    @Override
    public boolean isTreasure() {
        return isTreasure;
    }

    @Override
    public boolean isCursed() {
        return isCursed;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment otherEnchantment) {
        Enchantment enchantment = getVariousEnchantment();
        List<String> conflictsEnchantmentsList = Configurations.getConfig().getConflicts(enchantment.getKey().getKey());
        return conflictsEnchantmentsList != null && !conflictsEnchantmentsList.contains(otherEnchantment.getKey().getKey());
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        Enchantment enchantment = getVariousEnchantment();
        if (getItemType() == null) return enchantment.getItemTarget().includes(item);
        else return enchantment.getItemTarget().includes(item) && item.getType().toString().contains(getItemType());
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return super.getKey();
    }

    private Enchantment getVariousEnchantment() {
        return VariousEnchantment.getVariousEnchantment(this);
    }

//    public String firstUpperCase(String word){
//        if(word == null || word.isEmpty()) return "";
//        word = word.replaceAll(" ", "_");
//        return word.substring(0, 1).toUpperCase() + word.substring(1);
//    }

    public String getItemType() { return itemType; }

    public String getFormattedType() {
        if (itemType == null)
            return getItemTarget().toString();
        else
            return itemType;
    }
}

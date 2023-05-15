package ru.dodabyte.variousenchantments.enchantments;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

public class VariousEnchantmentWrapper extends Enchantment {

    private final String name;
    private int maxLevel = 0;
    private final EnchantmentTarget itemTarget;
    private String itemType;
    private boolean isTreasure = false, isCursed = false;

    public VariousEnchantmentWrapper(String namespace, int maxLevel,
                                     EnchantmentTarget itemTarget, boolean isTreasure, boolean isCursed) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().translate(
                "enchantment.display_names.various_enchantments." + namespace);
        this.maxLevel = maxLevel;
        this.itemTarget = itemTarget;
        this.isTreasure = isTreasure;
        this.isCursed = isCursed;
    }

    public VariousEnchantmentWrapper(String namespace, int maxLevel, EnchantmentTarget itemTarget, String itemType) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().translate(
                "enchantment.display_names.various_enchantments." + namespace);
        this.maxLevel = maxLevel;
        this.itemTarget = itemTarget;
        this.itemType = itemType;
    }

    public VariousEnchantmentWrapper(String namespace, int maxLevel, EnchantmentTarget itemTarget) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().translate(
                "enchantment.display_names.various_enchantments." + namespace);
        this.maxLevel = maxLevel;
        this.itemTarget = itemTarget;
    }

    public VariousEnchantmentWrapper(String namespace, EnchantmentTarget itemTarget) {
        super(new NamespacedKey(VariousEnchantmentsMain.getPlugin(), namespace));
        this.name = Configurations.getLanguage().translate(
                "enchantment.display_names.various_enchantments." + namespace);
        this.itemTarget = itemTarget;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

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
    public boolean conflictsWith(@NotNull Enchantment other) {
        Enchantment enchantment = getVariousEnchantment();
        Enchantment conflictEnchantment = VariousEnchantment.getVariousEnchantment(other);
        return enchantment != null && conflictEnchantment != null && enchantment.conflictsWith(conflictEnchantment);
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        if (getItemType() == null) return true;
        else return item.getType().name().contains(getItemType());
        //getVariousEnchantment().canEnchantItem(item);
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return super.getKey();
    }

    private Enchantment getVariousEnchantment() {
        return VariousEnchantment.getVariousEnchantment(this);
    }

    public String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";
        word = word.replaceAll(" ", "_");
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public String getItemType() { return itemType; }
}

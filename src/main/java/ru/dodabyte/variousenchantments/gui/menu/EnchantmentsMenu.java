package ru.dodabyte.variousenchantments.gui.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.gui.PaginatedMenu;
import ru.dodabyte.variousenchantments.utils.ChatUtils;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.List;

public class EnchantmentsMenu extends PaginatedMenu {

    private final ItemStack chosenItem;

    public EnchantmentsMenu(Player player, ItemStack chosenItem) {
        super(player);
        this.chosenItem = chosenItem;
    }

    @Override
    public String getMenuName() {
        return "Choose an Enchantment";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (event.getClickedInventory().equals(this.getInventory())) {
            Player player = (Player) event.getWhoClicked();

            List<Enchantment> enchantmentsList = VariousEnchantment.getRegisteredEnchantments();

            if (event.getCurrentItem() != null && (event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK) ||
                    event.getCurrentItem().getType().equals(Material.BOOK))) {
                for (Enchantment enchantment : enchantmentsList) {
                    if (EnchantmentUtils.hasEnchantment(event.getCurrentItem(), enchantment)) {
                        int level = EnchantmentUtils.getLevel(event.getCurrentItem(), enchantment);

                        boolean isConflict = false;
                        for (Enchantment otherEnchantment : chosenItem.getEnchantments().keySet()) {
                            if (otherEnchantment != null && !enchantment.conflictsWith(otherEnchantment)) {
                                isConflict = true;
                            }
                        }

                        if (isConflict) {
                            ChatUtils.printError(player, Configurations.getLanguage().translate("error.conflict_enchant"));
                            break;
                        }

                        for (ItemStack itemInInventory : player.getInventory().getContents()) {
                            if (itemInInventory != null && itemInInventory.equals(chosenItem)) {
                                if (EnchantmentUtils.hasEnchantment(itemInInventory, enchantment)) {
                                    EnchantmentUtils.updateUnsafeVariousEnchantment(itemInInventory, enchantment, level);
                                }
                                else {
                                    EnchantmentUtils.addUnsafeVariousEnchantment(itemInInventory, enchantment, level);
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                new ItemsToEnchantMenu(player).open();
            } else if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                new ItemsToEnchantMenu(player).open();
            } else if (event.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)) {
                if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())
                        .equalsIgnoreCase("Left")) {
                    if (page == 0) {
                        ChatUtils.printError(player, Configurations.getLanguage().translate("error.first_page_inventory"));
                    } else {
                        page = page - 1;
                        super.open();
                    }
                } else if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())
                        .equalsIgnoreCase("Right")) {
                    if (!((index + 1) >= enchantmentsList.size())) {
                        page = page + 1;
                        super.open();
                    } else {
                        ChatUtils.printError(player, Configurations.getLanguage().translate("error.last_page_inventory"));
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems(Player player) {
        addMenuBorder();

        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if (index >= VariousEnchantment.getRegisteredEnchantments().size()) break;
            Enchantment enchantment = VariousEnchantment.getRegisteredEnchantments().get(index);

            if (enchantment.canEnchantItem(chosenItem)) {
                boolean isConflict = false;
                for (Enchantment otherEnchantment : chosenItem.getEnchantments().keySet()) {
                    if (otherEnchantment != null && !enchantment.conflictsWith(otherEnchantment)) {
                        isConflict = true;
                    }
                }

                int level = 1;
                for (ItemStack itemInInventory : player.getInventory().getContents()) {
                    if (itemInInventory != null && itemInInventory.equals(chosenItem)) {
                        if (EnchantmentUtils.hasEnchantment(itemInInventory, enchantment)) {
                            level = EnchantmentUtils.getLevel(itemInInventory, enchantment) + 1;
                        }
                    }
                }

                for (; level <= enchantment.getMaxLevel(); level++) {
                    ItemStack playerItem;
                    if (isConflict) {
                        playerItem = new ItemStack(Material.BOOK);
                    }
                    else {
                        playerItem = new ItemStack(Material.ENCHANTED_BOOK);
                    }

                    EnchantmentUtils.addUnsafeVariousEnchantmentInMenu(playerItem, enchantment, level, isConflict);

                    inventory.addItem(playerItem);
                }
            }
        }
    }
}

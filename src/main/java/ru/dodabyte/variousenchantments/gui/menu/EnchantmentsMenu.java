package ru.dodabyte.variousenchantments.gui.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.gui.PaginatedMenu;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;

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
        if (event.getInventory().equals(this.getInventory())) {
            Player player = (Player) event.getWhoClicked();

            List<Enchantment> enchantmentList = VariousEnchantment.getRegisteredEnchantments();

            if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)) {
                for (Enchantment enchantment : enchantmentList) {
                    if (EnchantmentUtils.hasEnchantment(event.getCurrentItem(), enchantment)) {
                        int level = EnchantmentUtils.getLevel(event.getCurrentItem(), enchantment);

                        for (ItemStack itemInInventory : player.getInventory().getContents()) {
                            if (itemInInventory != null && itemInInventory.equals(chosenItem)) {
                                EnchantmentUtils.addUnsafeVariousEnchantment(itemInInventory, enchantment, level);
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
                        player.sendMessage(ChatColor.GRAY + "You are already on the first page.");
                    } else {
                        page = page - 1;
                        super.open();
                    }
                } else if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())
                        .equalsIgnoreCase("Right")) {
                    if (!((index + 1) >= enchantmentList.size())) {
                        page = page + 1;
                        super.open();
                    } else {
                        player.sendMessage(ChatColor.GRAY + "You are on the last page.");
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
            if (index >= VariousEnchantment.getEnchantments().size()) break;
            Enchantment enchantment = VariousEnchantment.getRegisteredEnchantments().get(index);

            if (EnchantmentUtils.possibleEnchant(chosenItem, enchantment)) {

                // TODO сделать так, чтобы зачар был сначала только 1 уровня, а далее по возрастанию в зависимости
                // TODO от текущего зачара

                for (int level = 1; level <= enchantment.getMaxLevel(); level++) {
                    ItemStack playerItem = new ItemStack(Material.ENCHANTED_BOOK, 1);

                    EnchantmentUtils.addUnsafeVariousEnchantment(playerItem, enchantment, level);

                    inventory.addItem(playerItem);
                }
            }
        }
    }
}

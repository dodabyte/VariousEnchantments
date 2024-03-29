package ru.dodabyte.variousenchantments.gui.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dodabyte.variousenchantments.gui.MenuManagerSystem;
import ru.dodabyte.variousenchantments.gui.PaginatedMenu;
import ru.dodabyte.variousenchantments.utils.ChatUtils;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.List;

public class ItemsToEnchantMenu extends PaginatedMenu {

    public ItemsToEnchantMenu(Player player) {
        super(player);
    }

    @Override
    public String getMenuName() {
        return "Choose an Item to Enchant";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (event.getClickedInventory().equals(this.getInventory())) {
            Player player = (Player) event.getWhoClicked();

            List<ItemStack> items = MenuManagerSystem.getPlayerItems(player);

            ItemStack clickedItem = event.getCurrentItem();
            if (event.getCurrentItem() != null && (EnchantmentTarget.WEAPON.includes(clickedItem) ||
                    EnchantmentTarget.ARMOR.includes(clickedItem) || EnchantmentTarget.BOW.includes(clickedItem) ||
                    EnchantmentTarget.TOOL.includes(clickedItem) || EnchantmentTarget.WEARABLE.includes(clickedItem))) {
                new EnchantmentsMenu(player, clickedItem).open();
            } else if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                player.closeInventory();
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
                    if (!((index + 1) >= items.size())) {
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
        List<ItemStack> items = MenuManagerSystem.getPlayerItems(player);

        addMenuBorder();

        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if (index >= items.size()) break;
            if (items.get(index) != null) {
                ItemStack playerItem = new ItemStack(items.get(index));
                ItemMeta itemMeta = items.get(index).getItemMeta();

                playerItem.setItemMeta(itemMeta);

                inventory.addItem(playerItem);
            }
        }
    }
}

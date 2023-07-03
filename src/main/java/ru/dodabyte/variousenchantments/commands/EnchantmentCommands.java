package ru.dodabyte.variousenchantments.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.utils.ChatUtils;
import ru.dodabyte.variousenchantments.utils.EnchantmentUtils;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.Objects;

public class EnchantmentCommands implements CommandExecutor {

    public EnchantmentCommands(VariousEnchantmentsMain plugin) {
        Objects.requireNonNull(plugin.getCommand("variousenchant")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("Only a player can use this command.");
            return true;
        }

        if (args.length < 1) {
            ChatUtils.printEnchantmentsList(player);
            ChatUtils.printError(player, Configurations.getLanguage().translate("error.enter_name_enchantment"));
            return true;
        }
        if (args.length < 2) {
            ChatUtils.printError(player, Configurations.getLanguage().translate("error.unknown_level_argument"));
            return true;
        }

        if (ChatUtils.checkInvalidArgument(args, label, 2, player)) return true;

        int level = 1;
        try {
            level = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            ChatUtils.printError(player, Configurations.getLanguage().translate("error.unknown_level_argument"));
        }

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.equals(new ItemStack(Material.AIR))) {
            ChatUtils.printError(player, Configurations.getLanguage().translate("error.air_in_main_hand"));
            return true;
        }

        for (Enchantment enchantment : VariousEnchantment.getRegisteredEnchantments()) {
            if (args[0].equalsIgnoreCase(enchantment.getKey().getKey())) {
                if (level < 1) {
                    ChatUtils.printError(player, Configurations.getLanguage().translate("error.min_level"));
                }
                else if (level > enchantment.getMaxLevel()) {
                    ChatUtils.printError(player, Configurations.getLanguage().translate("error.max_level"));
                }
                else if (!EnchantmentUtils.hasEnchantment(itemInMainHand, enchantment)) {
                    if (enchantment.canEnchantItem(itemInMainHand)) {
                        EnchantmentUtils.addUnsafeVariousEnchantment(itemInMainHand, enchantment, level);
                    }
                    else {
                        ChatUtils.printError(player,
                                Configurations.getLanguage().translate("error.cannot_enchant"));
                    }
                }
                else if (EnchantmentUtils.getLevel(itemInMainHand, enchantment) != level) {
                    EnchantmentUtils.updateUnsafeVariousEnchantment(itemInMainHand, enchantment, level);
                }
                else {
                    ChatUtils.printError(player,
                            Configurations.getLanguage().translate("error.already_enchantment_level"));
                }

                return true;

                // TODO проверить:
                //  можно ли зачарить на конкретный зачар,
                //  хватает ли опыта и лазурита.
            }
        }

        ChatUtils.printError(player, Configurations.getLanguage().translate("error.no_such_enchantment"));
        return true;
    }
}

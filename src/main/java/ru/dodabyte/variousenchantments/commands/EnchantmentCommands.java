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
import ru.dodabyte.variousenchantments.utils.CommandUtils;
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
            CommandUtils.printEnchantmentsList(player);
            CommandUtils.printError(player, Configurations.getLanguage().translate("errors.enter_name_enchantment"));
            return true;
        }

        int level = 1;
        try {
            if (args.length == 2) {
                level = Integer.parseInt(args[1]);
            }
        }
        catch (NumberFormatException e) {
            CommandUtils.printError(player, Configurations.getLanguage().translate("errors.unknown_level_argument"));
        }

        if (args.length > 2) {
            StringBuilder enteredCommand = new StringBuilder("/" + label);
            for (String arg : args) {
                enteredCommand.append(" ").append(arg);
            }
            String here = ChatColor.ITALIC + "HERE";
            enteredCommand.append("<--[").append(here).append("]");
            CommandUtils.printError(player,
                    Configurations.getLanguage().translate("errors.invalid_argument")
                            + ":\n" + enteredCommand);
            return true;
        }

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.equals(new ItemStack(Material.AIR))) {
            CommandUtils.printError(player, Configurations.getLanguage().translate("errors.air_in_main_hand"));
            return true;
        }

        for (Enchantment enchantment : VariousEnchantment.getEnchantments()) {
            if (args[0].equalsIgnoreCase(enchantment.getName())) {
                if (level > enchantment.getMaxLevel()) {
                    CommandUtils.printError(player, Configurations.getLanguage().translate("errors.max_level"));
                }
                else if (!EnchantmentUtils.hasEnchantment(itemInMainHand, enchantment)) {
                    if (EnchantmentUtils.possibleEnchant(itemInMainHand, enchantment)) {
                        EnchantmentUtils.addUnsafeVariousEnchantment(itemInMainHand, enchantment, level);
                    }
                    else {
                        CommandUtils.printError(player,
                                Configurations.getLanguage().translate("errors.cannot_enchant"));
                    }
                }
                else if (EnchantmentUtils.getLevel(itemInMainHand, enchantment) != level) {
                    EnchantmentUtils.updateUnsafeVariousEnchantment(itemInMainHand, enchantment, level);
                }
                else {
                    CommandUtils.printError(player,
                            Configurations.getLanguage().translate("errors.already_enchantment_level"));
                }

                return true;

                // TODO проверить:
                //  можно ли зачарить на конкретный зачар,
                //  хватает ли опыта и лазурита.
            }
        }

        CommandUtils.printError(player, Configurations.getLanguage().translate("errors.no_such_enchantments"));
        return  true;
    }
}

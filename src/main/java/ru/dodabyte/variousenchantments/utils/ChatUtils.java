package ru.dodabyte.variousenchantments.utils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentWrapper;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static void printHelp(Player player) {
        String message = ChatColor.YELLOW + "--------- " + ChatColor.WHITE +
                    Configurations.getLanguage().translate("ve_help_list") + ChatColor.YELLOW + " ---------\n" +
                ChatColor.GOLD + "/variousenchantments: "
                    + ChatColor.WHITE + Configurations.getLanguage().translate("help.help_command") + "\n" +
                ChatColor.GOLD + "/variousenchantments help: "
                    + ChatColor.WHITE + Configurations.getLanguage().translate("help.help_command") + "\n" +
                ChatColor.GOLD + "/variousenchantments list: "
                    + ChatColor.WHITE + Configurations.getLanguage().translate("help.list_command") + "\n" +
                ChatColor.GOLD + "/variousenchant: "
                    + ChatColor.WHITE + Configurations.getLanguage().translate("help.enchant_command") + "\n" +
                ChatColor.GOLD + "/variousenchantgui: "
                    + ChatColor.WHITE + Configurations.getLanguage().translate("help.enchant_gui_command") + "\n" +
                ChatColor.GOLD + "/variousenchantments reload: "
                + ChatColor.WHITE + Configurations.getLanguage().translate("help.reload_command") + "\n";
        player.sendMessage(message);
    }

    public static void printEnchantmentsList(Player player) {
        StringBuilder message = new StringBuilder(ChatColor.YELLOW + "--------- " + ChatColor.WHITE
                + Configurations.getLanguage().translate("list.ve_enchantments_list")
                + ChatColor.YELLOW + " ---------\n");
        List<String> enchantmentType = new ArrayList<>();
        for (Enchantment enchantment : VariousEnchantment.getRegisteredEnchantments()) {
            if (!enchantmentType.contains(Configurations.getLanguage().getType(((VariousEnchantmentWrapper) enchantment).
                    getFormattedType()))) {
                enchantmentType.add(Configurations.getLanguage().getType(((VariousEnchantmentWrapper) enchantment).
                        getFormattedType()));
                message.append(ChatColor.YELLOW).append("--- ").append(ChatColor.WHITE).
                        append(Configurations.getLanguage().getType(((VariousEnchantmentWrapper) enchantment).
                        getFormattedType())).append(ChatColor.YELLOW).append(" ---").append("\n");
            }
            message.append(ChatColor.GOLD).append(enchantment.getName()).append(" (").
                    append(enchantment.getKey().getKey()).append("): ").append(ChatColor.WHITE).
                    append(((VariousEnchantmentWrapper) enchantment).getDescription()).append("\n");
        }
        player.sendMessage(message.toString());
    }

    public static void printError(Player player, String message) {
        player.sendMessage(ChatColor.RED + message);
    }

    public static void printSuccess(Player player, String message) {
        player.sendMessage(ChatColor.GREEN + message);
    }

    public static boolean checkInvalidArgument(String[] args, String label, int length, Player player) {
        if (args.length > length) {
            StringBuilder enteredCommand = new StringBuilder(label);
            for (String arg : args) {
                enteredCommand.append(" ").append(arg);
            }
            String here = Configurations.getLanguage().translate("error.here");
            enteredCommand.append(ChatColor.ITALIC).append("<--[").append(here).append("]");
            ChatUtils.printError(player,
                    Configurations.getLanguage().translate("error.invalid_argument") +
                            ":\n" + enteredCommand);
            return true;
        }
        return false;
    }
}

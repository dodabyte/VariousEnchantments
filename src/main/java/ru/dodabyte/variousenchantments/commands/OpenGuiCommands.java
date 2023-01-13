package ru.dodabyte.variousenchantments.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.gui.menu.ItemsToEnchantMenu;
import ru.dodabyte.variousenchantments.utils.CommandUtils;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.Objects;

public class OpenGuiCommands implements CommandExecutor {

    public OpenGuiCommands(VariousEnchantmentsMain plugin) {
        Objects.requireNonNull(plugin.getCommand("variousenchantgui")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("Only a player can use this command.");
            return true;
        }

        if (args.length > 0) {
            StringBuilder enteredCommand = new StringBuilder("/" + label);
            for (String arg : args) {
                enteredCommand.append(" ").append(arg);
            }
            String here = ChatColor.ITALIC + "HERE";
            enteredCommand.append("<--[").append(here).append("]");
            CommandUtils.printError(player,
                    Configurations.getLanguage().translate("errors.invalid_argument")
                    + "\n" + enteredCommand);
            return true;
        }

        new ItemsToEnchantMenu(player).open();

        return true;
    }
}

package ru.dodabyte.variousenchantments.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.utils.ChatUtils;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.Objects;

public class GlobalCommands implements CommandExecutor {

    public GlobalCommands(VariousEnchantmentsMain plugin) {
        Objects.requireNonNull(plugin.getCommand("variousenchantments")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
                if (ChatUtils.checkInvalidArgument(args, label, 1, player)) return true;

                ChatUtils.printHelp(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("list")) {
                if (ChatUtils.checkInvalidArgument(args, label, 1, player)) return true;

                ChatUtils.printEnchantmentsList(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (ChatUtils.checkInvalidArgument(args, label, 1, player)) return true;

                Configurations.getConfig().reload();
                ChatUtils.printSuccess(player, Configurations.getLanguage().translate("success.reloaded"));
                return true;
            }

            ChatUtils.printError(player, Configurations.getLanguage().translate("error.command_not_exist"));
        }
        else {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                Configurations.reload();
                System.out.println("The plugin config has been successfully reloaded.");
            }
            else {
                System.out.println("Only a player can use this command.");
            }
        }

        return true;
    }
}

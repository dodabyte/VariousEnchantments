package ru.dodabyte.variousenchantments.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.utils.CommandUtils;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

import java.util.Objects;

public class GlobalCommands implements CommandExecutor {

    public GlobalCommands(VariousEnchantmentsMain plugin) {
        Objects.requireNonNull(plugin.getCommand("variousenchantments")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("Only a player can use this command.");
            return true;
        }

        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            CommandUtils.printHelp(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            CommandUtils.printEnchantmentsList(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            Configurations.reload();
            CommandUtils.printSuccess(player, Configurations.getLanguage().translate("success.reloaded"));
            return true;
        }

        return true;
    }
}

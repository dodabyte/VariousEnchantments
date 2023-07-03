package ru.dodabyte.variousenchantments.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;
import ru.dodabyte.variousenchantments.gui.menu.ItemsToEnchantMenu;
import ru.dodabyte.variousenchantments.utils.ChatUtils;
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

        if (ChatUtils.checkInvalidArgument(args, label, 0, player)) return true;

        new ItemsToEnchantMenu(player).open();

        return true;
    }
}

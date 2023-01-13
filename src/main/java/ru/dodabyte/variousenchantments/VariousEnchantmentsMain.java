package ru.dodabyte.variousenchantments;

import org.bukkit.plugin.java.JavaPlugin;
import ru.dodabyte.variousenchantments.actions.Arrows;
import ru.dodabyte.variousenchantments.commands.EnchantmentCommands;
import ru.dodabyte.variousenchantments.commands.GlobalCommands;
import ru.dodabyte.variousenchantments.commands.OpenGuiCommands;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.listeners.ArmorEnchantmentsListener;
import ru.dodabyte.variousenchantments.listeners.BowEnchantmentsListener;
import ru.dodabyte.variousenchantments.listeners.MenuListener;
import ru.dodabyte.variousenchantments.listeners.WeaponEnchantmentsListener;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

public final class VariousEnchantmentsMain extends JavaPlugin {

    private static VariousEnchantmentsMain PLUGIN;
    private static Configurations CONFIGURATIONS;

    // TODO При запуске проверять какой стоит язык, чтобы поменять все предметы со старого языка, на новый!

    @Override
    public void onEnable() {
        PLUGIN = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        CONFIGURATIONS = Configurations.getConfigurations();
        CONFIGURATIONS.setup();

        VariousEnchantment.addEnchantments();
        VariousEnchantment.registerEnchantments();

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        VariousEnchantment.unregisterEnchantments();
        Configurations.save();
    }

    public void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new WeaponEnchantmentsListener(), this);
        this.getServer().getPluginManager().registerEvents(new BowEnchantmentsListener(), this);
        this.getServer().getPluginManager().registerEvents(new ArmorEnchantmentsListener(), this);
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);

        Arrows.schedulerTeleportArrow();
        ArmorEnchantmentsListener.schedulerCheckArmor();
    }

    public void registerCommands() {
        new GlobalCommands(this);
        new EnchantmentCommands(this);
        new OpenGuiCommands(this);
    }

    public static VariousEnchantmentsMain getPlugin() {
        return PLUGIN;
    }
}

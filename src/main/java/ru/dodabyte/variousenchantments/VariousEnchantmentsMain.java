package ru.dodabyte.variousenchantments;

import org.bukkit.plugin.java.JavaPlugin;
import ru.dodabyte.variousenchantments.actions.CustomArrows;
import ru.dodabyte.variousenchantments.commands.EnchantmentCommands;
import ru.dodabyte.variousenchantments.commands.GlobalCommands;
import ru.dodabyte.variousenchantments.commands.OpenGuiCommands;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantment;
import ru.dodabyte.variousenchantments.listeners.*;
import ru.dodabyte.variousenchantments.tasks.CheckTimers;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

public final class VariousEnchantmentsMain extends JavaPlugin {

    private static VariousEnchantmentsMain PLUGIN;

    @Override
    public void onEnable() {
        PLUGIN = this;

        getConfig().options().copyDefaults();
        Configurations.setup();

        VariousEnchantment.addEnchantments();
        VariousEnchantment.registerEnchantments();

        registerEvents();
        registerCommands();

        checkTimers();
    }

    @Override
    public void onDisable() {
        VariousEnchantment.unregisterEnchantments();
        Configurations.save();
    }

    public void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new WeaponEnchantmentsListener(), this);
        this.getServer().getPluginManager().registerEvents(new WearableEnchantmentsListener(), this);
        this.getServer().getPluginManager().registerEvents(new ToolEnchantmentsListener(), this);
        this.getServer().getPluginManager().registerEvents(new BowEnchantmentsListener(), this);
        this.getServer().getPluginManager().registerEvents(new ArmorEnchantmentsListener(), this);
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);

        CustomArrows.schedulerArrow();
        ArmorEnchantmentsListener.schedulerCheckArmor();
    }

    public void registerCommands() {
        new GlobalCommands(this);
        new EnchantmentCommands(this);
        new OpenGuiCommands(this);
    }

    public void checkTimers() {
        new CheckTimers().runTaskTimer(getPlugin(), 0L, 20L);
    }

    public static VariousEnchantmentsMain getPlugin() {
        return PLUGIN;
    }
}

package ru.dodabyte.variousenchantments.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;

import java.io.File;
import java.io.IOException;

public class Configurations {
    private final static Configurations CONFIGURATIONS = new Configurations();
    private static File file;
    private static FileConfiguration fileConfiguration;
    private static MainConfiguration CONFIG;
    private static LanguageConfiguration LANGUAGE;

    public static Configurations getConfigurations() {
        return CONFIGURATIONS;
    }

    public static void setup() {
        file = new File(VariousEnchantmentsMain.getPlugin().getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                System.out.println("Oops, error creating the file.");
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        CONFIG = new MainConfiguration(fileConfiguration);
        LANGUAGE = new LanguageConfiguration(VariousEnchantmentsMain.getPlugin(), get().getString("language"));

        get().options().copyDefaults(true);
        save();
    }

    public static FileConfiguration get() {
        return fileConfiguration;
    }

    public static void save() {
        try {
            fileConfiguration.save(file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }

    public static void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        LANGUAGE = new LanguageConfiguration(VariousEnchantmentsMain.getPlugin(), get().getString("language"));
    }

    public static LanguageConfiguration getLanguage() {
        return LANGUAGE;
    }
}

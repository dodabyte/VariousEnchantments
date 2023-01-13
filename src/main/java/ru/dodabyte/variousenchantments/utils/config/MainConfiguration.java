package ru.dodabyte.variousenchantments.utils.config;

import org.bukkit.configuration.file.FileConfiguration;

public class MainConfiguration {

    private FileConfiguration fileConfiguration;

    public MainConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;

        get().addDefault("language", "ru_ru");
    }

    public FileConfiguration get() {
        return fileConfiguration;
    }
}

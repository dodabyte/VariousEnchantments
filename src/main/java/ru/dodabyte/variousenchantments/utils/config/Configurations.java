package ru.dodabyte.variousenchantments.utils.config;

public class Configurations {
    private static MainConfiguration CONFIG;
    private static LanguageConfiguration LANGUAGE;

    public static void setup() {
        CONFIG = new MainConfiguration();
        LANGUAGE = new LanguageConfiguration(getConfig().getLanguage());

        save();
    }

    public static void save() {
        getConfig().save();
    }

    public static void reload() {
        CONFIG = new MainConfiguration();
        LANGUAGE = new LanguageConfiguration(getConfig().getLanguage());
    }

    public static MainConfiguration getConfig() { return CONFIG; }

    public static LanguageConfiguration getLanguage() {
        return LANGUAGE;
    }
}

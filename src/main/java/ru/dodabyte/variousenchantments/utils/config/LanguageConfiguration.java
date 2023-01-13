package ru.dodabyte.variousenchantments.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class LanguageConfiguration {
    private FileConfiguration fileLanguageConfiguration;

    public LanguageConfiguration(Plugin plugin, String locale){
        if (locale.contains(File.separator)) throw new RuntimeException("Locale name is not valid");
        if (locale.equals("system")) locale = System.getProperty("user.language");

        InputStream is = null;
        is = plugin.getResource(locale + ".yml");
        if (is == null) {
            is = plugin.getResource("en_us.yml");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        fileLanguageConfiguration = YamlConfiguration.loadConfiguration(reader);
    }

    public String translate(String key) {
        return fileLanguageConfiguration.getString(key);
    }

//    public File getLangFolder(Plugin plugin) {
//        File lang = new File(plugin.getDataFolder(),"languages");
//        lang.mkdirs();
//        return lang;
//    }
}

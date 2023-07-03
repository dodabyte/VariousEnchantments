package ru.dodabyte.variousenchantments.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainConfiguration {
    private File file;
    private FileConfiguration fileConfiguration;

    public MainConfiguration() {
        file = new File(VariousEnchantmentsMain.getPlugin().getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception ignored) {}
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
            setup();
        }
        else fileConfiguration = YamlConfiguration.loadConfiguration(file);
        getFileConfiguration().options().copyDefaults(true);

        save();
        reload();
    }

    public void setup() {
        setLanguage();
        setEnableDescriptions();
        setEnchantmentsOptions();
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save configuration file");
        }
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void setLanguage() {
        getFileConfiguration().set("language", "ru_ru");
    }

    public void setEnableDescriptions() { getFileConfiguration().set("enable_description_in_gui", true); }

    public void setEnchantmentsOptions() {
        getFileConfiguration().createSection("enchantments");
        getFileConfiguration().createSection("enchantments");

        // Weapon enchantments
        setBleedingOptions();
        setSpectralOptions();
        setBeheadingOptions();
        setDazeOptions();

        // Bow enchantments
        setEnderOptions();
        setExplosionOptions();
        setLightningOptions();
        setPoisonCloudOptions();
        setFreezingOptions();

        // Hoe enchantments
        setHookOptions();

        // Tool enchantments
        setMagnetismOptions();

        // Helmet enchantments

        // Chestplate enchantments
        setHealOptions();

        // Leggings enchantments

        // Boots enchantments
        setJumpingOptions();
        setSpeedOptions();

        // Shield enchantments
        setThunderclapEnchantment();
        setShieldBashEnchantment();
    }

    private void setBleedingOptions() {
        getFileConfiguration().createSection("enchantments.bleeding");

        getFileConfiguration().set("enchantments.bleeding.enable", true);

        getFileConfiguration().set("enchantments.bleeding.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.bleeding.max_level", 3);

        getFileConfiguration().createSection("enchantments.bleeding.level_1");
        getFileConfiguration().set("enchantments.bleeding.level_1.period", 5);
        getFileConfiguration().set("enchantments.bleeding.level_1.damage", 2);

        getFileConfiguration().createSection("enchantments.bleeding.level_2");
        getFileConfiguration().set("enchantments.bleeding.level_2.period", 4);
        getFileConfiguration().set("enchantments.bleeding.level_2.damage", 3);

        getFileConfiguration().createSection("enchantments.bleeding.level_3");
        getFileConfiguration().set("enchantments.bleeding.level_3.period", 3);
        getFileConfiguration().set("enchantments.bleeding.level_3.damage", 4);
    }

    private void setSpectralOptions() {
        getFileConfiguration().createSection("enchantments.spectral");

        getFileConfiguration().set("enchantments.spectral.enable", true);

        getFileConfiguration().set("enchantments.spectral.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.spectral.max_level", 1);

        getFileConfiguration().createSection("enchantments.spectral.level_1");
        getFileConfiguration().set("enchantments.spectral.level_1.time", 10);
        getFileConfiguration().set("enchantments.spectral.level_1.effect_level", 1);
    }

    private void setBeheadingOptions() {
        getFileConfiguration().createSection("enchantments.beheading");

        getFileConfiguration().set("enchantments.beheading.enable", true);

        getFileConfiguration().set("enchantments.beheading.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.beheading.enable_player_head", true);
        getFileConfiguration().set("enchantments.beheading.enable_zombie_head", true);
        getFileConfiguration().set("enchantments.beheading.enable_creeper_head", true);
        getFileConfiguration().set("enchantments.beheading.enable_dragon_head", true);
        getFileConfiguration().set("enchantments.beheading.enable_skeleton_skull", true);
        getFileConfiguration().set("enchantments.beheading.enable_wither_skeleton_skull", true);

        getFileConfiguration().set("enchantments.beheading.max_level", 3);

        getFileConfiguration().createSection("enchantments.beheading.level_1");
        getFileConfiguration().set("enchantments.beheading.level_1.chance", 5);

        getFileConfiguration().createSection("enchantments.beheading.level_2");
        getFileConfiguration().set("enchantments.beheading.level_2.chance", 10);

        getFileConfiguration().createSection("enchantments.beheading.level_3");
        getFileConfiguration().set("enchantments.beheading.level_3.chance", 15);
    }

    private void setDazeOptions() {
        getFileConfiguration().createSection("enchantments.daze");

        getFileConfiguration().set("enchantments.daze.enable", true);

        getFileConfiguration().set("enchantments.daze.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.daze.max_level", 3);

        getFileConfiguration().createSection("enchantments.daze.level_1");
        getFileConfiguration().set("enchantments.daze.level_1.chance", 5);

        getFileConfiguration().createSection("enchantments.daze.level_2");
        getFileConfiguration().set("enchantments.daze.level_2.chance", 10);

        getFileConfiguration().createSection("enchantments.daze.level_3");
        getFileConfiguration().set("enchantments.daze.level_3.chance", 15);
    }

    private void setEnderOptions() {
        getFileConfiguration().createSection("enchantments.ender");

        getFileConfiguration().set("enchantments.ender.enable", true);

        getFileConfiguration().set("enchantments.ender.conflicts",
                Arrays.asList("explosion", "lightning", "poison_cloud", "freezing"));

        getFileConfiguration().set("enchantments.daze.max_level", 1);

        getFileConfiguration().createSection("enchantments.daze.level_1");
        getFileConfiguration().set("enchantments.daze.level_1.damage", 5);
    }

    private void setExplosionOptions() {
        getFileConfiguration().createSection("enchantments.explosion");

        getFileConfiguration().set("enchantments.explosion.enable", true);

        getFileConfiguration().set("enchantments.explosion.conflicts", Arrays.asList("ender", "freezing"));

        getFileConfiguration().set("enchantments.explosion.max_level", 3);

        getFileConfiguration().createSection("enchantments.explosion.level_1");
        getFileConfiguration().set("enchantments.explosion.level_1.power", 1.5);

        getFileConfiguration().createSection("enchantments.explosion.level_2");
        getFileConfiguration().set("enchantments.explosion.level_2.power", 2.5);

        getFileConfiguration().createSection("enchantments.explosion.level_3");
        getFileConfiguration().set("enchantments.explosion.level_3.power", 3.3);
    }

    private void setLightningOptions() {
        getFileConfiguration().createSection("enchantments.lightning");

        getFileConfiguration().set("enchantments.lightning.enable", true);

        getFileConfiguration().set("enchantments.lightning.conflicts", Arrays.asList("ender", "freezing"));

        getFileConfiguration().set("enchantments.lightning.max_level", 3);

        getFileConfiguration().createSection("enchantments.lightning.level_1");
        getFileConfiguration().set("enchantments.lightning.level_1.damage", 2);
        getFileConfiguration().set("enchantments.lightning.level_1.radius", 3);
        getFileConfiguration().set("enchantments.lightning.level_1.first_fire_chance", 80);
        getFileConfiguration().set("enchantments.lightning.level_1.second_fire_chance", 40);
        getFileConfiguration().set("enchantments.lightning.level_1.thirst_fire_chance", 20);

        getFileConfiguration().createSection("enchantments.lightning.level_2");
        getFileConfiguration().set("enchantments.lightning.level_2.damage", 4);
        getFileConfiguration().set("enchantments.lightning.level_2.radius", 3);
        getFileConfiguration().set("enchantments.lightning.level_2.first_fire_chance", 80);
        getFileConfiguration().set("enchantments.lightning.level_2.second_fire_chance", 40);
        getFileConfiguration().set("enchantments.lightning.level_2.thirst_fire_chance", 20);

        getFileConfiguration().createSection("enchantments.lightning.level_3");
        getFileConfiguration().set("enchantments.lightning.level_3.damage", 6);
        getFileConfiguration().set("enchantments.lightning.level_3.radius", 3);
        getFileConfiguration().set("enchantments.lightning.level_3.first_fire_chance", 80);
        getFileConfiguration().set("enchantments.lightning.level_3.second_fire_chance", 40);
        getFileConfiguration().set("enchantments.lightning.level_3.thirst_fire_chance", 20);
    }

    private void setPoisonCloudOptions() {
        getFileConfiguration().createSection("enchantments.poison_cloud");

        getFileConfiguration().set("enchantments.poison_cloud.enable", true);

        getFileConfiguration().set("enchantments.poison_cloud.conflicts", Arrays.asList("ender"));

        getFileConfiguration().set("enchantments.poison_cloud.max_level", 3);

        getFileConfiguration().createSection("enchantments.poison_cloud.level_1");
        getFileConfiguration().set("enchantments.poison_cloud.level_1.cloud_time", 2);
        getFileConfiguration().set("enchantments.poison_cloud.level_1.potion_time", 3);
        getFileConfiguration().set("enchantments.poison_cloud.level_1.radius", 2.5);
        getFileConfiguration().set("enchantments.poison_cloud.level_1.effect_level", 1);

        getFileConfiguration().createSection("enchantments.poison_cloud.level_2");
        getFileConfiguration().set("enchantments.poison_cloud.level_2.cloud_time", 3);
        getFileConfiguration().set("enchantments.poison_cloud.level_2.potion_time", 3);
        getFileConfiguration().set("enchantments.poison_cloud.level_2.radius", 2.5);
        getFileConfiguration().set("enchantments.poison_cloud.level_2.effect_level", 2);

        getFileConfiguration().createSection("enchantments.poison_cloud.level_3");
        getFileConfiguration().set("enchantments.poison_cloud.level_3.cloud_time", 4);
        getFileConfiguration().set("enchantments.poison_cloud.level_3.potion_time", 3);
        getFileConfiguration().set("enchantments.poison_cloud.level_3.radius", 2.5);
        getFileConfiguration().set("enchantments.poison_cloud.level_3.effect_level", 3);
    }

    private void setFreezingOptions() {
        getFileConfiguration().createSection("enchantments.freezing");

        getFileConfiguration().set("enchantments.freezing.enable", true);

        getFileConfiguration().set("enchantments.freezing.conflicts", Arrays.asList("ender", "lightning", "explosion"));

        getFileConfiguration().set("enchantments.freezing.max_level", 3);

        getFileConfiguration().createSection("enchantments.freezing.level_1");
        getFileConfiguration().set("enchantments.freezing.level_1.time", 3);
        getFileConfiguration().set("enchantments.freezing.level_1.radius", 1);
        getFileConfiguration().set("enchantments.freezing.level_1.effect_level", 1);

        getFileConfiguration().createSection("enchantments.freezing.level_2");
        getFileConfiguration().set("enchantments.freezing.level_2.time", 5);
        getFileConfiguration().set("enchantments.freezing.level_2.radius", 2);
        getFileConfiguration().set("enchantments.freezing.level_2.effect_level", 2);

        getFileConfiguration().createSection("enchantments.freezing.level_3");
        getFileConfiguration().set("enchantments.freezing.level_3.time", 7);
        getFileConfiguration().set("enchantments.freezing.level_3.radius", 3);
        getFileConfiguration().set("enchantments.freezing.level_3.effect_level", 3);
    }

    private void setJumpingOptions() {
        getFileConfiguration().createSection("enchantments.jumping");

        getFileConfiguration().set("enchantments.jumping.enable", true);

        getFileConfiguration().set("enchantments.jumping.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.jumping.max_level", 3);

        getFileConfiguration().createSection("enchantments.jumping.level_1");
        getFileConfiguration().set("enchantments.jumping.level_1.effect_level", 1);

        getFileConfiguration().createSection("enchantments.jumping.level_2");
        getFileConfiguration().set("enchantments.jumping.level_2.effect_level", 2);

        getFileConfiguration().createSection("enchantments.jumping.level_3");
        getFileConfiguration().set("enchantments.jumping.level_3.effect_level", 3);
    }

    private void setSpeedOptions() {
        getFileConfiguration().createSection("enchantments.speed");

        getFileConfiguration().set("enchantments.speed.enable", true);

        getFileConfiguration().set("enchantments.speed.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.speed.max_level", 3);

        getFileConfiguration().createSection("enchantments.speed.level_1");
        getFileConfiguration().set("enchantments.speed.level_1.effect_level", 1);

        getFileConfiguration().createSection("enchantments.speed.level_2");
        getFileConfiguration().set("enchantments.speed.level_2.effect_level", 2);

        getFileConfiguration().createSection("enchantments.speed.level_3");
        getFileConfiguration().set("enchantments.speed.level_3.effect_level", 3);
    }

    private void setHealOptions() {
        getFileConfiguration().createSection("enchantments.heal");

        getFileConfiguration().set("enchantments.heal.enable", true);

        getFileConfiguration().set("enchantments.heal.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.heal.max_level", 3);

        getFileConfiguration().createSection("enchantments.heal.level_1");
        getFileConfiguration().set("enchantments.heal.level_1.period", 7);
        getFileConfiguration().set("enchantments.heal.level_1.health_count", 1);
        getFileConfiguration().set("enchantments.heal.level_1.food_level_count", 1);

        getFileConfiguration().createSection("enchantments.heal.level_2");
        getFileConfiguration().set("enchantments.heal.level_2.period", 6);
        getFileConfiguration().set("enchantments.heal.level_2.health_count", 1);
        getFileConfiguration().set("enchantments.heal.level_2.food_level_count", 1);

        getFileConfiguration().createSection("enchantments.heal.level_3");
        getFileConfiguration().set("enchantments.heal.level_3.period", 5);
        getFileConfiguration().set("enchantments.heal.level_3.health_count", 1);
        getFileConfiguration().set("enchantments.heal.level_3.food_level_count", 1);
    }

    private void setHookOptions() {
        getFileConfiguration().createSection("enchantments.hook");

        getFileConfiguration().set("enchantments.hook.enable", true);

        getFileConfiguration().set("enchantments.hook.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.hook.max_level", 1);

        getFileConfiguration().createSection("enchantments.hook.level_1");
        getFileConfiguration().set("enchantments.hook.level_1.distance", 10);
        getFileConfiguration().set("enchantments.hook.level_1.radius", 3);
    }

    private void setMagnetismOptions() {
        getFileConfiguration().createSection("enchantments.magnetism");

        getFileConfiguration().set("enchantments.magnetism.enable", true);

        getFileConfiguration().set("enchantments.magnetism.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.magnetism.max_level", 3);

        getFileConfiguration().createSection("enchantments.magnetism.level_1");
        getFileConfiguration().set("enchantments.magnetism.level_1.radius", 3);

        getFileConfiguration().createSection("enchantments.magnetism.level_2");
        getFileConfiguration().set("enchantments.magnetism.level_2.radius", 5);

        getFileConfiguration().createSection("enchantments.magnetism.level_3");
        getFileConfiguration().set("enchantments.magnetism.level_3.radius", 7);
    }

    private void setThunderclapEnchantment() {
        getFileConfiguration().createSection("enchantments.thunderclap");

        getFileConfiguration().set("enchantments.thunderclap.enable", true);

        getFileConfiguration().set("enchantments.thunderclap.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.thunderclap.max_level", 3);

        getFileConfiguration().set("enchantments.thunderclap.enable_chance", true);
        getFileConfiguration().set("enchantments.thunderclap.enable_cooldown", false);

        getFileConfiguration().createSection("enchantments.thunderclap.level_1");
        getFileConfiguration().set("enchantments.thunderclap.level_1.damage", 2);
        getFileConfiguration().set("enchantments.thunderclap.level_1.radius", 3);
        getFileConfiguration().set("enchantments.thunderclap.level_1.chance", 20);
        getFileConfiguration().set("enchantments.thunderclap.level_1.cooldown", 10);

        getFileConfiguration().createSection("enchantments.thunderclap.level_2");
        getFileConfiguration().set("enchantments.thunderclap.level_2.damage", 4);
        getFileConfiguration().set("enchantments.thunderclap.level_2.radius", 3);
        getFileConfiguration().set("enchantments.thunderclap.level_2.chance", 20);
        getFileConfiguration().set("enchantments.thunderclap.level_2.cooldown", 10);

        getFileConfiguration().createSection("enchantments.thunderclap.level_3");
        getFileConfiguration().set("enchantments.thunderclap.level_3.damage", 6);
        getFileConfiguration().set("enchantments.thunderclap.level_3.radius", 3);
        getFileConfiguration().set("enchantments.thunderclap.level_3.chance", 20);
        getFileConfiguration().set("enchantments.thunderclap.level_3.cooldown", 10);
    }

    private void setShieldBashEnchantment() {
        getFileConfiguration().createSection("enchantments.shield_bash");

        getFileConfiguration().set("enchantments.shield_bash.enable", true);

        getFileConfiguration().set("enchantments.shield_bash.conflicts", Arrays.asList());

        getFileConfiguration().set("enchantments.shield_bash.max_level", 3);

        getFileConfiguration().createSection("enchantments.shield_bash.level_1");
        getFileConfiguration().set("enchantments.shield_bash.level_1.damage", 2);
        getFileConfiguration().set("enchantments.shield_bash.level_1.chance", 20);
        getFileConfiguration().set("enchantments.shield_bash.level_1.radius", 2);
        getFileConfiguration().set("enchantments.shield_bash.level_1.velocity", 0.5);

        getFileConfiguration().createSection("enchantments.shield_bash.level_2");
        getFileConfiguration().set("enchantments.shield_bash.level_2.damage", 3);
        getFileConfiguration().set("enchantments.shield_bash.level_2.chance", 30);
        getFileConfiguration().set("enchantments.shield_bash.level_2.radius", 2);
        getFileConfiguration().set("enchantments.shield_bash.level_2.velocity", 0.5);

        getFileConfiguration().createSection("enchantments.shield_bash.level_3");
        getFileConfiguration().set("enchantments.shield_bash.level_3.damage", 4);
        getFileConfiguration().set("enchantments.shield_bash.level_3.chance", 40);
        getFileConfiguration().set("enchantments.shield_bash.level_3.radius", 2);
        getFileConfiguration().set("enchantments.shield_bash.level_3.velocity", 0.5);
    }

    public int getMaxLevel(String enchantmentsKey) {
        int maxLevel = getFileConfiguration().getInt("enchantments." + enchantmentsKey + ".max_level");
        if (maxLevel < 1) {
            maxLevel = 1;
            getFileConfiguration().set("enchantments." + enchantmentsKey + ".max_level", maxLevel);
            save();
        }
        return maxLevel;
    }

    public double getParameter(String enchantmentKey, String parameterKey, int level) {
        int newLevel = level;
        if (newLevel > getMaxLevel(enchantmentKey)) {
            newLevel = getMaxLevel(enchantmentKey);
        }
        double param = getFileConfiguration().getDouble("enchantments." + enchantmentKey + ".level_" + newLevel +
                "." + parameterKey);
        if (param <= 0) {
            param = 1;
            getFileConfiguration().set("enchantments." + enchantmentKey + ".level_" + newLevel + "." + parameterKey, param);
            save();
        }
        return param;
    }

    public int getTime(String enchantmentKey, int level) {
        return (int) Math.round(getParameter(enchantmentKey, "time", level));
    }

    public int getCooldown(String enchantmentKey, int level) {
        return (int) Math.round(getParameter(enchantmentKey, "cooldown", level));
    }

    public int getDamage(String enchantmentKey, int level) {
        return (int) Math.round(getParameter(enchantmentKey, "damage", level));
    }

    public int getAmplifier(String enchantmentKey, int level) {
        return (int) Math.round(getParameter(enchantmentKey, "effect_level", level)) - 1;
    }

    public int getChance(String enchantmentKey, int level) {
        return (int) Math.round(getParameter(enchantmentKey, "chance", level));
    }

    public float getPower(String enchantmentKey, int level) {
        return (float) getParameter(enchantmentKey, "power", level);
    }

    public int getCount(String enchantmentKey, int level) {
        return (int) Math.round(getParameter(enchantmentKey, "count", level));
    }

    public float getRadius(String enchantmentKey, int level) {
        return (float) getParameter(enchantmentKey, "radius", level);
    }

    public int getPeriod(String enchantmentKey, int level) {
        return (int) Math.round(getParameter(enchantmentKey, "period", level));
    }

    public int getDistance(String enchantmentKey, int level) {
        return (int) Math.round(getParameter(enchantmentKey, "distance", level));
    }

    public float getVelocity(String enchantmentKey, int level) {
        return (float) getParameter(enchantmentKey, "velocity", level);
    }

    public String getLanguage() {
        return getFileConfiguration().getString("language");
    }

    public List<String> getConflicts(String enchantmentKey) {
        return getFileConfiguration().getStringList("enchantments." + enchantmentKey + ".conflicts");
    }

    public boolean isEnableEnchantment(String enchantmentKey) {
        return getFileConfiguration().getBoolean("enchantments." + enchantmentKey + ".enable");
    }

    public boolean isEnableWhat(String enchantmentKey, String parameter) {
        return getFileConfiguration().getBoolean("enchantments." + enchantmentKey + ".enable_" + parameter);
    }

    public boolean isEnableDescription() {
        return getFileConfiguration().getBoolean("enable_description_in_gui");
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
